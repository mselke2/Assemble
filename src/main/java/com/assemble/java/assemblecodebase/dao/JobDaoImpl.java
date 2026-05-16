package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Job;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class JobDaoImpl implements JobDao {
  
  private String prerequisitesError = "";
  
  // These are 2D arrays used as a kind of database table on the server.
  // The structure looks like this:
  // {
  //   {Col 0,           Col 1,           Col 2,           ...}
  //   ------------------------------------------------------------
  //   {TypeID1,         TypeID2,         TypeID3,         ...},  |   {Row 0}
  //   {RequiredCount1,  RequiredCount2,  RequiredCount3,  ...},  |   {Row 1}
  //   {AvailableCount1, AvailableCount2, AvailableCount3, ...},  |   {Row 2}
  //   {CommittedCount1, CommittedCount2, CommittedCount3, ...},  |   {Row 3}
  //   {Leftover1,       Leftover2,       Leftover3,       ...}   |   {Row 4}
  // }
  
  private int[][] inventoryCounts;
  private int[][] equipmentCounts;
  
  // Main functions
  @Override
  public int addJob(Job job) {
    
    int jobId = 0;
    
    // Run checkPrerequisites(job)
    if(checkPrerequisites(job)){
    
      String mySqlInsert = "INSERT INTO Job (ProductID, LineNumber, StartTime, ProjectedEndTime, PersonnelCount) VALUES (?, ?, ?, ?, ?);";
      try {
        
        Connection connection = MySQLUtility.createConnection();
        
        
        PreparedStatement preparedStatement = connection.prepareStatement(mySqlInsert);
        
        preparedStatement.setInt(1, job.getProductId());
        preparedStatement.setInt(2, job.getLineNumber());
        preparedStatement.setTimestamp(3, job.getStartTime());
        preparedStatement.setTimestamp(4, job.getProjectedEndTime());
        preparedStatement.setInt(5, job.getPersonnelCount());
        preparedStatement.executeUpdate();
        
        preparedStatement.close();
        
        String mySqlSelectId = "SELECT ID FROM Job WHERE ProductID = ? AND LineNumber = ? AND StartTime = ?;";
        preparedStatement = connection.prepareStatement(mySqlSelectId);
        preparedStatement.setInt(1, job.getProductId());
        preparedStatement.setInt(2, job.getLineNumber());
        preparedStatement.setTimestamp(3, job.getStartTime());
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if(resultSet.isBeforeFirst()) {
          resultSet.next();
          jobId = resultSet.getInt("ID");
        }
        
        connection.close();
        subtractInventory();
        
      } catch (Exception e) {
        throw new JobDaoException(e.getMessage());
      }
      
    } else {
      throw new JobDaoException(prerequisitesError);
    }
    
    return jobId;
  }
  
  @Override
  public int updateJob(Job job) {
    int id = job.getId();
    deleteJob(id);
    return addJob(job);
  }
  
  @Override
  public int deleteJob(int id) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a job exists with this jobID and execute it.
      String MySqlSelect = "SELECT * from  Job where ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(MySqlSelect);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // If a job exists
      if(resultSet.isBeforeFirst()) {
        resultSet.next();
        
        // Prepare a delete statement to delete this job from the database and execute it.
        String mySqlDelete = "DELETE FROM Job WHERE ID = ?;";
        preparedStatement = connection.prepareStatement(mySqlDelete);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        
        // Release inventory
        replaceInventory(resultSet.getInt("ProductID"));
        
        // Return the jobID.
        return id;
      } else {
        // ELSE
        // Throw a JobDaoException with the message "Job does not exist."
        throw new JobDaoException("Job does not exist.");
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new JobDaoException("Error deleting job." + e.getMessage());
    }
    
  }
  
  @Override
  public Job retrieve(int id) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a job exists with this jobID and execute it.
      String mySqlSelect = "SELECT * FROM Job WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // If a job exists
      if (resultSet.isBeforeFirst()) {
        // Move cursor to the result
        resultSet.next();
        // Create a new job object and set its fields with the values from the result set
        Job job = new Job();
        job.setId(resultSet.getInt("ID"));
        job.setProductId(resultSet.getInt("ProductID"));
        job.setLineNumber(resultSet.getInt("LineNumber"));
        job.setStartTime(resultSet.getTimestamp("StartTime"));
        job.setProjectedEndTime(resultSet.getTimestamp("ProjectedEndTime"));
        job.setPersonnelCount(resultSet.getInt("PersonnelCount"));
        job.setActualEndTime(resultSet.getTimestamp("ActualEndTime"));
        
        // Return the job object.
        return job;
      } else {
        // ELSE
        // Throw a JobDaoException with the message "Job does not exist."
        throw new JobDaoException("Job does not exist.");
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new JobDaoException("Failure retrieving job." + e.getMessage());
    }
  }
  
  public List<Job> retrieveForDate(LocalDate date) {
    // Create a Job array
    List<Job> jobs = new ArrayList<>();

    // Get a connection to the database
    try {
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see what jobs exist for the date
      // passed in date and execute it.
      String mySqlSelectExists = "SELECT job.*, product.Description as ProductDescription FROM job LEFT JOIN product ON job.ProductID = product.ID WHERE DATE(StartTime) = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelectExists);
      preparedStatement.setDate(1, Date.valueOf(date));
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();

      // Use a loop to create a new job object for each result and add it to the array.
      while(resultSet.next()) {
        Job job = new Job(resultSet.getInt("ProductID"), resultSet.getInt("LineNumber"), resultSet.getTimestamp("StartTime"),resultSet.getInt("PersonnelCount"));
        job.setId(resultSet.getInt("ID"));
        job.setProjectedEndTime(resultSet.getTimestamp("ProjectedEndTime"));
        job.setActualEndTime(resultSet.getTimestamp("ActualEndTime"));
        job.setProductName(resultSet.getString("ProductDescription"));

        jobs.add(job);
      }

      connection.close();
      preparedStatement.close();
    } catch (SQLException | ClassNotFoundException e) {
      throw new JobDaoException(e.getMessage());
    }

    return jobs;
  }
  
  // Utility functions
  
  public boolean subtractInventory() {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      String mySqlUpdateInventory = "UPDATE Inventory SET Count = ? WHERE ID = ?;";
      String mySqlSelectInventory = "SELECT * FROM Inventory WHERE TypeID = ?;";
      
      // Update inventory.
      // iterate through each required TypeID
      for (int i = 0; i < inventoryCounts[0].length; i++) {
        // Select all Inventory items of the specified type.
        PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelectInventory);
        preparedStatement.setInt(1, inventoryCounts[0][i]);
        ResultSet resultSet = preparedStatement.executeQuery();
        int inventoryRequired = inventoryCounts[1][i];
        
        // Step through Inventory items of the specified type,
        // see if subtracting what is required is possible.
        // If so, subtract what we need and break.
        // If not, subtract what we can from the
        // currently selected item down
        // to zero, update what we still need
        // and move to the next iteration to find the next inventory item
        // to remove from.
        if (resultSet.isBeforeFirst()) {
          while (resultSet.next()) {
            PreparedStatement preparedUpdateStatement = connection.prepareStatement(mySqlUpdateInventory);
            
            if (resultSet.getInt("Count") - inventoryRequired >= 0) {
              // Set the count of the currently selected inventory item to the current count minus the inventory required for this job.
              preparedUpdateStatement.setInt(1, resultSet.getInt("Count") -  inventoryRequired);
              preparedUpdateStatement.setInt(2, resultSet.getInt("ID"));
              preparedUpdateStatement.executeUpdate();
              preparedUpdateStatement.close();
              // Break because there's no more inventory required for the job.
              break;
              
            } else {
              
              // Store amount we are able to subtract from the currently selected inventory item.
              int subtracting = resultSet.getInt("Count");
              // Set the count for the currently selected inventory item to 0
              preparedUpdateStatement.setInt(1, 0);
              preparedUpdateStatement.setInt(2, resultSet.getInt("ID"));
              preparedUpdateStatement.executeUpdate();
              preparedUpdateStatement.close();
              // Update the amount of inventory we still need to subtract.
              inventoryRequired -= subtracting;
              
            }
            
          }
        } else {
          throw new JobDaoException("Error updating inventory.");
        }
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new JobDaoException("Error updating prerequisites" + e.getMessage());
    }
    
    return true;
  }
  
  public boolean replaceInventory(int productId) {
    try {
      
      // See which InventoryIDs are required for this product
      Connection connection = MySQLUtility.createConnection();
      String mySqlSelect = "SELECT * FROM ProductInventory WHERE ProductID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      preparedStatement.setInt(1, productId);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      if (resultSet.isBeforeFirst()) {
        resultSet.last();
        int records = resultSet.getRow();
        resultSet.beforeFirst();
        
        // Fill out array for required inventory counts
        for (int i = 0; i < records; i++) {
          resultSet.next();
          
          String mySqlInsert = "INSERT INTO inventory (TypeID, Count) VALUES (?, ?);";
          preparedStatement = connection.prepareStatement(mySqlInsert);
          preparedStatement.setInt(1, resultSet.getInt("InventoryTypeID"));
          preparedStatement.setInt(2, resultSet.getInt("RequiredInventoryCount"));
          preparedStatement.executeUpdate();
          
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new JobDaoException("Error updating inventory" + e.getMessage());
    }
    return true;
  }
  
  public boolean checkPrerequisites(Job job) {
    
    Timestamp startTime = job.getStartTime();
    Timestamp projectedEndTime = job.getProjectedEndTime();
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a job already exists
      // with this datetime range on this line number and execute it.
      String mySqlSelect = "SELECT * FROM Job WHERE LineNumber = ? AND StartTime < ? AND ProjectedEndTime > ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      
      preparedStatement.setInt(1, job.getLineNumber());
      preparedStatement.setTimestamp(2, projectedEndTime);
      preparedStatement.setTimestamp(3, startTime);
      
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF a job exists
      if (resultSet.isBeforeFirst()) {
        // Throw a JobDaoException with the message "Job already exists."
        throw new JobDaoException("Job already exists at that time for this line.");
      } else {
        
        // ELSE
        // Check to see if we have personnel for the start date of the job
        
        int personnelAvailableCount;
        
        int personnelCommittedCount = calculateCommittedPersonnelCount(startTime);
        
        
        String mySqlPersonnelAvailable = "SELECT * FROM personnel WHERE `Date` = ?;";
        PreparedStatement preparedStatementPersonnel = connection.prepareStatement(mySqlPersonnelAvailable);
        preparedStatementPersonnel.setDate(1, new Date(startTime.getTime()));
        ResultSet resultSetPersonnel = preparedStatementPersonnel.executeQuery();
        
        if (resultSetPersonnel.isBeforeFirst()) {
          resultSetPersonnel.next();
          personnelAvailableCount = resultSetPersonnel.getInt("Count");
        } else {
          personnelAvailableCount = 0;
        }
        
        // Get personnel required for product produced by job
        int personnelRequiredCount;
        
        String mySqlPersonnelRequired = "SELECT * FROM product WHERE ID = ?;";
        preparedStatementPersonnel = connection.prepareStatement(mySqlPersonnelRequired);
        preparedStatementPersonnel.setInt(1, job.getProductId());
        ResultSet resultsPersonnelRequired = preparedStatementPersonnel.executeQuery();
        
        if (resultsPersonnelRequired.isBeforeFirst()) {
          resultsPersonnelRequired.next();
          personnelRequiredCount = resultsPersonnelRequired.getInt("TargetPersonnelCount");
        } else {
          throw new JobDaoException("No personnel data for product.");
        }
        
        int personnelAvailablePrime = personnelAvailableCount - personnelCommittedCount;
        
        // If there aren't enough personnel, add to the prerequisitesError.
        if (personnelRequiredCount > personnelAvailablePrime) {
          prerequisitesError += "You need " + abs(personnelAvailablePrime - personnelRequiredCount) + " more personnel to schedule this job. ";
        }
        
        // Check to see if inventory and equipment are available for job.
        
        // Prepare select statements to get the InventoryTypeIDs and EquipmentTypeIDs for the requested
        // job.
        
        String mySqlInventoryTypeIds = "SELECT * FROM ProductInventory WHERE ProductID = ?;";
        String mySqlEquipmentTypeIds = "SELECT * FROM ProductEquipment WHERE ProductID = ?;";
        
        PreparedStatement inventoryTypeIdStatement = connection.prepareStatement(mySqlInventoryTypeIds, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        PreparedStatement equipmentIdStatement = connection.prepareStatement(mySqlEquipmentTypeIds, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        inventoryTypeIdStatement.setInt(1, job.getProductId());
        equipmentIdStatement.setInt(1, job.getProductId());
        
        ResultSet inventoryTypeIds= inventoryTypeIdStatement.executeQuery();
        ResultSet equipmentTypeIds = equipmentIdStatement.executeQuery();
        
        // If our inventory query has data
        if (inventoryTypeIds.isBeforeFirst()) {
          // Capture the number of different inventory types required for this job.
          inventoryTypeIds.last();
          int records = inventoryTypeIds.getRow();
          inventoryTypeIds.beforeFirst();
          // Initialize the array.
          inventoryCounts = new int[5][records];

          for (int i = 0; i < records; i++) {
            inventoryTypeIds.next();
            // Add the IDs to the array
            inventoryCounts[0][i] = inventoryTypeIds.getInt("InventoryTypeID");
          }
        } else {
          inventoryCounts = new int[5][0];
        }
        
        // If our equipment query has data
        if (equipmentTypeIds.isBeforeFirst()) {
          // Capture the number of equipment types required for this job.
          equipmentTypeIds.last();
          int records = equipmentTypeIds.getRow();
          equipmentTypeIds.beforeFirst();
          // Initialize the array
          equipmentCounts = new int[5][records];
          
          for (int i = 0; i < records; i++) {
            equipmentTypeIds.next();
            // Add the IDs to the array
            equipmentCounts[0][i] = equipmentTypeIds.getInt("EquipmentTypeID");
          }
        } else {
          equipmentCounts = new int[5][0];
        }
        
        // Prepare SQL statements to find the available and required counts
        // and fill in the arrays.
        String mySqlInventoryRequired = "SELECT * FROM ProductInventory WHERE InventoryTypeID = ? AND ProductID = ?;";
        String mySqlEquipmentRequired = "SELECT EquipmentTypeID, SUM(`RequiredEquipmentTypeCount`) AS `Count` FROM ProductEquipment WHERE ProductID = ? GROUP BY EquipmentTypeID;";
        String mySqlInventoryAvailable = "SELECT TypeID, SUM(`Count`) AS `Count` FROM Inventory WHERE TypeID = ? GROUP BY TypeID;";
        String mySqlEquipmentAvailable = "SELECT TypeID, SUM(Status) AS `Count` FROM Equipment WHERE TypeID = ? GROUP BY TypeID;";
        
        // Fill the 3rd row with committed inventory counts
        fillCommittedInventoryCounts(startTime);
        
        // Fill in the inventoryCounts array with a rotating SQL statement
        // searching for counts by TypeIDs
        for (int i = 0; i < inventoryCounts[0].length; i++) {
          PreparedStatement inventoryRequiredStatement = connection.prepareStatement(mySqlInventoryRequired);
          inventoryRequiredStatement.setInt(1, inventoryCounts[0][i]);
          inventoryRequiredStatement.setInt(2, job.getProductId());
          
          ResultSet inventoryRequiredResults = inventoryRequiredStatement.executeQuery();
          
          PreparedStatement inventoryAvailableStatement = connection.prepareStatement(mySqlInventoryAvailable);
          inventoryAvailableStatement.setInt(1, inventoryCounts[0][i]);
          
          ResultSet inventoryAvailableResults = inventoryAvailableStatement.executeQuery();
          
          if (inventoryRequiredResults.isBeforeFirst()) {
            inventoryRequiredResults.next();
            inventoryCounts[1][i] = inventoryRequiredResults.getInt("RequiredInventoryCount");
          } else {
            throw new JobDaoException("Inventory Required Count Error");
          }
          
          if (inventoryAvailableResults.isBeforeFirst()) {
            inventoryAvailableResults.next();
            inventoryCounts[2][i] = inventoryAvailableResults.getInt("Count");
          } else {
            throw new JobDaoException("Inventory Available Count Error");
          }
          
          
          // Calculate what is available after subtracting commitment.
          int availablePrime = inventoryCounts[2][i] - inventoryCounts[3][i];
          
          // Calculate the difference
          inventoryCounts[4][i] = availablePrime - inventoryCounts[1][i];
          
          if (inventoryCounts[4][i] < 0) {
            prerequisitesError += String.format("You need %d more of Inventory Type ID: %d. ", abs(inventoryCounts[4][i]), inventoryCounts[0][i]);
          }
          
        }
        
        // Fill the 3rd row with committed equipment counts
        fillCommittedEquipmentCount(startTime);
        
        // Fill in the equipmentCounts array with a rotating SQL statement
        // searching for counts by TypeIDs
        for(int i = 0; i < equipmentCounts[0].length; i++) {
          PreparedStatement equipmentRequiredStatement = connection.prepareStatement(mySqlEquipmentRequired);
          equipmentRequiredStatement.setInt(1, job.getProductId());
          
          ResultSet equipmentRequiredResults = equipmentRequiredStatement.executeQuery();
          
          PreparedStatement equipmentAvailableStatement = connection.prepareStatement(mySqlEquipmentAvailable);
          equipmentAvailableStatement.setInt(1, equipmentCounts[0][i]);
          
          ResultSet equipmentAvailableResults = equipmentAvailableStatement.executeQuery();
          
          if (equipmentRequiredResults.isBeforeFirst()) {
            equipmentRequiredResults.next();
            equipmentCounts[1][i] = equipmentRequiredResults.getInt("Count");
          } else {
            throw new JobDaoException("Equipment Required Count Error");
          }
          
          if (equipmentAvailableResults.isBeforeFirst()) {
            equipmentAvailableResults.next();
            equipmentCounts[2][i] = equipmentAvailableResults.getInt("Count");
          } else {
            throw new JobDaoException("Equipment Available Count Error");
          }
          
          
          
          // Calculate what is available after subtracting commitment.
          int availablePrime = equipmentCounts[2][i] - equipmentCounts[3][i];
          
          // Calculate the difference
          equipmentCounts[4][i] = availablePrime - equipmentCounts[1][i];
          
          if (equipmentCounts[4][i] < 0) {
            prerequisitesError += String.format("You need %d more of Equipment Type ID: %d. ", abs(equipmentCounts[4][i]), equipmentCounts[0][i]);
          }
        
        }
        
        job.setInventoryCounts(inventoryCounts);
        job.setEquipmentCounts(equipmentCounts);
        
        if (!prerequisitesError.isEmpty()) {
          return false;
        }
        
      } // End else
    } catch (Exception e) {
      throw new JobDaoException(e.getMessage());
    }
    
    return true;
  }
  
  public void fillCommittedInventoryCounts(Timestamp startTime) {
    // This method fills the current instance's inventoryCounts array's
    // 3rd row index with the committed number of inventory at a given time
    // for each TypeID stored in the first row index of the inventoryCounts array.
    
    int[] typeIDs;
    
    try {
      // Get a connection
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a query to select jobs at the time of the passed in job
      String mySqlSelect = "SELECT * FROM Job WHERE StartTime <= ? AND ProjectedEndTime >= ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      preparedStatement.setTimestamp(1, startTime);
      preparedStatement.setTimestamp(2, startTime);
      // Execute the query
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // If there's data
      if (resultSet.isBeforeFirst()) {
        
        // Capture how many jobs we have to look at
        resultSet.last();
        int records = resultSet.getRow();
        resultSet.beforeFirst();
        
        // We need to figure out how much inventory is committed for each
        // TypeID stored in this DAOs inventoryCounts array at the time of the job.
        
        // Start a loop to step through jobs at the start time.
        for(int j = 0 ; j < records; j++) {
          // Advance the cursor.
          resultSet.next();
          
          // Figure out which inventoryTypeIDs are used by this job and store them
          // in the typeIDs array.
          
          // Prepare a select statement to retrieve all the typeIDs in this job's product.
          String mySqlInventoryTypeIds = "SELECT * FROM ProductInventory WHERE ProductID = ?;";
          PreparedStatement inventoryTypeIdStatement = connection.prepareStatement(mySqlInventoryTypeIds, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          inventoryTypeIdStatement.setInt(1, resultSet.getInt("ProductID"));
          ResultSet inventoryTypeIdResults = inventoryTypeIdStatement.executeQuery();
          
          if (inventoryTypeIdResults.isBeforeFirst()) {
            inventoryTypeIdResults.last();
            
            // Initialize the typeIDs array with the number of different inventory types used by this job.
            typeIDs = new int[inventoryTypeIdResults.getRow()];
            inventoryTypeIdResults.beforeFirst();
            
            // Step through the typeIDs array and fill it with IDs from the result set.
            for (int k = 0; k < typeIDs.length; k++) {
              
              inventoryTypeIdResults.next();
              // Store the ID
              typeIDs[k] = inventoryTypeIdResults.getInt("InventoryTypeID");
              
            }
            
            // Start a loop to step through TypeIDs stored in the inventoryCounts array.
            // We need to see if this job uses the current TypeID, and if so, add that count to
            // row index 3 of the inventoryCounts array.
            for (int i = 0; i < inventoryCounts[0].length; i++) {
              // Compare the current typeID in inventoryCounts to
              // each typeID in typeIDs. If there's a match,
              // add the required number
              // to row index 3 of the inventoryCounts array.
              
              for (int l = 0; l < typeIDs.length; l++) {
                
                // There's a match
                if (typeIDs[l] == inventoryCounts[0][i]) {
                  
                  // Prepare a select statement to get the required count for this typeID for the current job in the loop.
                  String mySqlInventoryRequired = "SELECT * FROM ProductInventory WHERE InventoryTypeID = ? AND ProductID = ?;";
                  PreparedStatement inventoryRequiredStatement = connection.prepareStatement(mySqlInventoryRequired);
                  inventoryRequiredStatement.setInt(1, typeIDs[l]);
                  inventoryRequiredStatement.setInt(2, resultSet.getInt("ProductID"));
                  ResultSet inventoryRequiredResults = inventoryRequiredStatement.executeQuery();
                  
                  // If there's data.
                  if (inventoryRequiredResults.isBeforeFirst()) {
                    inventoryRequiredResults.next();
                    // Add this TypeIDs required count for this job's product to the
                    // total commited count.
                    inventoryCounts[3][i] += inventoryRequiredResults.getInt("RequiredInventoryCount");
                    
                  } else {
                    throw new JobDaoException("Inventory Required Count Error");
                  }
                }
              }
            }
          }
        }
      } else {
        // Fill the row with 0s
        for (int i = 0; i < inventoryCounts[0].length; i++) {
          inventoryCounts[3][i] = 0;
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      throw new JobDaoException(e.getMessage());
    }
  }
  
  public void fillCommittedEquipmentCount(Timestamp startTime) {
    // This method fills the current instance's equipmentCounts array's
    // 3rd row index with the committed number of equipment at a given time
    // for each TypeID stored in the first row index of the equipmentCounts array.
    
    int[] typeIDs;
    
    try {
      // Get a connection
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a query to select jobs at the time of the passed in job
      String mySqlSelect = "SELECT * FROM Job WHERE StartTime <= ? AND ProjectedEndTime >= ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      preparedStatement.setTimestamp(1, startTime);
      preparedStatement.setTimestamp(2, startTime);
      // Execute the query
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // If there's data
      if (resultSet.isBeforeFirst()) {
        
        // Capture how many jobs we have to look at
        resultSet.last();
        int records = resultSet.getRow();
        resultSet.beforeFirst();
        
        // We need to figure out how much equipment is committed for each
        // TypeID stored in this DAOs equipmentCounts array at the time of the job.
        
        // Start a loop to step through jobs at the start time.
        for(int j = 0 ; j < records; j++) {
          // Advance the cursor.
          resultSet.next();
          
          // Figure out which equipmentTypeIDs are used by this job and store them
          // in the typeIDs array.
          
          // Prepare a select statement to retrieve all the typeIDs in this job's product.
          String mySqlEquipmentTypeIds = "SELECT * FROM ProductEquipment WHERE ProductID = ?;";
          PreparedStatement equipmentTypeIdStatement = connection.prepareStatement(mySqlEquipmentTypeIds, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          equipmentTypeIdStatement.setInt(1, resultSet.getInt("ProductID"));
          ResultSet equipmentTypeIdResults = equipmentTypeIdStatement.executeQuery();
          
          if (equipmentTypeIdResults.isBeforeFirst()) {
            equipmentTypeIdResults.last();
            
            // Initialize the typeIDs array with the number of different equipment types used by this job.
            typeIDs = new int[equipmentTypeIdResults.getRow()];
            equipmentTypeIdResults.beforeFirst();
            
            // Step through the typeIDs array and fill it with IDs from the result set.
            for (int k = 0; k < typeIDs.length; k++) {
              
              equipmentTypeIdResults.next();
              // Store the ID
              typeIDs[k] = equipmentTypeIdResults.getInt("EquipmentTypeID");
              
            }
            
            // Start a loop to step through TypeIDs stored in the equipmentCounts array.
            // We need to see if this job uses the current TypeID, and if so, add that count to
            // row index 3 of the equipmentCounts array.
            for (int i = 0; i < equipmentCounts[0].length; i++) {
              // Compare the current typeID in equipmentCounts to
              // each typeID in typeIDs. If there's a match,
              // add the required number
              // to row index 3 of the equipmentCounts array.
              
              for (int l = 0; l < typeIDs.length; l++) {
                
                // There's a match
                if (typeIDs[l] == equipmentCounts[0][i]) {
                  
                  // Prepare a select statement to get the required count for this typeID for the current job in the loop.
                  String mySqlEquipmentRequired = "SELECT * FROM ProductEquipment WHERE EquipmentTypeID = ? AND ProductID = ?;";
                  PreparedStatement equipmentRequiredStatement = connection.prepareStatement(mySqlEquipmentRequired);
                  equipmentRequiredStatement.setInt(1, typeIDs[l]);
                  equipmentRequiredStatement.setInt(2, resultSet.getInt("ProductID"));
                  ResultSet equipmentRequiredResults = equipmentRequiredStatement.executeQuery();
                  
                  // If there's data.
                  if (equipmentRequiredResults.isBeforeFirst()) {
                    equipmentRequiredResults.next();
                    // Add this TypeIDs required count for this job's product to the
                    // total commited count.
                    System.out.println(equipmentRequiredResults.getInt("RequiredEquipmentTypeCount"));
                    equipmentCounts[3][i] += equipmentRequiredResults.getInt("RequiredEquipmentTypeCount");
                    
                  } else {
                    throw new JobDaoException("Equipment Required Count Error");
                  }
                }
              }
            }
          }
        }
      } else {
        // Fill the row with 0s
        for (int i = 0; i < equipmentCounts[0].length; i++) {
          equipmentCounts[3][i] = 0;
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      throw new JobDaoException(e.getMessage());
    }
  }
  
  public int calculateCommittedPersonnelCount(Timestamp startTime) {
    
    String mySqlSelect = "SELECT * FROM job WHERE StartTime <= ? AND ProjectedEndTime >= ?;";
    
    try {
      Connection connection = MySQLUtility.createConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      preparedStatement.setTimestamp(1, startTime);
      preparedStatement.setTimestamp(2, startTime);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      if (resultSet.isBeforeFirst()) {
        resultSet.last();
        int records = resultSet.getRow();
        resultSet.beforeFirst();
        
        int personnelCount = 0;
        for(int j = 0 ; j < records; j++) {
          resultSet.next();
          personnelCount += resultSet.getInt("PersonnelCount");
        }
        return personnelCount;
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      throw new JobDaoException(e.getMessage());
    }
    return 0;
  }
  
  public int[][] getInventoryCounts() {
    return inventoryCounts;
  }
  
  public void setInventoryCounts(int[][] inventoryCounts) {
    this.inventoryCounts = inventoryCounts;
  }
  
  public int[][] getEquipmentCounts() {
    return equipmentCounts;
  }
  
  public void setEquipmentCounts(int[][] equipmentCounts) {
    this.equipmentCounts = equipmentCounts;
  }
  
  public String getPrerequisitesError() {
    return prerequisitesError;
  }
}
