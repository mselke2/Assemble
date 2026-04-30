package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Inventory;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;
import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryDaoImpl implements InventoryDao{
  
  @Override
  public int addInventory(Inventory inventory) {
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
