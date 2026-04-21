package com.assemble.java.assemblecodebase.dao;

public class SessionDaoImpl implements SessionDao {
  @Override
  public String createSession(int userId) {
    
    // run LoginToken.generate() and store the String it returns as sessionId
    
    // Get a connection to the database
    
    // Prepare a MySQL select statement to check for existing sessions
      // with the passed in userId.
    
    // Execute the select statement
    
    // IF data exists for this userId, prepare a MySQL update statement to update the sessionId
      // with the new sessionId, and execute it.
    
    // ELSE prepare an insert MySQL statement and execute it.
    
    // Return sessionId
    return "";
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
