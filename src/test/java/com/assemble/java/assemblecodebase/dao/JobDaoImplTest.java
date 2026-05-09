package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Job;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class JobDaoImplTest {
  
  @Test
  void retrieveForDate() {
    
    JobDaoImpl jobDaoImpl = new JobDaoImpl();
    
    assertDoesNotThrow(() -> {
      Job[] jobs = jobDaoImpl.retrieveForDate(new Date(2026-1900, 0, 1));
      
      for  (int i = 0; i < jobs.length; i++) {
        System.out.println(jobs[i].getId() + ", " + jobs[i].getProductId() + ", " + jobs[i].getStartTime());
      }
    });
  }
  
  @Test
  void checkPrerequisites() {
  }
  
  @Test
  void addJob() {
    
    Job job = new Job(3, 2, new Timestamp(2026-1900, 0, 1, 20, 0, 0, 0));
    
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
    JobDaoImpl jobDao = new JobDaoImpl();
    
    assertDoesNotThrow(() -> {
      
      assertTrue(jobDao.retrieve(1) instanceof Job);
      
      assertThrows(Exception.class, () -> jobDao.retrieve(-1));
    });
  }
  
  @Test
  void fillCommittedInventoryCounts() {
    
    JobDaoImpl jobDao = new JobDaoImpl();
    assertDoesNotThrow(() -> {
      
      Job job = new Job(3, 6, new Timestamp(2026-1900, 0, 1, 14, 0, 0, 0));
      job.setProjectedEndTime(new  Timestamp(2026-1900, 0, 1, 16, 0, 0, 0));
      
      jobDao.addJob(job);
      jobDao.fillCommittedInventoryCounts(new Timestamp(2026-1900, 0, 1, 15, 0, 0, 0));
      
      int[][] inventoryCounts = jobDao.getInventoryCounts();
      
      for (int i = 0; i < inventoryCounts[3].length; i++) {
        System.out.println(inventoryCounts[0][i] + ", " + inventoryCounts[3][i]);
      }
      
    });
    
    
  }
  
  @Test
  void fillCommittedEquipmentCount() {
    
    JobDaoImpl jobDao = new JobDaoImpl();
    assertDoesNotThrow(() -> {
      
      Job job = new Job(3, 6, new Timestamp(2026-1900, 0, 1, 14, 0, 0, 0));
      job.setProjectedEndTime(new  Timestamp(2026-1900, 0, 1, 16, 0, 0, 0));
      
      jobDao.addJob(job);
      jobDao.fillCommittedEquipmentCount(new Timestamp(2026-1900, 0, 1, 15, 0, 0, 0));
      
      int[][] equipmentCounts = jobDao.getEquipmentCounts();
      
      for (int i = 0; i < equipmentCounts[3].length; i++) {
        System.out.println(equipmentCounts[0][i] + ", " + equipmentCounts[3][i]);
      }
      
    });
  }
  
  @Test
  void calculateCommittedPersonnelCount() {
    
    JobDaoImpl jobDao = new JobDaoImpl();
    assertDoesNotThrow(() -> {
      
      int count = jobDao.calculateCommittedPersonnelCount(new Timestamp(2026-1900, 0, 1, 19, 0, 0, 0));
      assertEquals(10, count);
      
      System.out.println(count);
      
    });
  }
}