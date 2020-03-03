package by.arhor.university.database

import java.io._
import java.nio.charset.StandardCharsets
import java.sql.{Connection, DriverManager, SQLException}
import java.util.concurrent.atomic.AtomicInteger
import java.util.{Properties, StringJoiner}
import java.util.regex.Pattern

import by.arhor.core.Either
import by.arhor.core.pattern.`lazy`.Lazy
import by.arhor.university.database.xml.{CreateQuery, Dependency, InsertQuery, Module}
import javax.xml.bind.{JAXBContext, JAXBException, Unmarshaller}
import javax.xml.stream.XMLInputFactory

import scala.util.{Failure, Success, Using}

final class DBProjectModel(modules: Map[String, Module]) {

  import by.arhor.university.database.DBProjectModel._

  private var currentContext: String = _

  def connection(): Connection = {
    val lazyConnection = CONNECTION.get()

    if (lazyConnection.hasError) {
      throw lazyConnection.getError
    } else {
      lazyConnection.getItem
    }
  }

  def executeScripts(): Unit = {
    modules.keys.foreach(key => resolveDependency(key))
  }

  def materialize(): String = {
    val text = new StringBuilder()

    modules.foreach { case (name, module) => module.executionCost = computeExecutionCost(name) }

    modules.values.toSeq
      .sortWith { _.executionCost < _.executionCost }
      .foreach { module => writeModule(module.name, text) }

    text.toString()
  }

  private def computeExecutionCost(name: String): Int = {
    val cost = new AtomicInteger(1)

    modules.get(name) match {
      case Some(module) =>
        module.forEachDependency { case Dependency(name) => cost.addAndGet(computeExecutionCost(name)) }

        module.forEachQuery {
          case CreateQuery(target) => target match {
            case "table" => cost.incrementAndGet()
            case "procedure" => cost.addAndGet(10)
            case _ =>
          }
          case _: InsertQuery => cost.addAndGet(100)
          case _ =>
        }

      case _ =>
    }

    cost.get()
  }

  private def handleContext(context: String): Unit = {
    if (context != null && !context.equals(currentContext)) {
      Using(connection().createStatement()) { statement =>
        currentContext = context
        statement.execute("USE " + currentContext)
        println(s"context changed to to [$currentContext]")
      } match {
        case Failure(exception) => throw exception
        case _ =>
      }
    }
  }

  private def resolveDependency(name: String): Unit = {
    modules.get(name) match {
      case Some(module) if !module.resolved =>

        println(s"processing module: $name")

        val dependencies = module.dependencies

        if (dependencies != null) {
          dependencies.forEach { dependency => resolveDependency(dependency.name) }
        }

        val queries = module.queries

        if (queries.list != null) {
          for (i <- 0 to queries.list.size()) {
            val query = queries.list.get(i)

            Using(connection().createStatement()) { statement =>
              if (query.context != null) {
                handleContext(query.context)
              } else if (queries.context != null) {
                handleContext(queries.context)
              }

              if (query.content != null) {

                val result = statement.executeUpdate(query.content)

                println(
                  s"\t-- ${i + 1} of ${queries.list.size()} queries processed, status - ${if (result > -1) "COMPLETE" else "NOT EXECUTED"}")

                if (result > -1) {
                  connection().commit()
                }
              }

            } match {
              case Failure(exception) => throw exception
              case _ =>
            }
          }
        }
        module.resolved = true
    }
  }

  private def buildDepList(module: Module): String = {
    val deps = new StringJoiner(", ", "-- #dependencies: [", "]\n\n")
    module.forEachDependency { case Dependency(name) => deps.add(name) }
    deps.toString
  }

  private def writeModule(name: String, text: StringBuilder): Unit = {
    modules.get(name) match {
      case Some(module) if !module.resolved =>
        module.forEachDependency { case Dependency(name) => writeModule(name, text) }

        val queries = module.queries

        if (queries.list != null) {
          text.append("-- #module: ").append(name).append(" ").append(BLOCK_START).append(" START\n")

          if (module.dependencies != null) {
            text.append(buildDepList(module))
          }

          var counter = 0
          queries.forEach { query =>
            if (query.context != null) {
              writeContext(query.context, text)
            } else if (queries.context != null) {
              writeContext(queries.context, text)
            }

            if (query.content != null) {
              Using(new BufferedReader(new StringReader(query.content))) { reader =>

                var leadingSpaces = 0
                var firstLineProcessed = false

                reader.lines
                  .filter(line => !(line.isBlank || line.isEmpty))
                  .forEach { line =>
                    if (!firstLineProcessed) {
                      for (c <- line.toCharArray) {
                        if (!firstLineProcessed && (c == ' ')) {
                          leadingSpaces += 1
                        } else {
                          firstLineProcessed = true
                        }
                      }
                    }

                    text.append(line.substring(leadingSpaces))
                    text.append("\n")
                  }
              } match {
                case Failure(exception) => throw exception
                case _ =>
              }

              counter += 1

              text.append("GO").append('\n')
              if (counter < queries.list.size()) {
                text.append('\n')
              }
            }
          }
        }
        text.append("-- #module: ").append(name).append(" ").append(BLOCK_END).append(" END\n\n")
        module.resolved = true
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

  def fromRootDirectory(rootDirectory: File): DBProjectModel = {
    if (!rootDirectory.isDirectory) {
      throw new IllegalArgumentException("Passed file `rootDirectory` must be a `directory`...")
    }
    val modules = handleDirectory(rootDirectory)
    new DBProjectModel(modules)
  }

  private def handleDirectory(directory: File): Map[String, Module] = {
    var modules = Map[String, Module]()

    val files = directory.listFiles()

    if (files != null) {
      files.foreach { file =>
        if (file.isDirectory) {
          val nestedModules = handleDirectory(file)
          modules ++= nestedModules
        } else if (XML_FILE_PATTERN.get().matcher(file.getName).matches()) {
          val module = parseModule(file)
          modules += (module.name -> module)
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

    Using(new BufferedReader(new FileReader(xmlDocument, StandardCharsets.UTF_8))) { br =>
      val xmlStreamReader = DBProjectModel.XML_INPUT_FACTORY.get().createXMLStreamReader(br)
      lazyUnmarshaller.getItem.unmarshal(xmlStreamReader).asInstanceOf[Module]
    } match {
      case Success(value) => value
      case Failure(exception) => throw exception
    }
  }
}