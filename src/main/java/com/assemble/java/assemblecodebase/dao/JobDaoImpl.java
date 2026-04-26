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
    
    // If ajob exists
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
        for (int i = 1; i <= rows; i++) {
          resultSet.next();
          Job job = new Job(resultSet.getInt(1), resultSet.getInt(2), resultSet.getTimestamp(3),resultSet.getInt(6));
          job.setProjectedEndTime(resultSet.getTimestamp(4));
          job.setActualEndTime(resultSet.getTimestamp(5));
          
          jobs[i - 1] = job;
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
    
    Timestamp startTime = job.getStartTime();
    Timestamp projectedEndTime = job.getProjectedEndTime();
    
    int inventoryRequired;
    int inventoryAvailable;
    int equipmentRequired;
    int equipmentAvailable;
    
    
    try {
      // Get a connection to the deatabase
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a job already exists
      // with this productID and datetime range and execute it.
      String mySqlSelect = "SELECT * FROM job WHERE productID = ? AND ((StartTime <= ? AND ProjectedEndTime >= ?) OR (StartTime <= ? AND ProjectedEndTime >= ?))";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setInt(1, job.getProductId());
      preparedStatement.setTimestamp(2, startTime);
      preparedStatement.setTimestamp(3, projectedEndTime);
      preparedStatement.setTimestamp(4, startTime);
      preparedStatement.setTimestamp(5, projectedEndTime);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF a job exists
      if (resultSet.isBeforeFirst()) {
        // Throw a JobDaoException with the message "Job already exists."
        throw new JobDaoException("Job already exists.");
      }else {
        // ELSE
        // Check to see if inventory is available for job.
        // Prepare a select statement to find inventory types and counts required for job and store them.
        String mySqlSelectInventoryRequired = "SELECT * FROM ProductInventory WHERE ProductID = ?;";
        PreparedStatement preparedStatementInventory = connection.prepareStatement(mySqlSelectInventoryRequired);
        preparedStatement.setInt(1, job.getProductId());
        ResultSet results  = preparedStatementInventory.executeQuery();
        
        if (results.isBeforeFirst()) {
          
          
          results.last();
          int rows = results.getRow();
          results.beforeFirst();
          
          // Two-dimensional array to store TypeIDs and associated available/required counts.
          int[][] idsRequiredsAvailables = new int[3][rows];
          
          // Add ProductIDs and required counts to the first and second rows of the array using a loop.
          for (int i = 0; i < rows; i++) {
            results.next();
            
            idsRequiredsAvailables[0][i] = results.getInt(1);
            idsRequiredsAvailables[1][i] = results.getInt(2);
            
          }
          
          // Prepare a select statement to find inventory counts available for required typeIDs and store them
          // in the third row of the array.
          String mySqlSelectInventoryAvailable = "SELECT * FROM Inventory WHERE TypeID = ?;";
          for (int i = 1; i <= rows; i++) {
            PreparedStatement preparedStatementInventoryAvailable = connection.prepareStatement(mySqlSelectInventoryAvailable);
            preparedStatementInventoryAvailable.setInt(1, idsRequiredsAvailables[0][i]);
            ResultSet resultsAvailable  = preparedStatementInventoryAvailable.executeQuery();
            
            if (resultsAvailable.isBeforeFirst()) {
              resultsAvailable.next();
              idsRequiredsAvailables[2][i] = resultsAvailable.getInt(3);
            } else {
              throw new JobDaoException("Inventory Type Error.");
            }
          }
          
          // IF inventory required is greater than inventory available for any typeID
          // add "Not enough of ID:[TypeID]. You need [number of TypeID short] more."
          // to the prerequisites error.
          for (int i = 0; i < rows; i++) {
            int excess = idsRequiredsAvailables[2][i] - idsRequiredsAvailables[1][i];
            
            if (excess < 0) {
              prerequisitesError += String.format("Not enough of ID: %d. You need %d more.", idsRequiredsAvailables[0][i], abs(excess));
            }
            
          }
          
        } else {
          throw new JobDaoException("No inventory exists.");
        }
        
        
        
        
        // ELSE
        // Subtract inventory required from inventory available and store in a variable.
        
        // Check to see if equipment is available for job.
        // Prepare a select statement to find equipment types and counts required for job and store them.
        // Prepare a select statement to find equipment counts available for required types and store them
        
        // IF equipment required is greater than equipment available
        // Throw a JobDaoException with the message "Not enough equipment available."
        
        // ELSE
        // Subtract equipment required from equipment available and store in a variable.
        // Prepare an update statement to update inventory counts in the database and execute it.
        // Prepare an update statement to update equipment counts in the database and execute it.
        // Return true.
      }
      
      
      
    } catch (Exception e) {
      throw new JobDaoException(e.getMessage());
    }
  }
  
  public boolean releasePrerequisites(Job job) {
    
    // Get a connection to the database
    
    // Prepare a select statement to find inventory types and counts required for job and store them using a loop.
    // Prepare a select statement to find equipment types and counts required for job and store them using a loop.
    
    // Use InventoryDao and EquipmentDao to add counts back to the database for each inventory and equipment type required for this job
    return true;
  }
}
