package by.arhor.university.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;

import by.arhor.core.Either;
import by.arhor.core.Lazy;
import by.arhor.university.database.model.Module;

public final class DBProjectModel {

  private static final String DB_URL      = "db.url";
  private static final String DB_DRIVER   = "db.driver";
  private static final String DB_USERNAME = "db.username";
  private static final String DB_PASSWORD = "db.password";

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

  private static final Lazy<XMLInputFactory> XML_INPUT_FACTORY =
      Lazy.evalSafe(
          () -> {
            final var xmlInputFactory = XMLInputFactory.newFactory();
            xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
            return xmlInputFactory;
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

  private final Map<String, Module> modules = new HashMap<>();

  private String currentContext;

  private DBProjectModel(Map<String, Module> modules) {
    this.modules.putAll(modules);
  }

  public void executeScripts() {
    modules.keySet().forEach(this::resolveDependency);
  }

  private void handleContext(String context) {
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

  private void resolveDependency(String name) {
    final var module = modules.get(name);
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

  public static DBProjectModel fromRootDirectory(@Nonnull File rootDirectory) throws Throwable {
    if (!rootDirectory.isDirectory()) {
      throw new IllegalArgumentException("Passed file `rootDirectory` must be a `directory`...");
    }
    final var modules = handleDirectory(rootDirectory);
    return new DBProjectModel(modules);
  }

  private static final Lazy<Pattern> XML_FILE_PATTERN =
      Lazy.evalSafe(() -> Pattern.compile("(.+)(\\.xml)"));

  private static Map<String, Module> handleDirectory(@Nonnull File directory) throws Throwable {
    final var modules = new HashMap<String, Module>();

    final var files = directory.listFiles();

    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          final var nestedModules = handleDirectory(file);
          modules.putAll(nestedModules);
        } else if (XML_FILE_PATTERN.get().matcher(file.getName()).matches()) {
          final var module = parseModule(file);
          modules.put(module.getName(), module);
        }
      }
    }

    return modules;
  }

  private static Module parseModule(File xmlDocument) throws Throwable {
    final var lazyUnmarshaller = UNMARSHALLER.get();

    if (lazyUnmarshaller.hasError()) {
      throw lazyUnmarshaller.getError();
    }

    try (var br = new BufferedReader(new FileReader(xmlDocument))) {
      final var xmlStreamReader = XML_INPUT_FACTORY.get().createXMLStreamReader(br);

      return (Module) lazyUnmarshaller.getItem().unmarshal(xmlStreamReader);
    }
  }
}
