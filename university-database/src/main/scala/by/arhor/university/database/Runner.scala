package by.arhor.university.database

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

import scala.util.Using

object Runner extends App {

  private val COMMAND_ROUTER: Map[String, () => Unit] = Map(
    "--create-file" -> (() => createFile()),
    "--execute"     -> (() => execute())
  )

  if ((args.length > 0) && (args(0) != null)) {
    val command = args(0).toLowerCase

    COMMAND_ROUTER.get(command) match {
      case Some(route) => route.apply()
      case None => handleUnknownCommand(command)
    }
  } else {
    createFile()
  }

  private def handleUnknownCommand(command: String): Unit = {
    println(s"unknown command '$command}'")
    println("available commands:")

    COMMAND_ROUTER.keys.foreach(key => println(s"\t$key"))
  }

  private def execute(): Unit = {
    buildModel().executeScripts()
  }

  private def createFile(): Unit = {
    val script = buildModel().materialize()
    val path = Paths.get("./university-database/whole-script.sql")

    Files.deleteIfExists(path)
    Files.createFile(path)

    Using(new BufferedWriter(new FileWriter(path.toFile, StandardCharsets.UTF_8))) { writer =>
      writer.write(script)
      writer.flush()
    }
  }

  private def buildModel(): DBProjectModel = {
    val resource: URL = this.getClass.getClassLoader.getResource("sql")

    if (resource == null) {
      throw new IllegalArgumentException("`sql` folder is not found!")
    }

    DBProjectModel.fromRootDirectory(new File(resource.getFile))
  }
}
