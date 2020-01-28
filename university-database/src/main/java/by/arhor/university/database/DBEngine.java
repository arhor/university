package by.arhor.university.database;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import by.arhor.core.Lazy;

public final class DBEngine {

  private static final String DB_URL = "jdbc:sqlserver://localhost:1433;database=university;encrypt=false;loginTimeout=30;";
  private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
  private static final String DB_USERNAME = "sa";
  private static final String DB_PASSWORD = "Admin1234";
  private static final int DB_CONNECTIONS = 4;

  private static final Lazy<DBEngine> INSTANCE = Lazy.evalSafe(DBEngine::new);

  private final ArrayBlockingQueue<Connection> freeConnections;
  private final ArrayBlockingQueue<Connection> busyConnections;
  private final Throwable error;

  public static DBEngine getInstance() {
    return INSTANCE.get();
  }

  private DBEngine() {
    ArrayBlockingQueue<Connection> freeConnectionsTemp;
    ArrayBlockingQueue<Connection> busyConnectionsTemp;
    Throwable errorTemp;
    try {
      Class.forName(DB_DRIVER);
      freeConnectionsTemp = new ArrayBlockingQueue<>(DB_CONNECTIONS);
      busyConnectionsTemp = new ArrayBlockingQueue<>(DB_CONNECTIONS);

      final Class<?>[] CLASSES = { Connection.class };
      final ClassLoader classLoader = getClass().getClassLoader();

      for (int i = 0; i < DB_CONNECTIONS; i++) {
        final var connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        final var proxy = Proxy.newProxyInstance(
            classLoader,
            CLASSES,
            invocationHandlerFor(connection)
        );

        freeConnectionsTemp.add((Connection) proxy);
      }

      errorTemp = null;
    } catch (ClassNotFoundException | SQLException e) {
      errorTemp = e;
      freeConnectionsTemp = null;
      busyConnectionsTemp = null;
    }
    freeConnections = freeConnectionsTemp;
    busyConnections = busyConnectionsTemp;
    error = errorTemp;
  }

  public final Connection getConnection() throws Throwable {
    if (error == null) {
      final var connection = freeConnections.take();
      busyConnections.put(connection);
      return connection;
    }
    throw error;
  }

  private InvocationHandler invocationHandlerFor(Connection connection) {
    return (obj, method, args) -> {
      if ("close".equals(method.getName())) {
        freeConnections.put(connection);
        return null;
      } else {
        return method.invoke(obj, args);
      }
    };
  }

}
