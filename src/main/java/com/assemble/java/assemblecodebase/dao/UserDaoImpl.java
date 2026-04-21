package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.User;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {
  
  @Override
  public int addUser(User user) throws UserDaoException {
  
    // Get a connection to the database
    try {
      Connection conn = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a user exists
      // with this username and execute it.
      String mySqlSelectExists = "SELECT * FROM user WHERE username = ?";
      
      PreparedStatement preparedStatement = conn.prepareStatement(mySqlSelectExists);
      
      // Set the username parameter in the prepared statement to the username of the passed in user
      preparedStatement.setString(1, user.getUsername());
      
      ResultSet results = preparedStatement.executeQuery();
      
      // IF a user exists for this username
      if (results.isBeforeFirst()) {
        // Throw a UserDaoException with the message "User already exists."
        throw new UserDaoException("User already exists");
        
      } else {
        // ELSE
        // Prepare an insert statement to add this user to the database and execute it.
        String mySqlInsert = "INSERT INTO user (username, permissionId, firstName, lastName, passwordHash) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = conn.prepareStatement(mySqlInsert);
        
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setInt(2, user.getPermissionId());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getPasswordHash());
        preparedStatement.executeUpdate();
        
        // Prepare a select statement to get the newly created userID and execute it.
        String mySqlSelectId = "SELECT ID, passwordHash FROM user WHERE username = ?";
        preparedStatement = conn.prepareStatement(mySqlSelectId);
        preparedStatement.setString(1, user.getUsername());
        results = preparedStatement.executeQuery();
        
        if (results.isBeforeFirst()) {
          
          results.next();
          
          int id = results.getInt("id");
          String passwordIn =  results.getString("passwordHash");
          // Salt the password with the userID
          String saltedPassword = passwordIn + id;
          // Re-hash the password with the new salt
          String passwordOut = DigestUtils.sha256Hex(saltedPassword);
          
          // Prepare an update statement to update the password with the new hash and execute it.
          String mySqlUpdate = "UPDATE user SET passwordHash = ? WHERE username = ?";
          preparedStatement = conn.prepareStatement(mySqlUpdate);
          preparedStatement.setString(1, passwordOut);
          preparedStatement.setString(2, user.getUsername());
          preparedStatement.executeUpdate();
          
          // Cleanup
          results.close();
          preparedStatement.close();
          conn.close();
          
          return id;
          // Return the userID.
          // ENDIF
        } else {
          throw new UserDaoException("User was not created successfully.");
        }
      }
    } catch (Exception e) {
      throw new UserDaoException(e.getMessage());
    }
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
