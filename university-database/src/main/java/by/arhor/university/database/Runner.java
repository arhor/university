package by.arhor.university.database;

import java.io.File;
import java.net.URL;

public class Runner {

  public static void main(String[] args) throws Throwable {
    URL resource = Runner.class.getClassLoader().getResource("sql");
    if (resource == null) {
      throw new IllegalArgumentException("`sql` folder is not found!");
    } else {
      DBProjectModel
          .fromRootDirectory(new File(resource.getFile()))
          .executeScripts();
    }
  }
}
