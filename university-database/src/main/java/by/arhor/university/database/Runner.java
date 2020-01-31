package by.arhor.university.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

public class Runner {

  private static final Pattern EXEC_STATEMENT =
      Pattern.compile("^(exec:)( ?)(')(\\w+(\\.sql)*)(')$");

  private final File[] folders;

  private Runner() {
    URL resource = getClass().getClassLoader().getResource("sql");
    if (resource == null) {
      throw new IllegalArgumentException("`sql` folder is not found!");
    } else {
      folders = new File(resource.getFile()).listFiles();
    }
  }

  private void processFolder(String name, Connection connection) throws FileNotFoundException, SQLException {
    final var folder = Arrays.stream(folders)
        .filter(File::isDirectory)
        .filter(file -> name.equals(file.getName()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(""));
    processFolder(folder, connection);
  }

  private void execute() throws Throwable {
    Connection connection = ConnectionPool.getInstance().getConnection();

    processFolder("tables", connection);
    processFolder("data", connection);

    connection.close();
  }

  private void processFolder(File folder, Connection connection) throws FileNotFoundException, SQLException {
    final var manifest = findManifest(folder);


    try (var content = new Scanner(manifest);) {

      System.out.println(content);

      final var folderContent = folder.listFiles();

      while (content.hasNextLine()) {
        final var matcher = EXEC_STATEMENT.matcher(content.nextLine());
        if (matcher.matches()) {
          final var fileNameToExecute = matcher.group(4);
          System.out.println("Line matches! Executing " + fileNameToExecute);

          assert folderContent != null;
          final var script = Arrays
              .stream(folderContent)
              .filter(file -> fileNameToExecute.equals(file.getName()))
              .findFirst()
              .orElseThrow(() -> new RuntimeException("File " + fileNameToExecute + "not found"));

          try (var scriptScanner = new Scanner(script)) {
            final var scriptBody = new StringBuilder();

            while (scriptScanner.hasNextLine()) {
              scriptBody.append(scriptScanner.nextLine()).append('\n');
            }

            final var scriptToExecute = scriptBody.toString();

            try (var statement = connection.createStatement()) {

              System.out.println("connection acquired: " + connection);
//              System.out.println("Executing:\n" + scriptToExecute);

              final var result = statement.executeUpdate(scriptToExecute);

              System.out.println(fileNameToExecute + " - " + (result > -1 ? "SUCCESS" : "FAILURE"));

              if (result > -1) {
                connection.commit();
              }
            }
          }
        }
      }
    }
  }

  private File findManifest(@Nonnull File directory) {
    final var files = directory.listFiles();

    if (files == null) {
      throw new RuntimeException("Directory " + directory.getName() + " is empty");
    }

    File manifest = null;

    for (File file : files) {
      if ("exec-manifest.txt".equals(file.getName())) {
        if (manifest != null) {
          throw new RuntimeException("There is no exec-manifest.txt in the directory " + directory.getName());
        }
        manifest = file;
      }
    }

    if (manifest == null) {
      throw new RuntimeException("More than one exec-manifest.txt present in the directory " + directory.getName());
    }

    return manifest;
  }

  public static void main(String[] args) throws Throwable {
    final var app = new Runner();
    app.execute();
  }

}
