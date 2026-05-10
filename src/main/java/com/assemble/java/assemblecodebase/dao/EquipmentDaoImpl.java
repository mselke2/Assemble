package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentDaoImpl implements EquipmentDao {
  
  
  @Override
  public int addEquipment(Equipment equipment) {
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see if Equipment exists
      // with this ID and execute it.
      String sqlSelect = "SELECT * FROM equipment WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
      preparedStatement.setInt(1, equipment.getId());
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF Equipment exists
      if (resultSet.isBeforeFirst()) {
        // Throw an EquipmentDaoException with the message "Equipment already exists."
        throw new EquipmentDaoException("Equipment already exists.");
      } else {
        // ELSE
        // Prepare an set statement to add this equipment to the database and execute it.
        String mySqlInsert = "INSERT INTO equipment (ID, TypeID, Status) VALUES (?, ?, ?);";
        // Prepare a select statement to get the newly created equipmentID and execute it.
        preparedStatement = connection.prepareStatement(mySqlInsert);
        preparedStatement.setInt(1, equipment.getId());
        preparedStatement.setInt(2, equipment.getTypeId());
        preparedStatement.setInt(3, equipment.getStatus());
        preparedStatement.executeUpdate();
        
        preparedStatement.close();
        connection.close();
        // Return the equipmentID.
        return equipment.getId();
      }
      // ENDIF
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public boolean updateEquipment(Equipment equipment) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see if Equipment exists
      // with this ID and execute it.
      String sqlSelect = "SELECT * FROM equipment WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
      preparedStatement.setInt(1, equipment.getId());
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF Equipment exists
      if (resultSet.isBeforeFirst()) {
        String mySqlUpdate = "UPDATE equipment SET TypeID = ?, Status = ? WHERE ID = ?;";
        preparedStatement = connection.prepareStatement(mySqlUpdate);
        preparedStatement.setInt(1, equipment.getTypeId());
        preparedStatement.setInt(2, equipment.getStatus());
        preparedStatement.setInt(3, equipment.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        
        return true;
        // ELSE
      } else {
        // Throw an EquipmentDaoException with the message "Equipment does not exist."
        throw new EquipmentDaoException("Equipment does not exist.");
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    // ENDIF
  }
  
  @Override
  public int deleteEquipmentById(int id) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see if Equipment exists
      // with this typeID and execute it.
      String sqlSelect = "SELECT * FROM equipment WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF Equipment exists
      if (resultSet.isBeforeFirst()) {
        // Store the equipmentID in a variable
        resultSet.next();
        int returnId = resultSet.getInt("ID");
        // Prepare a delete statement to delete this equipment from the database and execute it.
        String mySqlDelete = "DELETE FROM equipment WHERE ID = ?;";
        preparedStatement = connection.prepareStatement(mySqlDelete);
        preparedStatement.setInt(1, returnId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        // Return the equipmentID.
        return returnId;
      } else {
        // ELSE
        // Throw an EquipmentDaoException with the message "Equipment does not exist."
        throw new EquipmentDaoException("Equipment does not exist.");
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Equipment retrieveById(int id) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if Equipment exists
      // with this typeID and execute it.
      String sqlSelect = "SELECT * FROM equipment WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF Equipment exists
      // Move the cursor to the first result
      // Create an Equipment object with the data from the result and return it.
      if (resultSet.isBeforeFirst()) {
        resultSet.next();
        
        Equipment equipment = new Equipment(resultSet.getInt("ID"), resultSet.getInt("TypeID"), resultSet.getInt("Status"));
        preparedStatement.close();
        connection.close();
        return equipment;
      } else {
        // ELSE
        // Throw an EquipmentDaoException with the message "Equipment does not exist."
        throw new EquipmentDaoException("Equipment does not exist.");
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
