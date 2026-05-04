package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Inventory;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;
import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;

import java.sql.*;

public class InventoryDaoImpl implements InventoryDao{
  
  @Override
  public int addInventory(Inventory inventory) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if Inventory exists
      // with this inventoryID and execute it.
      String MySQLSelect = "SELECT * FROM inventory WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(MySQLSelect);
      preparedStatement.setInt(1, inventory.getId());
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF inventory exists
      // Throw an InventoryDaoException with the message "Inventory already exists."
      if(resultSet.isBeforeFirst()) {
        throw new InventoryDaoException("Inventory already exists.");
      } else {
        // ELSE
        // Prepare an set statement to add this inventory to the database and execute it.
        String mySqlInsert = "INSERT INTO inventory (ID, TypeID, Count) VALUES (?, ?, ?);";
        PreparedStatement preparedStatementInsert = connection.prepareStatement(mySqlInsert);
        preparedStatementInsert.setInt(1, inventory.getId());
        preparedStatementInsert.setInt(2, inventory.getTypeId());
        preparedStatementInsert.setInt(3, inventory.getCount());
        // Return the inventoryID.
        preparedStatementInsert.executeUpdate();
        preparedStatementInsert.close();
        connection.close();
        
        return inventory.getId();
        // ENDIF
      }
    } catch (Exception e) {
      throw new InventoryDaoException(e.getMessage());
    }
  }
  
  @Override
  public boolean updateInventory(Inventory inventory) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if Inventory exists
      // with this inventoryID and execute it.
      
      String MySQLSelect = "SELECT * FROM inventory WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(MySQLSelect);
      preparedStatement.setInt(1, inventory.getId());
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF inventory exists
      // Prepare an update statement to update this inventory in the database and execute it.
      if(resultSet.isBeforeFirst()) {
        String mySqlUpdate = "UPDATE inventory SET TypeID = ?, Count = ? WHERE ID = ?;";
        PreparedStatement preparedStatementUpdate = connection.prepareStatement(mySqlUpdate);
        preparedStatementUpdate.setInt(1, inventory.getTypeId());
        preparedStatementUpdate.setInt(2, inventory.getCount());
        preparedStatementUpdate.setInt(3, inventory.getId());
        preparedStatementUpdate.executeUpdate();
        preparedStatementUpdate.close();
        connection.close();
        
        return true;
      
      } else {
        throw new InventoryDaoException("Inventory does not exists.");
      }
      // ELSE
      // Throw an InventoryDaoException with the message "Inventory does not exist."
      // ENDIf
    } catch (SQLException | ClassNotFoundException e) {
      throw new InventoryDaoException(e.getMessage());
    }
  
  }
  
  @Override
  public int deleteInventoryById(int id) {
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if Inventory exists
      // with this inventoryID and execute it.
      
      String MySQLSelect = "SELECT * FROM inventory WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(MySQLSelect);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF inventory exists
      if(resultSet.isBeforeFirst()) {
        // Store the inventoryID in a variable
        resultSet.next();
        int returnId = resultSet.getInt("ID");
        // Prepare a delete statement to delete this inventory from the database and execute it.
        // Return the inventoryID.
        String MySQLDelete = "DELETE FROM inventory WHERE ID = ?;";
        PreparedStatement preparedStatementDelete = connection.prepareStatement(MySQLDelete);
        preparedStatementDelete.setInt(1, returnId);
        preparedStatementDelete.executeUpdate();
        
        preparedStatementDelete.close();
        connection.close();
        return returnId;
      } else {
        throw new InventoryDaoException("Inventory does not exists.");
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new InventoryDaoException(e.getMessage());
    }
    
  }
  
  @Override
  public Inventory retrieveById(int id) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see if Inventory exists
      // with this inventoryID and execute it
      String MySQLSelect = "SELECT * FROM inventory WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(MySQLSelect);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF inventory exists
      if(resultSet.isBeforeFirst()) {
        // Move cursor to the result
        resultSet.next();
        // Create an inventory object with the data from the result and return it.
        Inventory inventory = new Inventory(resultSet.getInt("ID"), resultSet.getInt("TypeID"), resultSet.getInt("Count"));
        return inventory;
      } else {
        // ELSE
        // Throw an InventoryDaoException with the message "Inventory does not exist.
        throw new InventoryDaoException("Inventory does not exists.");
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new InventoryDaoException(e.getMessage());
    }
  }
}
