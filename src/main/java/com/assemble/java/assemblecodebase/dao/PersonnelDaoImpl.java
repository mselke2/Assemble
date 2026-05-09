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
  public int retrieve(Date date) {
    
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
        throw new PersonnelDaoException("No personnel record found for date " + date);
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new PersonnelDaoException("Failed to retrieve personnel count for date " + date);
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
        Personnel personnel = new Personnel();
        personnel.setId(result.getInt("ID"));
        personnel.setDate(result.getDate("date"));
        personnel.setCount(result.getInt("Count"));
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
