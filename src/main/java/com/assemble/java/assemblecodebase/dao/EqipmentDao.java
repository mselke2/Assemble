package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;

public interface EqipmentDao {
  
  void addEquipment(int typeId, String description);
  void updateEquipment(int typeId, String description);
  void deleteEquipment(int typeId);
  Equipment retrieve(int id);
  
}
