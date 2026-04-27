package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Job;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    
    // Run updatePrerequisites(job)
    
    // IF updatePrerequisites throws an exception
      // Catch the exception and throw a JobDaoException with the message from the caught exception.
    
    // ELSE
      // Get a connection to the database
      // Prepare an insert statement to add this job to the database.
      // IF job.id is not null, include it in the insert statement, otherwise let the database generate it.
      // Execute the insert statement.
      // Prepare a select statement to get the newly created jobID and execute it.
      // Return the jobID.
    
    return 0;
  }
  
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
          Job job = new Job(resultSet.getInt("ID"), resultSet.getInt("ProductID"), resultSet.getInt("LineNumber"), resultSet.getTimestamp("StartTime"),resultSet.getInt("PersonnelCount"));
          job.setProjectedEndTime(resultSet.getTimestamp(4));
          job.setActualEndTime(resultSet.getTimestamp(5));
          
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
  
  public void updatePrerequisites(Job job) {
    
    // Use this method to update the database with new values for
    // inventory and equipment counts.
    
    
  
  }
  
  public boolean checkPrerequisites(Job job, String prerequisite) {
    
    Timestamp startTime = job.getStartTime();
    Timestamp projectedEndTime = job.getProjectedEndTime();
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a job already exists
      // with this datetime range on this line number and execute it.
      String mySqlSelect = "SELECT * FROM job WHERE ((StartTime >= ? ) OR (ProjectedEndTime <= ?)) AND (LineNumber = ?);";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setTimestamp(1, startTime);
      preparedStatement.setTimestamp(2, projectedEndTime);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF a job exists
      if (resultSet.isBeforeFirst()) {
        // Throw a JobDaoException with the message "Job already exists."
        throw new JobDaoException("Job already exists at that time for this line.");
      } else {
        
        // ELSE
        // Check to see if inventory and equipment are available for job.
        // Prepare select statements to get the InventoryTypeIDs and EquipmentTypeIDs for the requested
        // job.
        
        String mySqlInventoryTypeIds = "SELECT * FROM ProductInventory WHERE ProductID = ?;";
        String mySqlEquipmentTypeIds = "SELECT * FROM EquipmentInventory WHERE ProductID = ?;";
        PreparedStatement inventoryTypeIdStatement = connection.prepareStatement(mySqlInventoryTypeIds);
        PreparedStatement equipmentIdStatement = connection.prepareStatement(mySqlEquipmentTypeIds);
        inventoryTypeIdStatement.setInt(1, job.getProductId());
        equipmentIdStatement.setInt(1, job.getProductId());
        ResultSet inventoryTypeIds = inventoryTypeIdStatement.executeQuery();
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
        // and fill in the arrays
        String mySqlInventoryCount =
          "SELECT" +
            "pi.RequiredInventoryCount AS InventoryRequired, " +
            "i.Count AS InventoryAvailable, " +
            "(i.Count - pi.RequiredInventoryCount) AS Leftover " +
          "FROM " +
            "Product AS p " +
            "INNER JOIN ProductInventory AS pi ON p.ID = pi.ProductID " +
            "INNER JOIN InventoryType AS it ON pi.InventoryTypeID = it.ID " +
            "INNER JOIN Inventory AS i ON it.ID = i.TypeID " +
          "WHERE " +
            "pi.InventoryTypeID = ?;";
        
        String mySqlEquipmentCount =
          "SELECT " +
            "pe.RequiredEquipmentTypeCount AS EquipmentRequired, " +
            "e.Count AS EquipmentAvailable, " +
            "(e.Count - pe.RequiredEquipmentTypeCount) AS Leftover " +
          "FROM " +
            "Product AS p " +
            "INNER JOIN ProductEquipment AS pe ON p.ID = pe.ProductID " +
            "INNER JOIN EquipmentType AS et ON pe.EquipmentTypeID = et.ID " +
            "INNER JOIN Equipment AS e ON et.ID = e.TypeID " +
          "WHERE " +
            "pe.EquipmentTypeID = ?;";
        
        // Fill in the inventoryCounts array with a rotating SQL statement
        // searching for counts by TypeIDs
        for (int i = 0; i < inventoryCounts[0].length; i++) {
          PreparedStatement inventoryCountStatement = connection.prepareStatement(mySqlInventoryCount);
          // Set the TypeID for the inventoryCountStatement
          inventoryCountStatement.setInt(1, inventoryCounts[0][i]);
          ResultSet inventoryCount = inventoryCountStatement.executeQuery();
          if (inventoryCount.isBeforeFirst()) {
            inventoryCount.next();
            
            // Set required and available, and calculate leftover
            inventoryCounts[1][i] =  inventoryCount.getInt("InventoryRequired");
            inventoryCounts[2][i] = inventoryCount.getInt("InventoryAvailable");
            inventoryCounts[3][i] = inventoryCount.getInt("Leftover");
            
            // Add to the error if necessary
            if (inventoryCounts[3][i] < 0) {
              prerequisitesError += String.format("Not enough of InventoryTypeID: %d. You need %d more.", inventoryCounts[0][i], abs(inventoryCounts[3][i]));
            }
            
          } else {
            throw new  JobDaoException("Inventory Count Error");
          }
        }
        
        // Fill in the equipmentCounts array with a rotating SQL statement
        // searching for counts by TypeIDs
        for(int i = 0; i < equipmentCounts[0].length; i++) {
          PreparedStatement equipmentCountStatement = connection.prepareStatement(mySqlEquipmentCount);
          // Set the TypeID for the equipmentCountStatement
          equipmentCountStatement.setInt(1, equipmentCounts[0][i]);
          ResultSet equipmentCount = equipmentCountStatement.executeQuery();
          
          if (equipmentCount.isBeforeFirst()) {
            equipmentCount.next();
            
            // Set required and available, and calculate leftover
            equipmentCounts[1][i] =  equipmentCount.getInt("EquipmentRequired");
            equipmentCounts[2][i] = equipmentCount.getInt("EquipmentAvailable");
            equipmentCounts[3][i] = equipmentCount.getInt("Leftover");
            
            // Ass to the error if necessary
            if (equipmentCounts[3][i] < 0) {
              prerequisitesError += String.format("Not enough of EquipmentTypeID: %d. You need %d more.", equipmentCounts[0][i], abs(equipmentCounts[3][i]));
            }
            
          } else {
            throw new  JobDaoException("Equipment Count Error");
          }
        }
        
        if (!prerequisitesError.isEmpty()) {
          return false;
        }
        
      } // End else
    } catch (Exception e) {
      throw new JobDaoException(e.getMessage());
    }
    
    return true;
  }
  
  public boolean releasePrerequisites(Job job) {
    
    // Get a connection to the database
    
    // Prepare a select statement to find inventory types and counts required for job and store them using a loop.
    // Prepare a select statement to find equipment types and counts required for job and store them using a loop.
    
    // Use InventoryDao and EquipmentDao to add counts back to the database for each inventory and equipment type required for this job
    return true;
  }
}
