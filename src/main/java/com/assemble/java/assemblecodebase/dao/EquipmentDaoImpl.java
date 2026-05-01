package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;
import com.assemble.java.assemblecodebase.model.Inventory;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentDaoImpl implements EqipmentDao{
  
  
  @Override
  public int addEquipment(Equipment equipment) {
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see if Equipment exists
      // with this ID and execute it.
      String sqlSelect = "SELECT * FROM inventory WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
      preparedStatement.setInt(1, equipment.getId());
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF Equipment exists
      if (resultSet.isBeforeFirst()) {
        // Throw an EquipmentDaoException with the message "Equipment already exists."
        throw new EquipmentDaoException("Equipment already exists.");
      } else {
        // ELSE
        // Prepare an insert statement to add this equipment to the database and execute it.
        String mySqlInsert = "INSERT INTO inventory (ID, TypeID, Count) VALUES (?, ?, ?);";
        // Prepare a select statement to get the newly created equipmentID and execute it.
        preparedStatement = connection.prepareStatement(mySqlInsert);
        preparedStatement.setInt(1, equipment.getId());
        preparedStatement.setInt(2, equipment.getTypeId());
        preparedStatement.setInt(3, equipment.getCount());
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
  public void updateEquipment(Equipment equipment) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if Equipment exists
      // with this typeID and execute it.
    
    // IF Equipment exists
      // Prepare an update statement to update this equipment in the database and execute it.
    
    // ELSE
      // Throw an EquipmentDaoException with the message "Equipment does not exist."
    
    // ENDIF
  }
  
  @Override
  public int deleteEquipmentById(int id) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if Equipment exists
      // with this typeID and execute it.
    
    // IF Equipment exists
      // Store the equipmentID in a variable
      // Prepare a delete statement to delete this equipment from the database and execute it.
      // Return the equipmentID.
    
    // ELSE
      // Throw an EquipmentDaoException with the message "Equipment does not exist."
    
    // ENDIF
    return 0;
  }
  
  @Override
  public Equipment retrieveById(int id) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if Equipment exists
      // with this typeID and execute it.
    
    // IF Equipment exists
      // Move the cursor to the first result
      // Create an Equipment object with the data from the result and return it.
    
    // ELSE
      // Throw an EquipmentDaoException with the message "Equipment does not exist."
    
    // ENDIF
    
    return null;
  }
}
