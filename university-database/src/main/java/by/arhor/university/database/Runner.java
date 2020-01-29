package by.arhor.university.database;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

import by.arhor.university.database.parser.Directive;

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

              try (var scan = new Scanner(innerFile)) {
                int row = 0;
                while (scan.hasNext()) {
                  final var line = scan.nextLine();

                  if (Directive.DEPENDENCIES.matches(line)) {
                    System.out.println("dependencies directive!");
                  } else if (Directive.MAIN.matches(line)) {
                    System.out.println("main directive!");
                  } else if (Directive.CREATE.matches(line)) {
                    System.out.println("create directive!");
                  } else if (Directive.INIT.matches(line)) {
                    System.out.println("init directive!");
                  }
                  System.out.println((row++) + ": " + line);
                }
              }

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
