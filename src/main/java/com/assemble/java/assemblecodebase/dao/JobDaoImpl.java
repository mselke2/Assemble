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
    
  
  }
  
  public boolean checkPrerequisites(Job job, String prerequisite) {
    
    Timestamp startTime = job.getStartTime();
    Timestamp projectedEndTime = job.getProjectedEndTime();
    
    // Two-dimensional arrays to store TypeIDs and associated available/required counts.
    int[][] counts;
    
    try {
      // Get a connection to the deatabase
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
      }else {
        // ELSE
        // Check to see if inventory is available for job.
        // Prepare a select statement to find inventory types and counts required for job and store them.
        String mySqlSelectInventoryRequired = "SELECT * FROM ProductInventory WHERE ProductID = ?;";
        PreparedStatement preparedStatementInventory = connection.prepareStatement(mySqlSelectInventoryRequired);
        preparedStatement.setInt(1, job.getProductId());
        ResultSet resultsInventory  = preparedStatementInventory.executeQuery();
        
        if (resultsInventory.isBeforeFirst()) {
          
          // Capture the number of inventory types required for the job.
          resultsInventory.last();
          int rows = resultsInventory.getRow();
          resultsInventory.beforeFirst();
          
          // This is a 2D array used as a kind of database table on the server.
          // The structure looks like this:
          // {
          //   {Col 0,           Col 1,           Col 2,           ...}
          //   {TypeID1,         TypeID2,         TypeID3,         ...}, {Row 0}
          //   {RequiredCount1,  RequiredCount2,  RequiredCount3,  ...}, {Row 1}
          //   {AvailableCount1, AvailableCount2, AvailableCount3, ...}, {Row 2}
          //   {Difference1,     Difference2,     Difference3,     ...}  {Row 3}
          // }
          //  int[rows][columns] -- Create a 2D array
          //                        with 4 rows and
          //                        a number of columns equal
          //                        to the number of different
          //                        inventory types required
          //                        for this job.
          counts = new int[4][rows];
          String mySqlSelectInventoryAvailable = "SELECT * FROM Inventory WHERE TypeID = ?;";
          
          // Use a loop to iterate through the inventoryCounts array and
          // fill in the required data from the database.
          for (int i = 0; i < rows; i++) {
            resultsInventory.next();
            
            // Add InventoryTypeIDs and required RequiredInventoryCounts to the first and second rows of the current column.
            counts[0][i] = resultsInventory.getInt("InventoryTypeID");
            counts[1][i] = resultsInventory.getInt("RequiredInventoryCount");
            
            // Prepare a select statement to find inventory count available for
            // the typeID we just captured and store it
            // in the third row of the array.
            PreparedStatement preparedStatementInventoryAvailable = connection.prepareStatement(mySqlSelectInventoryAvailable);
            
            // Set the ? in the select statement to the TypeID from the first row of the array for the current column.
            preparedStatementInventoryAvailable.setInt(1, counts[0][i]);
            ResultSet resultsAvailable  = preparedStatementInventoryAvailable.executeQuery();
            
            if (resultsAvailable.isBeforeFirst()) {
              resultsAvailable.next();
              // Set the third row of the array to the corresponding available
              // count for the TypeID in the current column.
              counts[2][i] = resultsAvailable.getInt("Count");
            } else {
              throw new JobDaoException("Inventory Type Error.");
            }
            
            // Set the fourth row of the array to the difference between the
            // second and third rows for the current column.
            counts[3][i] = counts[2][i] - counts[1][i];
            
            // IF inventory required is greater than inventory available for any typeID
            // add "Not enough of ID:[TypeID]. You need [number of TypeID short] more."
            // to the prerequisites error.
            if (counts[3][i] < 0) {
              prerequisitesError += String.format("Not enough of InvetoryTypeID: %d. You need %d more.", counts[0][i], abs(counts[3][i]));
            }
          }
          
        } else {
          throw new JobDaoException("No inventory exists.");
        }
        // If the prerequisites error is empty, update the prodict counts and
        // inventory counts.
      }
    } catch (Exception e) {
      throw new JobDaoException(e.getMessage());
    }
    
    return false;
  }
  
  public boolean releasePrerequisites(Job job) {
    
    // Get a connection to the database
    
    // Prepare a select statement to find inventory types and counts required for job and store them using a loop.
    // Prepare a select statement to find equipment types and counts required for job and store them using a loop.
    
    // Use InventoryDao and EquipmentDao to add counts back to the database for each inventory and equipment type required for this job
    return true;
  }
}
