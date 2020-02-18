package by.arhor.university.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import by.arhor.core.Either;
import by.arhor.core.Lazy;
import by.arhor.university.database.model.Dependencies;
import by.arhor.university.database.model.Dependency;
import by.arhor.university.database.model.Module;
import by.arhor.university.database.model.Queries;
import by.arhor.university.database.model.Query;

public class Runner {

  private static final String DB_URL      = "db.url";
  private static final String DB_DRIVER   = "db.driver";
  private static final String DB_USERNAME = "db.username";
  private static final String DB_PASSWORD = "db.password";

  //  private static final Pattern EXEC_STATEMENT =
  //      Pattern.compile("^(exec:)( ?)(')([a-zA-Z0-9_\\-]+(\\.sql)*)(')$");
  //
  //  private final File[] folders;
  //
  //  private Runner() {
  //    URL resource = getClass().getClassLoader().getResource("sql");
  //    if (resource == null) {
  //      throw new IllegalArgumentException("`sql` folder is not found!");
  //    } else {
  //      folders = new File(resource.getFile()).listFiles();
  //    }
  //  }
  //
  //  private void processFolder(String name, Connection connection) throws IOException,
  // SQLException {
  //    System.out.print("Looking for " + name + " folder");
  //    final var folder = Arrays.stream(folders)
  //        .filter(File::isDirectory)
  //        .filter(file -> name.equals(file.getName()))
  //        .findFirst()
  //        .orElseThrow(() -> new RuntimeException("Folder " + name + " is not found!"));
  //    System.out.print(" - DONE\n\n");
  //    processFolder(folder, connection);
  //  }
  //
  //  private void processFolder(File folder, Connection connection) throws IOException,
  // SQLException {
  //    final var manifest = findManifest(folder);
  //
  //    try (var content = new Scanner(manifest)) {
  //
  //      final var folderContent = folder.listFiles();
  //
  //      while (content.hasNextLine()) {
  //        final var matcher = EXEC_STATEMENT.matcher(content.nextLine());
  //        if (matcher.matches()) {
  //          final var fileNameToExecute = matcher.group(4);
  //
  //          final var script = Arrays
  //              .stream(Objects.requireNonNull(folderContent))
  //              .filter(file -> fileNameToExecute.equals(file.getName()))
  //              .findFirst()
  //              .orElseThrow(() -> new RuntimeException("File " + fileNameToExecute + "not
  // found"));
  //
  //          try (var scriptReader = new BufferedReader(new FileReader(script))) {
  //            System.out.println("Processing file '" + fileNameToExecute + "':");
  //
  //            final var batches = new ArrayList<String>();
  //            final var scriptBody = new StringBuilder();
  //
  //            String line;
  //            while ((line = scriptReader.readLine()) != null) {
  //              if (line.matches("^( *)GO( *)$")) {
  //                batches.add(scriptBody.toString());
  //                scriptBody.delete(0, scriptBody.length());
  //                continue;
  //              }
  //              scriptBody.append(line).append('\n');
  //            }
  //
  //            if (scriptBody.length() > 0) {
  //              batches.add(scriptBody.toString());
  //            }
  //
  //            for (int i = 0; i < batches.size(); i++) {
  //              try (var statement = connection.createStatement()) {
  //
  //                final var query = batches.get(i);
  //
  //                final var result = statement.executeUpdate(query);
  //
  //                System.out.printf(
  //                    "-- %d of %d batches processed, status - %s%n",
  //                    i + 1,
  //                    batches.size(),
  //                    (result > -1) ? "COMPLETE" : "NOT EXECUTED"
  //                );
  //
  //                if (result > -1) {
  //                  connection.commit();
  //                }
  //              }
  //            }
  //            System.out.println();
  //          }
  //        }
  //      }
  //    }
  //  }
  //
  //  private File findManifest(@Nonnull File directory) {
  //    final var files = directory.listFiles();
  //
  //    if (files == null) {
  //      throw new RuntimeException("Directory " + directory.getName() + " is empty");
  //    }
  //
  //    File manifest = null;
  //
  //    for (File file : files) {
  //      if ("exec-manifest.txt".equals(file.getName())) {
  //        if (manifest != null) {
  //          throw new RuntimeException("There is no exec-manifest.txt in the directory " +
  // directory.getName());
  //        }
  //        manifest = file;
  //      }
  //    }
  //
  //    if (manifest == null) {
  //      throw new RuntimeException("More than one exec-manifest.txt present in the directory " +
  // directory.getName());
  //    }
  //
  //    return manifest;
  //  }
  //
  //  private void execute() throws Throwable {
  //    Connection connection = ConnectionPool.getInstance().getConnection();
  //
  //    processFolder("tables", connection);
  //    processFolder("procedures", connection);
  //    processFolder("data", connection);
  //
  //    connection.close();
  //  }

