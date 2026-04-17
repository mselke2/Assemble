package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Job;

public interface JobDao {
  
  void addJob(Job job);
  void updateJob(Job job);
  void deleteJob(Job job);
  Job retrieve(int id);
}
