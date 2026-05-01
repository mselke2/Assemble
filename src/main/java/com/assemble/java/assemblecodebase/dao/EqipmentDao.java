package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;

public interface EqipmentDao {
  
  int addEquipment(int id, int typeId, int count);
  void updateEquipment(int id, int typeId, int count);
  int deleteEquipmentById(int id);
  Equipment retrieveById(int id);
  
}
