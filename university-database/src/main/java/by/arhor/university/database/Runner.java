package by.arhor.university.database;

import static java.util.Map.entry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Runner {

  @FunctionalInterface
  private interface Route {
    void handle() throws Throwable;
  }

  private static final Map<String, Route> COMMAND_ROUTER = Map.ofEntries(
      entry("--create-file", Runner::createFile),
      entry("--execute", Runner::execute)
  );

  public static void main(String[] args) throws Throwable {
    if ((args.length > 0) && (args[0] != null)) {
      var command = args[0].toLowerCase();

      var route = COMMAND_ROUTER.get(command);
      if (route != null) {
        route.handle();
      } else {
        handleUnknownCommand(command);
      }
    }
  }

  private static void handleUnknownCommand(String command) {
    System.out.printf("unknown command '%s'%n", command);
    System.out.println("available commands:");
    COMMAND_ROUTER.keySet().forEach(c -> System.out.printf("\t%s%n", c));
  }

  private static void execute() throws Throwable {
    buildModel().executeScripts();
  }

  private static void createFile() throws Throwable {
    final var script = buildModel().materialize();

    var path = Paths.get("./university-database/whole-script.sql");

    Files.deleteIfExists(path);
    Files.createFile(path);

    try (var writer = new BufferedWriter(new FileWriter(path.toFile()))) {
      writer.write(script);
      writer.flush();
    }
  }

  private static DBProjectModel buildModel() throws Throwable {
    URL resource = Runner.class.getClassLoader().getResource("sql");
    if (resource == null) {
      throw new IllegalArgumentException("`sql` folder is not found!");
    }
    return DBProjectModel.fromRootDirectory(new File(resource.getFile()));
  }
}
