package by.arhor.university.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

public class Runner {

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
//  private void processFolder(String name, Connection connection) throws IOException, SQLException {
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
//  private void processFolder(File folder, Connection connection) throws IOException, SQLException {
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
//              .orElseThrow(() -> new RuntimeException("File " + fileNameToExecute + "not found"));
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
//          throw new RuntimeException("There is no exec-manifest.txt in the directory " + directory.getName());
//        }
//        manifest = file;
//      }
//    }
//
//    if (manifest == null) {
//      throw new RuntimeException("More than one exec-manifest.txt present in the directory " + directory.getName());
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

  public static void main(String[] args) throws Throwable {
//    final var app = new Runner();
//    app.execute();



  }

}
