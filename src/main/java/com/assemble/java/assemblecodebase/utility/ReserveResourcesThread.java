package com.assemble.java.assemblecodebase.utility;

import com.assemble.java.assemblecodebase.dao.JobDao;
import com.assemble.java.assemblecodebase.dao.JobDaoImpl;
import com.assemble.java.assemblecodebase.model.Job;

import java.sql.Date;
import java.sql.Timestamp;

public class ReserveResourcesThread extends Thread {
  
  private long timeToReserve;
  private Job jobToReserve;
  private JobDaoImpl jobDao =  new JobDaoImpl();
  
  
  public ReserveResourcesThread(long timeToReserve, Job jobToReserve) {
    setTimeToReserve(timeToReserve);
    setJobToReserve(jobToReserve);
    
    jobDao.setInventoryCounts(jobToReserve.getInventoryCounts());
    jobDao.setEquipmentCounts(jobToReserve.getEquipmentCounts());
  }
  
  @Override
  public void run() {
    
    while (true) {
      
      try {
        Thread.sleep(timeToReserve * 60 * 1000);
        
        jobDao.updatePrerequisites(new Date(jobToReserve.getStartTime().getTime()), jobToReserve.getPersonnelCount());
        
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
  }
  
  public long getTimeToReserve() {
    return timeToReserve;
  }
  
  public void setTimeToReserve(long timeToReserve) {
    this.timeToReserve = timeToReserve;
  }
  
  public Job getJobToReserve() {
    return jobToReserve;
  }
  
  public void setJobToReserve(Job jobToReserve) {
    this.jobToReserve = jobToReserve;
  }
}
