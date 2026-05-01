package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;

public class EquipmentDaoImpl implements EqipmentDao{
  
  
  @Override
  public int addEquipment(int id, int typeId, int count) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if Equipment exists
      // with this typeID and execute it.
  
    // IF Equipment exists
      // Throw an EquipmentDaoException with the message "Equipment already exists."
    
    // ELSE
      // Prepare an insert statement to add this equipment to the database and execute it.
      // Prepare a select statement to get the newly created equipmentID and execute it.
      // Return the equipmentID.
    
    // ENDIF
    return 0;
  }
  
  @Override
  public void updateEquipment(int id, int typeId, int count) {
    
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
