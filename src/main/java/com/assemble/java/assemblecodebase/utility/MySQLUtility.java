package com.assemble.java.assemblecodebase.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLUtility {

  public static final int TIMEOUT = 30;

  public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
  
  public static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/assemble?user=root&password=68741Cub";

  public static Connection createConnection() throws ClassNotFoundException, SQLException {

    // Set the driver
    Class.forName(DRIVER_NAME);

    // Return a connection using the previously set driver and the connectoin url.
    return DriverManager.getConnection(CONNECTION);

  }

  public static void closeConnection(Connection connection, Statement statement) {

    try {

      if (null != connection) {
        connection.close();
      }

      if (null != statement) {
        statement.close();
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }


}
