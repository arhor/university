package by.arhor.university.database

import java.io._
import java.nio.charset.StandardCharsets
import java.sql.{Connection, DriverManager, SQLException}
import java.util.concurrent.atomic.AtomicInteger
import java.util.{Properties, StringJoiner}
import java.util.regex.Pattern

import by.arhor.core.{Either, Lazy}
import by.arhor.university.database.model.{CreateQuery, InsertQuery, Module}
import javax.annotation.Nonnull
import javax.xml.bind.{JAXBContext, JAXBException, Unmarshaller}
import javax.xml.stream.XMLInputFactory

import scala.util.{Failure, Success, Using}

final class DBProjectModel(modules: Map[String, Module]) {

  import by.arhor.university.database.DBProjectModel._

  private var currentContext: String = _

  def executeScripts(): Unit = {
    modules.keys.foreach(key => resolveDependency(key))
  }

  def materialize(): String = {
    val text = new StringBuilder()

    modules.foreach { case (name, module) => module.setExecutionCost(computeExecutionCost(name)) }

    modules.values
      .toSeq
      .sortWith {
        _.getExecutionCost < _.getExecutionCost
      }
      .foreach { module => writeModule(module.getName, text) }


    text.toString()
  }

  private def computeExecutionCost(name: String): Int = {
    val cost = new AtomicInteger(1)

    modules.get(name) match {
      case Some(module) =>
        val dependencies = module.getDependencies
        if (dependencies != null) {
          val dependencyList = dependencies.getList
          if (dependencyList != null) {
            dependencyList.forEach { dependency => cost.addAndGet(computeExecutionCost(dependency.getName)) }
          }
        }

        val queries = module.getQueries
        if (queries != null) {
          val queryList = queries.getList
          if (queryList != null) {
            queryList.forEach {
              case query: CreateQuery => query.getTarget match {
                case "table" => cost.incrementAndGet()
                case "procedure" => cost.addAndGet(10)
                case _ =>
              }
              case _: InsertQuery => cost.addAndGet(100)
              case _ =>
            }
          }
        }
      case _ =>
    }

    cost.get()
  }

  private def handleContext(context: String): Unit = {
    if (context != null) {
      val lazyConnection = CONNECTION.get()
      if (lazyConnection.hasError) {
        lazyConnection.getError.printStackTrace()
        return
      }
      if (!context.equals(currentContext)) {
        Using(lazyConnection.getItem.createStatement()) { statement =>
          currentContext = context
          statement.execute("USE " + currentContext)
          System.out.printf("context changed to to [%s]%n", currentContext)
        } match {
          case Failure(exception) => throw exception
          case _ =>
        }
      }
    }
  }

  private def resolveDependency(name: String): Unit = {
    modules.get(name) match {
      case Some(module) if !module.isResolved =>

        println(s"processing module: $name")

        val dependencies = module.getDependencies

        if (dependencies != null) {
          dependencies.forEach(dependency => resolveDependency(dependency.getName))
        }

        val lazyConnection = CONNECTION.get()
        if (lazyConnection.hasError) {
          lazyConnection.getError.printStackTrace()
          return
        }

        val queries = module.getQueries
        val queryList = queries.getList

        if (queryList != null) {
          for (i <- 0 to queryList.size()) {
            val query = queryList.get(i)

            Using(lazyConnection.getItem.createStatement()) { statement =>
              if (query.getContext != null) {
                handleContext(query.getContext)
              } else if (queries.getContext != null) {
                handleContext(queries.getContext)
              }

              if (query.getContent != null) {

                val result = statement.executeUpdate(query.getContent)

                println(
                  s"\t-- ${i + 1} of ${queryList.size()} queries processed, status - ${if (result > -1) "COMPLETE" else "NOT EXECUTED"}")

                if (result > -1) {
                  lazyConnection.getItem.commit()
                }
              }

            } match {
              case Failure(exception) => throw exception
              case _ =>
            }
          }
        }
        module.setResolved(true)
    }
  }

