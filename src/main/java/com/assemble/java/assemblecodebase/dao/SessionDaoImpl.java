package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.utility.LoginToken;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;

import java.sql.*;

public class SessionDaoImpl implements SessionDao {
  @Override
  public String createSession(int userId) {
    
    // run LoginToken.getNewToken() and store the String it returns as sessionId
    String sessionId = LoginToken.getNewToken();
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a MySQL select statement to check for existing sessions
      // with the passed in userId.
      String mySqlSelect = "SELECT * FROM session WHERE UserID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setInt(1, userId);
      // Execute the select statement
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF data exists for this userId, prepare a MySQL update statement to update the sessionId
      // with the new sessionId, and execute it.
      if (resultSet.isBeforeFirst()) {
        String mySqlUpdate = "UPDATE session SET SessionID = ?, LastUsed = ? WHERE UserID = ?;";
        PreparedStatement preparedStatement1 = connection.prepareStatement(mySqlUpdate);
        preparedStatement1.setString(1, sessionId);
        preparedStatement1.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        preparedStatement1.setInt(3, userId);
        preparedStatement1.executeUpdate();
      } else {
        // ELSE prepare an insert MySQL statement and execute it.
        String mySqlInsert = "INSERT INTO session (UserID, SessionID, LastUsed) VALUES (?, ?, ?);";
        PreparedStatement preparedStatement2 = connection.prepareStatement(mySqlInsert);
        preparedStatement2.setInt(1, userId);
        preparedStatement2.setString(2, sessionId);
        preparedStatement2.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        preparedStatement2.executeUpdate();
      }
      preparedStatement.close();
      connection.close();
      // Return sessionId
      return sessionId;
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public int removeSession(String sessionId) {
    
    // run retrieve() to check if sessionId exists in the database.
    
    // IF retrieve() returns a positive number
      // Get a connection to the database
      // Prepare a MySQL delete statement to delete the session with the passed in sessionId, and execute it.
      // Return the userId that was returned by retrieve()
    
    // ELSE return -1
    
    return -1;
  }
  
  @Override
  public int retrieve(String sessionId) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a session exists with the passed in sessionId and execute it.
    
    // IF data exists
      // Return the userId associated with this sessionId
    // ELSE
      // Return -1
    return -1;
  }
}