  private static final Lazy<XMLInputFactory> XML_INPUT_FACTORY =
      Lazy.evalSafe(
          () -> {
            final var xmlInputFactory = XMLInputFactory.newFactory();
            xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
            return xmlInputFactory;
          });

  private static final Lazy<Either<Connection, Throwable>> CONNECTION =
      Lazy.evalSafe(
          () -> {
            final var classLoader = Runner.class.getClassLoader();

            final var properties = new Properties();

            final var configFile = classLoader.getResourceAsStream("database.properties");

            if (configFile == null) {
              return null;
            }

            Connection connection = null;

            try {
              properties.load(configFile);
              Class.forName(properties.getProperty(DB_DRIVER));
              connection =
                  DriverManager.getConnection(
                      properties.getProperty(DB_URL),
                      properties.getProperty(DB_USERNAME),
                      properties.getProperty(DB_PASSWORD));
              return Either.success(connection);
            } catch (IOException | ClassNotFoundException | SQLException e) {
              return Either.error(e);
            }
          });

  private static final Lazy<Either<Unmarshaller, Throwable>> UNMARSHALLER =
      Lazy.evalSafe(
          () -> {
            try {
              final var context = JAXBContext.newInstance(Module.class);
              final var unmarshaller = context.createUnmarshaller();
              return Either.success(unmarshaller);
            } catch (JAXBException e) {
              return Either.error(e);
            }
          });

  public static void main(String[] args) throws Throwable {

    final var lazyUnmarshaller = UNMARSHALLER.get();
    if (lazyUnmarshaller.hasError()) {
      throw lazyUnmarshaller.getError();
    }
    final var unmarshaller = lazyUnmarshaller.getItem();

    final var lazyConnection = CONNECTION.get();
    if (lazyConnection.hasError()) {
      throw lazyConnection.getError();
    }

    URL resource = Runner.class.getClassLoader().getResource("sql");
    if (resource == null) {
      throw new IllegalArgumentException("`sql` folder is not found!");
    } else {
      final var directories = new File(resource.getFile()).listFiles();
      assert directories != null;
      for (File directory : directories) {
        if ("tables".equals(directory.getName()) || "procedures".equals(directory.getName())) {
          final var files = directory.listFiles();
          assert files != null;
          for (File file : files) {
            if (file.getName().matches("(.+)(\\.xml)")) {
              try (var br = new BufferedReader(new FileReader(file))) {

                final var xmlInputFactory = XML_INPUT_FACTORY.get();
                final var xmlStreamReader = xmlInputFactory.createXMLStreamReader(br);

                final var module = (Module) unmarshaller.unmarshal(xmlStreamReader);
                MODULES.put(module.getName(), module);
              }

            }
          }
        }
      }
    }

    MODULES.keySet().forEach(Runner::resolveDependency);

  }

  private static final Map<String, Module> MODULES = new HashMap<>();
  private static String currentContext = null;

  private static void resolveDependency(String name) {
    final var module = MODULES.get(name);
    if (module != null && !module.isResolved()) {

      System.out.printf("processing module: %s%n", name);

      final var dependencies = module.getDependencies();
      if (dependencies != null) {
        dependencies.forEach(dependency -> resolveDependency(dependency.getName()));
      }
      final var lazyConnection = CONNECTION.get();
      if (lazyConnection.hasError()) {
        lazyConnection.getError().printStackTrace();
        return;
      }
      final var connection = lazyConnection.getItem();

      final var queries = module.getQueries();

      final var queryList = queries.getQueries();

      if (queryList != null) {
        for (int i = 0; i < queryList.size(); i++) {
          final var query = queryList.get(i);
          try (var statement = connection.createStatement()) {

            if (query.getContext() != null) {
              handleContext(query.getContext());
            } else if (queries.getContext() != null) {
              handleContext(queries.getContext());
            }

            if (query.getContent() != null) {

              final var result = statement.executeUpdate(query.getContent());

              System.out.printf(
                  "\t-- %d of %d queries processed, status - %s%n",
                  i + 1,
                  queryList.size(),
                  (result > -1) ? "COMPLETE" : "NOT EXECUTED"
              );

              if (result > -1) {
                connection.commit();
              }
            }

          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
      module.setResolved(true);
    }
  }

  private static void handleContext(String context) {
    if (context != null) {
      final var lazyConnection = CONNECTION.get();
      if (lazyConnection.hasError()) {
        lazyConnection.getError().printStackTrace();
        return;
      }
      if (!context.equals(currentContext)) {
        try (var statement = lazyConnection.getItem().createStatement()) {
          currentContext = context;
          statement.execute("USE " + currentContext);
          System.out.printf("context changed to to [%s]%n", currentContext);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
