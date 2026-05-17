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
        // ELSE prepare a set MySQL statement and execute it.
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
  public String removeSession(int userId) {
    
    // run retrieve() to check if sessionId exists in the database.
    
    // IF retrieve() returns a positive number (SessionID)
    String sessionId = retrieve(userId);
    
    if(sessionId != null) {
      // Get a connection to the database
      try {
        Connection connection = MySQLUtility.createConnection();
        // Prepare a MySQL delete statement to delete the session with the passed in sessionId, and execute it.
        String mySqlDelete = "DELETE FROM session WHERE UserID = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(mySqlDelete);
        preparedStatement.setInt(1, userId);
        preparedStatement.executeUpdate();
        connection.close();
        
        // Return the userId that was returned by retrieve()
        return sessionId;
      } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    } else {
      // ELSE return null
      return null;
    }
  }
  
  @Override
  public int retrieve(String sessionId) {
    // Return the userId associated with a sessionId,
    // or -1 if there is no session.
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a session exists with the passed in sessionId and execute it.
      String mySqlSelect = "SELECT * FROM session WHERE SessionID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setString(1, sessionId);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF data exists
      if (resultSet.isBeforeFirst()) {
        resultSet.next();
        int userId = resultSet.getInt("UserID");
        
        stampSession(userId);
        connection.close();
        return userId;
        // Return the userId associated with this sessionId
      } else {
        // ELSE
        // Return -1
        connection.close();
        return -1;
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
  public String retrieve(int userId) {
    // Return the sessionId associated with a userId,
    // or an empty string if there is no session.
    
    try {
      Connection connection = MySQLUtility.createConnection();
      
      String mySqlSelect = "SELECT * FROM session WHERE UserID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setInt(1, userId);
      
      ResultSet resultSet = preparedStatement.executeQuery();
      
      if (resultSet.isBeforeFirst()) {
        resultSet.next();
        stampSession(userId);
        String sessionId = resultSet.getString("SessionID");
        connection.close();
        return sessionId;
        
      } else  {
        connection.close();
        return "";
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  public boolean stampSession(int userId) {
    // Update the LastUsed column for the session associated with the passed in userId to the current time.
    
    try {
      
      Connection connection = MySQLUtility.createConnection();
      String mySqlUpdate = "UPDATE session SET LastUsed = ? WHERE UserID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlUpdate);
      preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
      preparedStatement.setInt(2, userId);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
      return true;
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