  private def writeModule(@Nonnull name: String, @Nonnull text: StringBuilder): Unit = {
    modules.get(name) match {
      case Some(module) if !module.isResolved =>
        val dependencies = module.getDependencies
        if (dependencies != null) {
          dependencies.forEach(dependency => writeModule(dependency.getName, text))
        }

        val queries = module.getQueries
        val queryList = queries.getList

        if (queryList != null) {
          text.append("-- #module: ").append(name).append(" ").append(BLOCK_START).append(" START\n")

          if (dependencies != null) {
            val dependencyList = dependencies.getList
            if (dependencyList != null) {
              val deps = new StringJoiner(", ", "[", "]\n\n")
              dependencyList.forEach(dependency => deps.add(dependency.getName))
              text.append("-- #dependencies: ").append(deps.toString)
            }
          }

          var counter = 0

          queryList.forEach { query =>
            if (query.getContext != null) {
              writeContext(query.getContext, text)
            } else if (queries.getContext != null) {
              writeContext(queries.getContext, text)
            }

            val content = query.getContent
            if (content != null) {
              Using(new BufferedReader(new StringReader(content))) { reader =>

                var leadingSpaces = 0
                var firstLineProcessed = false

                reader.lines
                  .filter(line => !(line.isBlank || line.isEmpty))
                  .forEach { line: String =>
                    if (!firstLineProcessed) {
                      println(s"processing first line: $leadingSpaces")
                      for (c <- line.toCharArray) {
                        if (!firstLineProcessed && c == ' ') {
                          leadingSpaces += 1
                        } else {
                          firstLineProcessed = true
                        }
                      }
                      println(s"first line counted: $leadingSpaces")
                    }

                    println(s"[DEBUG] $line ## leading spaces: $leadingSpaces")
                    text.append(line.substring(leadingSpaces))
                    text.append("\n")
                  }
              } match {
                case Failure(exception) => throw exception
                case _ =>
              }

              counter += 1

              text.append("GO").append('\n')
              if (counter < queryList.size()) {
                text.append('\n')
              }
            }
          }
        }
        text.append("-- #module: ").append(name).append(" ").append(BLOCK_END).append(" END\n\n")
        module.setResolved(true)
      case _ =>
    }
  }

  private def writeContext(context: String, text: StringBuilder): Unit = {
    if (context != null && !context.equals(currentContext)) {
      currentContext = context
      text.append("USE ").append(currentContext).append("\n")
      text.append("GO").append("\n")
      text.append("\n")
    }
  }
}

object DBProjectModel {

  private val BLOCK_START = ">>>"
  private val BLOCK_END = "<<<"

  private val DB_URL = "db.url"
  private val DB_DRIVER = "db.driver"
  private val DB_USERNAME = "db.username"
  private val DB_PASSWORD = "db.password"

  private val CONNECTION: Lazy[Either[Connection, Throwable]] = Lazy.evalSafe { () =>
    val classLoader = classOf[DBProjectModel].getClassLoader
    val properties = new Properties()
    val configFile = classLoader.getResourceAsStream("database.properties")

    if (configFile == null) {
      throw new RuntimeException("config file not found!")
    }

    try {
      properties.load(configFile)
      Class.forName(properties.getProperty(DB_DRIVER))
      val connection =
        DriverManager.getConnection(
          properties.getProperty(DB_URL),
          properties.getProperty(DB_USERNAME),
          properties.getProperty(DB_PASSWORD))
      Either.success(connection)
    } catch {
      case e@(_: IOException | _: ClassNotFoundException | _: SQLException) => Either.error(e)
    }
  }

  private val XML_INPUT_FACTORY: Lazy[XMLInputFactory] = Lazy.evalSafe { () =>
    val xmlInputFactory = XMLInputFactory.newFactory()
    xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false)
    xmlInputFactory
  }

  private val UNMARSHALLER: Lazy[Either[Unmarshaller, Throwable]] = Lazy.evalSafe { () =>
    try {
      val context = JAXBContext.newInstance(classOf[Module])
      val unmarshaller = context.createUnmarshaller()
      Either.success(unmarshaller)
    } catch {
      case e: JAXBException => Either.error(e)
    }
  }

  private val XML_FILE_PATTERN: Lazy[Pattern] = Lazy.evalSafe { () => Pattern.compile(".+(-.+)*(\\.xml)") }

  def fromRootDirectory(@Nonnull rootDirectory: File): DBProjectModel = {
    if (!rootDirectory.isDirectory) {
      throw new IllegalArgumentException("Passed file `rootDirectory` must be a `directory`...")
    }
    val modules = handleDirectory(rootDirectory)
    new DBProjectModel(modules)
  }

  private def handleDirectory(@Nonnull directory: File): Map[String, Module] = {
    var modules = Map[String, Module]()

    val files = directory.listFiles()

    if (files != null) {
      files.foreach { file =>
        if (file.isDirectory) {
          val nestedModules = handleDirectory(file)
          modules ++= nestedModules
        } else if (XML_FILE_PATTERN.get().matcher(file.getName).matches()) {
          val module = parseModule(file)
          modules += (module.getName -> module)
        }
      }
    }
    modules
  }

  private def parseModule(xmlDocument: File): Module = {
    val lazyUnmarshaller = DBProjectModel.UNMARSHALLER.get()

    if (lazyUnmarshaller.hasError) {
      throw lazyUnmarshaller.getError
    }

    Using(new BufferedReader(new FileReader(xmlDocument, StandardCharsets.UTF_8))) { br: Reader =>
      val xmlStreamReader = DBProjectModel.XML_INPUT_FACTORY.get().createXMLStreamReader(br)
      lazyUnmarshaller.getItem.unmarshal(xmlStreamReader).asInstanceOf[Module]
    } match {
      case Success(value) => value
      case Failure(exception) => throw exception
    }
  }
}