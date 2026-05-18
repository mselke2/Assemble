package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Personnel;

import java.sql.Date;
import java.util.List;

public interface PersonnelDao {
  void set(Date date, int count);

  int retrieveCount(Date date);

  Personnel retrieve(int id);

  void delete(Date date);

  List<Personnel> retrieveAll();
}
