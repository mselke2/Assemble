package com.assemble.java.assemblecodebase.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLUtility {
  public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
  
  public static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/assemble?user=AssembleAdmin&password=kM.l79R1lhD";
  
  // This connection string is for testing only on amber's machine
//   public static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/assemble?user=root&password=68741Cub";

  public static Connection createConnection() throws ClassNotFoundException, SQLException {

    // Set the driver
    Class.forName(DRIVER_NAME);

    // Return a connection using the previously set driver and the connection url.
    return DriverManager.getConnection(CONNECTION);

  }
}
