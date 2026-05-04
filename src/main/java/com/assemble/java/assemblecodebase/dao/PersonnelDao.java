package com.assemble.java.assemblecodebase.dao;

import java.sql.Date;

public interface PersonnelDao {
  void set(Date date, int count);
  void retrieve(Date date);
  void delete(Date date);
}
