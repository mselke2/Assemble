package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;

public interface EquipmentDao {
  
  int addEquipment(Equipment equipment);
  boolean updateEquipment(Equipment equipment);
  int deleteEquipmentById(int id);
  Equipment retrieveById(int id);
  
}
