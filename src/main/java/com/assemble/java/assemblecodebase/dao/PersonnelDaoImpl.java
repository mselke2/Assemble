package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Personnel;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDaoImpl implements PersonnelDao {
  @Override
  public void set(Date date, int count) {
    
    try {
      Connection connection = MySQLUtility.createConnection();
      String mySqlSelect = "SELECT * FROM personnel WHERE date = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setDate(1, date);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      if (resultSet.isBeforeFirst()) {
        String mySqlUpdate = "UPDATE personnel SET count = ? WHERE date = ?";
        preparedStatement = connection.prepareStatement(mySqlUpdate);
        preparedStatement.setInt(1, count);
        preparedStatement.setDate(2, date);
        preparedStatement.executeUpdate();
      } else {
        String mySqlInsert = "INSERT INTO personnel (date, count) VALUES (?, ?);";
        preparedStatement = connection.prepareStatement(mySqlInsert);
        preparedStatement.setDate(1, date);
        preparedStatement.setInt(2, count);
        preparedStatement.executeUpdate();
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new PersonnelDaoException("Failed to set personnel count for date " + date);
    }
  }
  
  @Override
  public int retrieveCount(Date date) {
    
    try {
      Connection connection = MySQLUtility.createConnection();
      String mySqlSelect = "SELECT * FROM personnel WHERE date = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setDate(1, date);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      if (resultSet.isBeforeFirst()) {
        resultSet.next();
        
        return resultSet.getInt("Count");
      } else {
        return 0;
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new PersonnelDaoException("Failed to retrieve personnel count for date " + date);
    }
  }

  @Override
  public Personnel retrieve(int id) {
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see if Personnel exists
      // with this personnelID and execute it
      String MySQLSelect = "SELECT * FROM personnel WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(MySQLSelect);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      // IF personnel exists
      if(resultSet.isBeforeFirst()) {
        // Move cursor to the result
        resultSet.next();
        // Create a personnel object with the data from the result and return it.
        Personnel personnel = new Personnel(resultSet.getDate("Date"), resultSet.getInt("Count"));
        personnel.setId(resultSet.getInt("ID"));
        return personnel;
      } else {
        // ELSE
        // Throw an PersonnelDaoException with the message "Personnel does not exist."
        throw new PersonnelDaoException("Personnel does not exists.");
      }

    } catch (SQLException | ClassNotFoundException e) {
      throw new PersonnelDaoException(e.getMessage());
    }
  }

  @Override
  public void delete(Date date) {
  
    try {
      Connection connection = MySQLUtility.createConnection();
      String MySqlSelect = "SELECT * FROM personnel WHERE date = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(MySqlSelect);
      preparedStatement.setDate(1, date);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      if (resultSet.isBeforeFirst()) {
        String mySqlDelete = "DELETE FROM personnel WHERE date = ?";
        preparedStatement = connection.prepareStatement(mySqlDelete);
        preparedStatement.setDate(1, date);
        preparedStatement.executeUpdate();
      } else {
        throw new PersonnelDaoException("No personnel record found for date " + date);
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new PersonnelDaoException("Failed to delete personnel record for date " + date);
    }
  }

  @Override
  public List<Personnel> retrieveAll() {
    List<Personnel> personnelList = new ArrayList<>();

    try {
      Connection conn = MySQLUtility.createConnection();

      String mySqlSelectAll = "SELECT * FROM personnel";
      Statement statement = conn.createStatement();
      ResultSet result = statement.executeQuery(mySqlSelectAll);
      while (result.next()) {
        Personnel personnel = new Personnel(result.getDate("date"), result.getInt("Count"));
        personnel.setId(result.getInt("ID"));
        personnelList.add(personnel);
      }

      statement.close();
      conn.close();
    } catch (SQLException | ClassNotFoundException e) {
      throw new PersonnelDaoException(e.getMessage());
    }

    return personnelList;
  }
}
