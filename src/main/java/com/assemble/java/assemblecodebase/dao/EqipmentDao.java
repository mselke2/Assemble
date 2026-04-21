package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;

public interface EqipmentDao {
  
  int addEquipment(int typeId, String description);
  void updateEquipment(int typeId, String description);
  int deleteEquipment(int typeId);
  Equipment retrieve(int id);
  
}
