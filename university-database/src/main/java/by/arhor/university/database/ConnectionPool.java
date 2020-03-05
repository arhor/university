package by.arhor.university.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

import by.arhor.university.core.pattern.lazy.Lazy;

public final class ConnectionPool implements AutoCloseable {

  private static final String DEFAULT_URL      = "jdbc:sqlserver://localhost:1433;database=university;";
  private static final String DEFAULT_DRIVER   = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
  private static final String DEFAULT_USERNAME = "sa";
  private static final String DEFAULT_PASSWORD = "Admin1234";
  private static final int    DEFAULT_POOLSIZE = 10;

  private static final String DB_URL      = "db.url";
  private static final String DB_DRIVER   = "db.driver";
  private static final String DB_USERNAME = "db.username";
  private static final String DB_PASSWORD = "db.password";
  private static final String DB_POOLSIZE = "db.poolsize";

  private static final Lazy<ConnectionPool> INSTANCE = Lazy.evalSafe(ConnectionPool::new);

  private final ArrayBlockingQueue<Connection> freeConnections;
  private final ArrayBlockingQueue<Connection> busyConnections;
  private final Properties config;
  private final Throwable error;

  public static ConnectionPool getInstance() {
    return INSTANCE.get();
  }

  private ConnectionPool() {
    final var classLoader = getClass().getClassLoader();
    final var properties = new Properties();

    ArrayBlockingQueue<Connection> freeConnectionsTemp;
    ArrayBlockingQueue<Connection> busyConnectionsTemp;
    Throwable errorTemp;

    try {
      final var configFile = classLoader.getResourceAsStream("database.properties");
      if (configFile != null) {
        properties.load(configFile);
      } else {
        properties.put(DB_URL, DEFAULT_URL);
        properties.put(DB_DRIVER, DEFAULT_DRIVER);
        properties.put(DB_USERNAME, DEFAULT_USERNAME);
        properties.put(DB_PASSWORD, DEFAULT_PASSWORD);
        properties.put(DB_POOLSIZE, DEFAULT_POOLSIZE);
      }

      final int dbConnections = Integer.parseInt(properties.getProperty(DB_POOLSIZE));

      Class.forName(properties.getProperty(DB_DRIVER));

      freeConnectionsTemp = new ArrayBlockingQueue<>(dbConnections);
      busyConnectionsTemp = new ArrayBlockingQueue<>(dbConnections);

      final Class<?>[] classes = { Connection.class, ProxyConnection.class };

      for (int i = 0; i < dbConnections; i++) {
        final var connection = DriverManager.getConnection(
            properties.getProperty(DB_URL),
            properties.getProperty(DB_USERNAME),
            properties.getProperty(DB_PASSWORD)
        );

//        final var proxy = Proxy.newProxyInstance(
//            classLoader,
//            classes,
//            invocationHandlerFor(connection)
//        );

        freeConnectionsTemp.add(connection);
      }

      errorTemp = null;
    } catch (ClassNotFoundException | SQLException | IOException e) {
      errorTemp = e;
      freeConnectionsTemp = null;
      busyConnectionsTemp = null;
    }
    freeConnections = freeConnectionsTemp;
    busyConnections = busyConnectionsTemp;
    config = properties;
    error = errorTemp;
  }

  public final Connection getConnection() throws Throwable {
    if (error == null) {
      Connection connection = null;
      try {
        connection = freeConnections.take();
        busyConnections.put(connection);
        return connection;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      return connection;
    }
    throw error;
  }

  public final void releaseConnection(Connection connection) {
    try {
      if (connection instanceof ProxyConnection) {
        busyConnections.remove(connection);
        if (!connection.getAutoCommit()) {
          connection.setAutoCommit(true);
        }
        freeConnections.put((ProxyConnection) connection);
      }

    } catch (SQLException e) {
      // fuf
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

//  private InvocationHandler invocationHandlerFor(Connection connection) {
//    return (obj, method, args) -> {
//      if ("close".equals(method.getName())) {
//        freeConnections.put(connection);
//        System.out.println("fake");
//        return null;
//      } else if ("_close".equals(method.getName())) {
//        connection.close();
//        System.out.println("real");
//        return null;
//      } else {
//        System.out.println(method);
//        try {
//          return method.invoke(obj, args);
//        } catch (Throwable t) {
//          System.out.println(t);
//          Thread.currentThread().interrupt();
//          return null;
//        }
//      }
//    };
//  }

  @Override
  public void close() throws Exception {
    final int dbConnections = Integer.parseInt(config.getProperty(DB_POOLSIZE));

    for (int i = 0; i < dbConnections; i++) {
      try {
        freeConnections.take().close();
      } catch (SQLException e) {
        // fuf
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    Enumeration<Driver> drivers = DriverManager.getDrivers();

    while (drivers.hasMoreElements()) {
      try {
        java.sql.Driver driver = drivers.nextElement();
        DriverManager.deregisterDriver(driver);
      } catch (SQLException e) {
        // fuf
      }
    }
  }

  private interface ProxyConnection extends Connection {
    void _close() throws SQLException;
  }

}
