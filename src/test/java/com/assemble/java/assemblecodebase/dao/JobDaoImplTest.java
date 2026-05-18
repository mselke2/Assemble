package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Job;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JobDaoImplTest {

  @Test
  void retrieveForDate() {

    JobDaoImpl jobDaoImpl = new JobDaoImpl();

    assertDoesNotThrow(() -> {
      List<Job> jobs = jobDaoImpl.retrieveForDate(LocalDate.of(2026, 1, 1));

      for (int i = 0; i < jobs.size(); i++) {
        System.out.println(jobs.get(i).getId() + ", " + jobs.get(i).getProductId() + ", " + jobs.get(i).getStartTime());
      }
    });
  }

  @Test
  void checkPrerequisites() {
  }

  @Test
  void addJob() {

    Job job = new Job(3, 1, new Timestamp(2026 - 1900, 0, 1, 14, 0, 0, 0));

    ProductDaoImpl productDao = new ProductDaoImpl();
    JobDaoImpl jobDao = new JobDaoImpl();


    job.setProjectedEndTime(job.getStartTime(), productDao.retrieve(job.getProductId()).getMinutesDuration());
    job.setPersonnelCount(productDao.retrieve(job.getProductId()).getTargetPersonnelCount());

    jobDao.addJob(job);


  }

  @Test
  void updateJob() {

    JobDaoImpl jobDao = new JobDaoImpl();
    ProductDaoImpl productDao = new ProductDaoImpl();

    assertDoesNotThrow(() -> {
      Job job = new Job(3, 1, new Timestamp(2026 - 1900, 0, 1, 20, 0, 0, 0));
      job.setId(1);
      job.setProjectedEndTime(job.getStartTime(), productDao.retrieve(job.getProductId()).getMinutesDuration());
      job.setPersonnelCount(productDao.retrieve(job.getProductId()).getTargetPersonnelCount());
      jobDao.updateJob(job);
    });
  }

  @Test
  void deleteJob() {
    JobDaoImpl jobDao = new JobDaoImpl();

    assertDoesNotThrow(() -> {
      jobDao.deleteJob(1);
    });
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
  void replaceInventory() {

    JobDaoImpl jobDao = new JobDaoImpl();
    assertDoesNotThrow(() -> {
      jobDao.replaceInventory(3);
    });
  }
}