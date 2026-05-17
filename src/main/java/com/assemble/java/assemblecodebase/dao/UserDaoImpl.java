package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.User;
import com.assemble.java.assemblecodebase.model.UserPermission;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
  
  @Override
  public int addUser(User user) throws UserDaoException {
  
    // Get a connection to the database
    try {
      Connection conn = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a user exists
      // with this username and execute it.
      String mySqlSelectExists = "SELECT * FROM user WHERE username = ?;";
      
      PreparedStatement preparedStatement = conn.prepareStatement(mySqlSelectExists);
      
      // Set the username parameter in the prepared statement to the username of the passed in user
      preparedStatement.setString(1, user.getUsername());
      
      ResultSet results = preparedStatement.executeQuery();
      
      // IF a user exists for this username
      if (results.isBeforeFirst()) {
        // Throw a UserDaoException with the message "A user with that username already exists."
        throw new UserDaoException("A user with that username already exists.");
        
      } else {
        // ELSE
        // Prepare a set statement to add this user to the database and execute it.
        String mySqlInsert = "INSERT INTO user (username, permissionId, firstName, lastName, passwordHash) VALUES (?, ?, ?, ?, ?);";
        preparedStatement = conn.prepareStatement(mySqlInsert);
        
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setInt(2, user.getPermissionId());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getPasswordHash());
        preparedStatement.executeUpdate();
        
        // Prepare a select statement to get the newly created userID and execute it.
        String mySqlSelectId = "SELECT ID, passwordHash FROM user WHERE username = ?;";
        preparedStatement = conn.prepareStatement(mySqlSelectId);
        preparedStatement.setString(1, user.getUsername());
        results = preparedStatement.executeQuery();
        
        if (results.isBeforeFirst()) {
          
          results.next();
          
          int id = results.getInt("ID");
          String passwordIn =  results.getString("passwordHash");
          // Salt the password with the userID
          String saltedPassword = passwordIn + id;
          // Re-hash the password with the new salt
          String passwordOut = DigestUtils.sha256Hex(saltedPassword);
          
          // Prepare an update statement to update the password with the new hash and execute it.
          String mySqlUpdate = "UPDATE user SET passwordHash = ? WHERE username = ?;";
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
          conn.close();
          throw new UserDaoException("User was not created successfully.");
        }
      }
    } catch (Exception e) {
      throw new UserDaoException(e.getMessage());
    }
  }
  
  @Override
  public void updateUser(String username, User user) {
    try {
      // Get a connection to the database
      Connection conn = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a user exists
      // with this username and execute it.
      String mySqlSelectExists = "SELECT * FROM user WHERE username = ?;";
      PreparedStatement preparedStatement = conn.prepareStatement(mySqlSelectExists);
      preparedStatement.setString(1, username);
      ResultSet results = preparedStatement.executeQuery();
      
      // IF a user exists for this username
      if (results.isBeforeFirst()) {
        // Salt the new password with the userID
        results.next();
        
        if(user.getPasswordHash() != null) {
          String passwordIn = user.getPasswordHash();
          String saltedPassword = passwordIn + results.getInt("ID");
          String passwordOut = DigestUtils.sha256Hex(saltedPassword);
          // Re-hash the password with the new salt.
          // Prepare an update statement to update this user in the database and execute it.
          String mySqlUpdate = "UPDATE user SET username = ?, PermissionID = ?, FirstName = ?, LastName = ?, passwordHash = ? WHERE username = ?;";
          preparedStatement = conn.prepareStatement(mySqlUpdate);
          preparedStatement.setString(1, user.getUsername());
          preparedStatement.setInt(2, user.getPermissionId());
          preparedStatement.setString(3, user.getFirstName());
          preparedStatement.setString(4, user.getLastName());
          preparedStatement.setString(5, passwordOut);
          preparedStatement.setString(6, username);
          preparedStatement.executeUpdate();
        } else {
          String mySqlUpdate = "UPDATE user SET Username = ?, PermissionID = ?, FirstName = ?, LastName = ? WHERE username = ?;";
          preparedStatement = conn.prepareStatement(mySqlUpdate);
          preparedStatement.setString(1, user.getUsername());
          preparedStatement.setInt(2, user.getPermissionId());
          preparedStatement.setString(3, user.getFirstName());
          preparedStatement.setString(4, user.getLastName());
          preparedStatement.setString(5, username);
          preparedStatement.executeUpdate();
          
        }
        
      } else {
        // ELSE
        // Throw a UserDaoException with the message "User does not exist."
        conn.close();
        throw new UserDaoException("User does not exist.");
      }
      // ENDIF
    } catch (Exception e) {
      throw new UserDaoException(e.getMessage());
    }
  }
  
  @Override
  public void deleteUser(int userId) {

    try {
      Connection conn = MySQLUtility.createConnection();

      // Get a connection to the database

      // Prepare a select statement to see if a user exists
      // with this userId and execute it.
      String mySqlSelectExists = "SELECT * FROM user WHERE ID = ?;";
      PreparedStatement preparedStatement = conn.prepareStatement(mySqlSelectExists);
      preparedStatement.setInt(1, userId);
      ResultSet results = preparedStatement.executeQuery();

      // IF a user exists
      if (results.isBeforeFirst()) {
        // Prepare a delete statement to delete this user from the database and execute it.
        String mySqlDelete = "DELETE FROM user WHERE ID = ?;";
        preparedStatement = conn.prepareStatement(mySqlDelete);
        preparedStatement.setInt(1, userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        conn.close();
      } else {
        // ELSE
        // Throw a UserDaoException with the message "User does not exist."
        throw new UserDaoException("User does not exist.");
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new UserDaoException(e.getMessage());
    }
  }
  
  @Override
  public int retrieveWithLogin(String username, String password) {

    try {
      Connection conn = MySQLUtility.createConnection();

      // Get a connection to the database

      // Prepare a select statement to see if a user exists with this username and execute it
      String mySqlSelectExists = "SELECT * FROM user WHERE username = ?;";
      PreparedStatement preparedStatement = conn.prepareStatement(mySqlSelectExists);
      preparedStatement.setString(1, username);
      ResultSet results = preparedStatement.executeQuery();

      // IF a user exists
      if (results.isBeforeFirst()) {
        results.next();
        // Salt the password with the userID
        int id = results.getInt("ID");
        String passwordIn =  password + id;
        // Re-hash the password with the new salt.
        passwordIn = DigestUtils.sha256Hex(passwordIn);
        // IF the hashed password matches the password in the database
        if(passwordIn.equals(results.getString("passwordHash"))) {
          conn.close();
          return id;
        } else {
          // ELSE
          // Throw a UserDaoException with the message "Incorrect password."
          conn.close();
          throw new UserDaoException("Username or password does not match.");
        }
        // ENDIF
      } else {
        // ELSE
        // Throw a UserDaoException with the message "User does not exist."
        // ENDIF
        throw new UserDaoException("Username or password does not match.");
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new UserDaoException(e.getMessage());
    }
  }

  @Override
  public List<UserPermission> retrievePermissions() {
    List<UserPermission> permissions = new ArrayList<>();

    try {
      Connection conn = MySQLUtility.createConnection();

      String mySqlSelectAll = "SELECT * FROM userpermission;";
      Statement statement = conn.createStatement();
      ResultSet result = statement.executeQuery(mySqlSelectAll);
      while (result.next()) {
        UserPermission perm = new UserPermission();
        perm.setId(result.getInt("ID"));
        perm.setDescription(result.getString("Description"));
        permissions.add(perm);
      }

      statement.close();
      conn.close();
    } catch (SQLException | ClassNotFoundException e) {
      throw new UserDaoException(e.getMessage());
    }

    return permissions;
  }
  
  public User[] retrieveAll() {
    
    try {
      Connection connection = MySQLUtility.createConnection();
      
      String mySqlSelectAll = "SELECT * FROM user;";
      PreparedStatement statement = connection.prepareStatement(mySqlSelectAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet results = statement.executeQuery();
      User[] users;
      
      if (results.isBeforeFirst()) {
        results.last();
        int records =  results.getRow();
        users = new User[records];
        results.beforeFirst();
        
        for (int i = 0; i < records; i++) {
          results.next();
          
          int id = results.getInt("ID");
          String username = results.getString("Username");
          String firstName = results.getString("FirstName");
          String lastName = results.getString("LastName");
          int permissionId = results.getInt("PermissionID");
          
          User user = new User();
          user.setId(id);
          user.setUsername(username);
          user.setFirstName(firstName);
          user.setLastName(lastName);
          user.setPermissionId(permissionId);
          
          users[i] = user;
        }
        
        results.close();
        statement.close();
        connection.close();
        
        return users;
      } else {
        throw new UserDaoException("Users do not exist.");
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new UserDaoException("Error retrieving user list. " + e.getMessage());
    }
  }
  
  public User retrieveByUsername(String userToGrab) {
    
    try {
      Connection connection =  MySQLUtility.createConnection();
      String mySqlSelectById = "SELECT * FROM user WHERE Username = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelectById);
      preparedStatement.setString(1, userToGrab);
      ResultSet results = preparedStatement.executeQuery();
      
      if (results.isBeforeFirst()) {
        results.next();
        
        int userId = results.getInt("ID");
        String username = results.getString("Username");
        String firstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        int permissionId = results.getInt("PermissionID");
        String passwordHash = results.getString("passwordHash");
        
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPermissionId(permissionId);
        user.setPasswordHash(passwordHash);
        
        results.close();
        preparedStatement.close();
        connection.close();
        
        return user;
        
      } else  {
        connection.close();
        throw new UserDaoException("User does not exist.");
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new UserDaoException("Error retrieving user." + e.getMessage());
    }
    
  }

  public User retrieveById(int id) {
    try {
      Connection connection = MySQLUtility.createConnection();
      String mySqlSelectById = "SELECT * FROM user WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelectById);
      preparedStatement.setInt(1, id);
      ResultSet results = preparedStatement.executeQuery();
      if (results.next()) {
        User user = new User();
        user.setId(results.getInt("ID"));
        user.setUsername(results.getString("Username"));
        user.setFirstName(results.getString("FirstName"));
        user.setLastName(results.getString("LastName"));
        user.setPermissionId(results.getInt("PermissionID"));

        connection.close();
        return user;
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new UserDaoException(e.getMessage());
    }

    return null;
  }
}
