package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;

public interface EqipmentDao {
  
  int addEquipment(Equipment equipment);
  void updateEquipment(Equipment equipment);
  int deleteEquipmentById(int id);
  Equipment retrieveById(int id);
  
}
