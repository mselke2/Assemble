package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Inventory;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;
import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
        // Prepare an insert statement to add this inventory to the database and execute it.
        String mySqlInsert = "INSERT INTO inventory (TypeID, Count) VALUES (?, ?);";
        PreparedStatement preparedStatementInsert = connection.prepareStatement(mySqlInsert,  Statement.RETURN_GENERATED_KEYS);
        preparedStatementInsert.setInt(1, inventory.getTypeId());
        preparedStatementInsert.setInt(2, inventory.getCount());
        // Return the inventoryID.
        preparedStatementInsert.executeUpdate();
        
        ResultSet generatedKeys = preparedStatementInsert.getGeneratedKeys();
        if(generatedKeys.next()) {
          return generatedKeys.getInt(1);
        }
        // ENDIF
      }
    } catch (Exception e) {
      throw new InventoryDaoException(e.getMessage());
    }
    
    return 0;
  }
  
  @Override
  public void updateInventory(Inventory inventory) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if Inventory exists
      // with this inventoryID and execute it.
    
    // IF inventory exists
      // Prepare an update statement to update this inventory in the database and execute it.
    
    // ELSE
      // Throw an InventoryDaoException with the message "Inventory does not exist."
    // ENDIf
  
  }
  
  @Override
  public int deleteInventory(Inventory inventory) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if Inventory exists
      // with this inventoryID and execute it.
    
    // IF inventory exists
      // Store the inventoryID in a variable
      // Prepare a delete statement to delete this inventory from the database and execute it.
      // Return the inventoryID.
    // ELSE
      // Throw an InventoryDaoException with the message "Inventory does not exist."
    // ENDIF
    
    return 0;
  }
  
  @Override
  public Inventory retrieve(int id) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if Inventory exists
      // with this inventoryID and execute it.
    
    // IF inventory exists
      // Move cursor to the result
      // Create an inventory object with the data from the result and return it.
    
    // ELSE
      // Throw an InventoryDaoException with the message "Inventory does not exist."
    // ENDIF
    
    return null;
  }
}
