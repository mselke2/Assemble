package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Job;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;

class JobDaoImplTest {
  
  @Test
  void retrieveForDate() {
  }
  
  @Test
  void checkPrerequisites() {
  }
  
  @Test
  void addJob() {
    
    Job job = new Job(3, 1, new Timestamp(2026-1900, 0, 1, 12, 0, 0, 0));
    
    ProductDaoImpl productDao = new ProductDaoImpl();
    JobDaoImpl jobDao = new JobDaoImpl();
    
    
    job.setProjectedEndTime(job.getStartTime(), productDao.retrieve(job.getProductId()).getMinutesDuration());
    job.setPersonnelCount(productDao.retrieve(job.getProductId()).getTargetPersonnelCount());
    
    jobDao.addJob(job);
    
    
  }
  
  @Test
  void updateJob() {
  }
  
  @Test
  void deleteJob() {
  }
  
  @Test
  void retrieve() {
  }
}