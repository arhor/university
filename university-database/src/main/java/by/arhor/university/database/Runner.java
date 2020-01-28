package by.arhor.university.database;

import java.io.File;
import java.net.URL;
import java.sql.Connection;

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
    final var engine = ConnectionPool.getInstance();

    try (var connection = engine.getConnection()) {
      System.out.println(connection);
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
