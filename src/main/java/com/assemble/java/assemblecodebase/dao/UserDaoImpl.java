package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.User;

public class UserDaoImpl implements UserDao {
  
  @Override
  public int addUser(User user) throws UserDaoException {
  
    // Get a connection to the database
    
    // Prepare a select statement to see if a user exists
      // with this username and execute it.
    
    // IF a user exists for this username
      // Throw a UserDaoException with the message "User already exists."
    
    // ELSE
      // Prepare an insert statement to add this user to the database and execute it.
      // Prepare a select statement to get the newly created userID and execute it.
      // Salt the password with the userID
      // Re-hash the password with the new salt
      // Prepare an update statement to update the password with the new hash and execute it.
      // Return the userID.
    // ENDIF
    return 0;
  }
  
  @Override
  public void updateUser(User user) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a user exists
      // with this username and execute it.
    
    // IF a user exists for this username
      // IF password is not null
        // Salt the new password with the userID
        // Re-hash the password with the new salt.
      // Prepare an update statement to update this user in the database and execute it.
      // ENDIF
    // ELSE
      // Throw a UserDaoException with the message "User does not exist."
    // ENDIF
  }
  
  @Override
  public void deleteUser(int userId) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a user exists
      // with this userId and execute it.
    
    // IF a user exists
      // Prepare a delete statement to delete this user from the database and execute it.
    
    // ELSE
      // Throw a UserDaoException with the message "User does not exist."
    // ENDIF
  }
  
  @Override
  public int retrieve(String username, String password) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a user exists with this username and execute it
    
    // IF a user exists
      // Salt the password with the userID
      // Re-hash the password with the new salt.
      // IF the hashed password matches the password in the database
        // Return the userID
      // ELSE
        // Throw a UserDaoException with the message "Incorrect password."
      // ENDIF
    // ELSE
      // Throw a UserDaoException with the message "User does not exist."
    // ENDIF
    return 0;
  }
}
