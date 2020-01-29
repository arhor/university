package by.arhor.university.database;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import by.arhor.university.database.parser.Directive;
import by.arhor.university.database.parser.ScriptParser;

public class Runner {

  private final File[] folders;

  private Runner() {
    URL resource = getClass().getClassLoader().getResource("sql");
    if (resource == null) {
      throw new IllegalArgumentException("`sql` folder is not found!");
    } else {
      folders = new File(resource.getFile()).listFiles();
    }
  }

  public static void main(String[] args) throws Throwable {
    final var app = new Runner();

    for (File file : app.folders) {
      if (file.isDirectory() && "tables".equals(file.getName())) {
        final var innerContent = file.listFiles();
        if (innerContent != null) {
          for (File innerFile : innerContent) {
            if (innerFile.isFile()) {
              System.out.println(innerFile.getName());

              final var parser = ScriptParser.getInstance();

              parser
                  .parseFile(innerFile)
                  .forEach(
                      (directive, script) -> {
                        System.out.println("Parsed directive: " + directive);
                        System.out.println("Inner content:");
                        System.out.println(script);
                      });

              return;
            }
          }
        }
      }
    }

//    init();
//
//    try (
//        var connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//        var statement = connection.createStatement();
//    ) {
//      var resultSet = statement.executeQuery("SELECT * FROM users");
//      var metaData = resultSet.getMetaData();
//      var columnCount = metaData.getColumnCount();
//
//
//      for (int i = 1; i <= columnCount; i++) {
//        System.out.println(metaData.getColumnClassName(i));
//      }
//
//
//      while (resultSet.next()) {
//        for (int i = 1; i <= columnCount; i++) {
//          System.out.println(resultSet.getObject(i));
//        }
//        System.out.println();
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
  }

}
