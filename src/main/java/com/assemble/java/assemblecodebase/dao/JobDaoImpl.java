package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Job;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;
import java.sql.*;
import java.time.LocalDate;

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
  //   {Leftover1,       Leftover2,       Leftover3,       ...}   |   {Row 3}
  // }
  
  private int[][] inventoryCounts;
  private int[][] equipmentCounts;
  
  public String getPrerequisitesError() {
    return prerequisitesError;
  }
  
  @Override
  public int addJob(Job job) {
    
    int jobId = -1;
    
    // Run checkPrerequisites(job)
    if(checkPrerequisites(job)){
    
      String mySqlInsert = "INSERT INTO Job (ProductID, LineNumber, StartTime, ProjectedEndTime, PersonnelCount) VALUES (?, ?, ?, ?, ?);";
      String mySqlUpdate = "UPDATE Job SET ProductID = ?, LineNumber = ?, StartTime = ?, ProjectedEndTime = ?, PersonnelCount = ? WHERE ID = ?;";
      try {
        
        Connection connection = MySQLUtility.createConnection();
        
        if (job.getId() != -1) {
          PreparedStatement preparedStatement = connection.prepareStatement(mySqlUpdate);
          
          preparedStatement.setInt(1, job.getProductId());
          preparedStatement.setInt(2, job.getLineNumber());
          preparedStatement.setTimestamp(3, job.getStartTime());
          preparedStatement.setTimestamp(4, job.getProjectedEndTime());
          preparedStatement.setInt(5, job.getPersonnelCount());
          preparedStatement.setInt(6, job.getId());
          preparedStatement.executeUpdate();
          
          preparedStatement.close();
          jobId = job.getId();
          
          
        } else {
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
        }
        
        connection.close();
        updatePrerequisites(new Date(job.getStartTime().getTime()), job.getPersonnelCount());
        
      } catch (Exception e) {
        throw new JobDaoException(e.getMessage());
      }
      
    } else {
      throw new JobDaoException(prerequisitesError);
    }
    
    return jobId;
  }
  
  // May not need this method ------------------------------
  @Override
  public void updateJob(Job job) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a job already exists
      // with this productID and datetime range and execute it.
    
    // IF a job exists
      // Run deleteJob(job) to delete existing job
      // Run addJob(job) to add new job with updated information
    
    // ELSE
      // Throw a JobDaoException with the message "Job does not exist."
  }
  
  @Override
  public int deleteJob(int id) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a job exists with this jobID and execute it.
    
    // If a job exists
      // Store the jobID in a variable
      // Run releasePrerequisites(job) to add inventory and equipment back to database
      // Prepare a delete statement to delete this job from the database and execute it.
      // Return the jobID.
    
    // ELSE
      // Throw a JobDaoException with the message "Job does not exist."
    
    return 0;
  }
  
  @Override
  public Job retrieve(int id) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a job exists with this jobID and execute it.
    
    // If a job exists
      // Move cursor to the result
      // Create a new job object and set its fields with the values from the result set
      // Return the job object.
    
    // ELSE
      // Throw a JobDaoException with the message "Job does not exist."
    
    return null;
  }
  
  public Job[] retrieveForDate(LocalDate date) {
    
    // Get a connection to the database
    try {
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see what jobs exist for the
      // passed in date and execute it.
      String mySqlSelectExists = "SELECT * FROM job WHERE DATE(StartTime) = ?;";
      String mySqlSelectProductDuration = "SELECT MinutesDuration FROM product WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelectExists);
      preparedStatement.setString(1, date.toString());
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();
      
      // IF jobs exist
      if (resultSet.isBeforeFirst()) {
        resultSet.last();
        int rows = resultSet.getRow();
        resultSet.beforeFirst();
        
        // Create a Job array
        Job[] jobs  = new Job[rows];
        
        // Use a loop to move the cursor through the results and create a new job object for each result and add it to the array.
        for (int i = 0; i < rows; i++) {
          resultSet.next();
          
          // Get the MinutesDuration to calculate projected end time for the job.
          PreparedStatement preparedStatementDuration = connection.prepareStatement(mySqlSelectProductDuration);
          preparedStatementDuration.setInt(1, resultSet.getInt("MinutesDuration"));
          ResultSet resultSetDuration = preparedStatementDuration.executeQuery();
          // DAO to retrieve target personnel count
          ProductDaoImpl productDao = new ProductDaoImpl();

          Job job = new Job(resultSet.getInt("ProductID"), resultSet.getInt("LineNumber"), resultSet.getTimestamp("StartTime"));
          job.setStartTime(resultSetDuration.getTimestamp("StartTime"));
          job.setProjectedEndTime(job.getStartTime(), resultSetDuration.getInt("MinutesDuration"));
          job.setPersonnelCount(productDao.retrieve(resultSet.getInt("ProductID")).getTargetPersonnelCount());
          
          jobs[i] = job;
        }
        
        // Return the array of jobs.
        return jobs;
      } else {
        throw new JobDaoException("No jobs exist for this date.");
      }
      
      
      // ELSE
      // Return an empty array or null.
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    
  }
  
  public boolean updatePrerequisites(Date date, int personnelCount) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      String mySqlUpdateInventory = "UPDATE Inventory SET Count = ? WHERE ID = ?;";
      String mySqlUpdateEquipment = "UPDATE Equipment SET Status = 0 WHERE ID = ?;";
      String mySqlSelectEquipment = "SELECT * FROM Equipment WHERE TypeID = ?;";
      String mySqlSelectInventory = "SELECT * FROM Inventory WHERE TypeID = ?;";
      String mySqlSelectPersonnelCount = "SELECT * FROM personnel WHERE Date = ?;";
      String mySqlUpdatePersonnelCount = "UPDATE personnel SET Count = ? WHERE Date = ?;";
      
      // Update Personnel
      PreparedStatement preparedStatementPersonnel = connection.prepareStatement(mySqlSelectPersonnelCount);
      preparedStatementPersonnel.setDate(1, date);
      ResultSet resultSetPersonnel = preparedStatementPersonnel.executeQuery();
      if (resultSetPersonnel.isBeforeFirst()) {
        resultSetPersonnel.next();
        preparedStatementPersonnel =  connection.prepareStatement(mySqlUpdatePersonnelCount);
        preparedStatementPersonnel.setInt(1, resultSetPersonnel.getInt("Count") - personnelCount);
        preparedStatementPersonnel.setDate(2, date);
        preparedStatementPersonnel.executeUpdate();
      }else {
        throw new JobDaoException("Error updating personnel count.");
      }
      
      
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
      
      // Update equipment.
      // Iterate through each required TypeID
      for (int i = 0; i < equipmentCounts[0].length; i++) {
        // Select all equipment of the specified TypeID
        PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelectEquipment);
        preparedStatement.setInt(1, equipmentCounts[0][i]);
        ResultSet resultSet = preparedStatement.executeQuery();
        int equipmentRequired = equipmentCounts[1][i];
        
        
        if (resultSet.isBeforeFirst()) {
          
          // Step through equipment of the required type
          while (resultSet.next() && equipmentRequired > 0) {
            // Select the next piece of equipment
            PreparedStatement preparedUpdateStatement = connection.prepareStatement(mySqlUpdateEquipment);
            // If it's available.
            if(resultSet.getInt("Status") == 1) {
              // Set it to not available
              preparedUpdateStatement.setInt(1, resultSet.getInt("ID"));
              preparedUpdateStatement.executeUpdate();
              preparedUpdateStatement.close();
              // Update equipment required.
              equipmentRequired -= 1;
            }
          }
        } else {
          throw new JobDaoException("Error updating equipment.");
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new JobDaoException("Error updating prerequisites" + e.getMessage());
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
      preparedStatement.setTimestamp(3, startTime);
      preparedStatement.setTimestamp(2, projectedEndTime);
      preparedStatement.setInt(1, job.getLineNumber());
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF a job exists
      if (resultSet.isBeforeFirst()) {
        // Throw a JobDaoException with the message "Job already exists."
        throw new JobDaoException("Job already exists at that time for this line.");
      } else {
        
        // ELSE
        
        // Check to see if we have personnel for the start date of the job
        int personnelAvailableCount;
        int personnelRequiredCount;
        
        // Get personnel available for date of job
        String mySqlPersonnelAvailable = "SELECT * FROM personnel WHERE Date = ?;";
        PreparedStatement preparedStatementPersonnel =  connection.prepareStatement(mySqlPersonnelAvailable);
        preparedStatementPersonnel.setDate(1, new Date(startTime.getTime()));
        ResultSet resultsPersonnelAvailable = preparedStatementPersonnel.executeQuery();
        
        if (resultsPersonnelAvailable.isBeforeFirst()) {
          resultsPersonnelAvailable.next();
          personnelAvailableCount = resultsPersonnelAvailable.getInt("Count");
        } else {
          throw new JobDaoException("No personnel data for date.");
        }
        
        // Get personnel required for product produced by job
        String mySqlPersonnelRequired = "SELECT * FROM product WHERE ID = ?;";
        preparedStatementPersonnel= connection.prepareStatement(mySqlPersonnelRequired);
        preparedStatementPersonnel.setInt(1, job.getProductId());
        ResultSet resultsPersonnelRequired = preparedStatementPersonnel.executeQuery();
        
        if (resultsPersonnelRequired.isBeforeFirst()) {
          resultsPersonnelRequired.next();
          personnelRequiredCount = resultsPersonnelRequired.getInt("TargetPersonnelCount");
        } else {
          throw new JobDaoException("No personnel data for product.");
        }
        
        // If there aren't enough personnel, add to the prerequisitesError.
        if (personnelRequiredCount > personnelAvailableCount) {
          prerequisitesError += "You need " + abs(personnelAvailableCount - personnelRequiredCount) + " more personnel to schedule this job. ";
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
          int rows = inventoryTypeIds.getRow();
          inventoryTypeIds.beforeFirst();
          // Initialize the array.
          inventoryCounts = new int[4][rows];
          
          for (int i = 0; i < rows; i++) {
            inventoryTypeIds.next();
            // Add the IDs to the array
            inventoryCounts[0][i] = inventoryTypeIds.getInt("InventoryTypeID");
          }
        } else {
          throw new JobDaoException("No inventory types exist for this product.");
        }
        
        // If our equipment query has data
        if (equipmentTypeIds.isBeforeFirst()) {
          // Capture the number of equipment types required for this job.
          equipmentTypeIds.last();
          int rows = equipmentTypeIds.getRow();
          equipmentTypeIds.beforeFirst();
          // Initialize the array
          equipmentCounts = new int[4][rows];
          
          for (int i = 0; i < rows; i++) {
            equipmentTypeIds.next();
            // Add the IDs to the array
            equipmentCounts[0][i] = equipmentTypeIds.getInt("EquipmentTypeID");
          }
        } else {
          throw new JobDaoException("No equipment types exist for this product.");
        }
        
        // Prepare SQL statements to find the available and required counts
        // and fill in the arrays.
        String mySqlInventoryRequired = "SELECT * FROM ProductInventory WHERE InventoryTypeID = ? AND ProductID = ?;";
        String mySqlEquipmentRequired = "SELECT EquipmentTypeID, SUM(`RequiredEquipmentTypeCount`) AS `Count` FROM ProductEquipment WHERE ProductID = ? GROUP BY EquipmentTypeID;";
        String mySqlInventoryAvailable = "SELECT TypeID, SUM(`Count`) AS `Count` FROM Inventory WHERE TypeID = ? GROUP BY TypeID;";
        String mySqlEquipmentAvailable = "SELECT TypeID, SUM(Status) AS `Count` FROM Equipment WHERE TypeID = ? GROUP BY TypeID;";
        
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
          
          inventoryCounts[3][i] = inventoryCounts[2][i] - inventoryCounts[1][i];
          
          if (inventoryCounts[3][i] < 0) {
            prerequisitesError += String.format("You need %d more of Inventory Type ID: %d. ", abs(inventoryCounts[3][i]), inventoryCounts[0][i]);
          }
          
        }
        
        // Fill in the equipmentCounts array with a rotating SQL statement
        // searching for counts by TypeIDs
        for(int i = 0; i < equipmentCounts[0].length; i++) {
          PreparedStatement equipmentRequiredStatement = connection.prepareStatement(mySqlEquipmentRequired);
          equipmentRequiredStatement.setInt(1, equipmentCounts[0][i]);
          
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
          
          equipmentCounts[3][i] = equipmentCounts[2][i] - equipmentCounts[1][i];
          
          if (equipmentCounts[3][i] < 0) {
            prerequisitesError += String.format("You need %d more of Equipment Type ID: %d. ", abs(equipmentCounts[3][i]), equipmentCounts[0][i]);
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
  
  public static boolean releasePrerequisites(Job job) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      String mySqlUpdate = "UPDATE Equipment SET Count = ? WHERE ID = ? ;";
      
      for (int i = 0; i < job.getEquipmentCounts()[0].length; i++) {
        PreparedStatement equipmentCountStatement = connection.prepareStatement(mySqlUpdate);
        
      }
      
      // Use InventoryDao and EquipmentDao to add counts back to the database for each inventory and equipment type required for this job
    } catch (Exception e) {
      throw new JobDaoException(e.getMessage());
    }
    return true;
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
}
