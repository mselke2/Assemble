package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.utility.MySQLUtility;

import java.sql.*;

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
  public void retrieve(Date date) {
  
  }
  
  @Override
  public void delete(Date date) {
  
  }
}
