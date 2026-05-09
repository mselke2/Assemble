package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Job;

public interface JobDao {
  
  int addJob(Job job);
  void updateJob(int id, Job job);
  int deleteJob(int id);
  Job retrieve(int id);
}
