package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;
import com.assemble.java.assemblecodebase.model.EquipmentType;

import java.util.List;

public interface EquipmentDao {
  
  int addEquipment(Equipment equipment);
  boolean updateEquipment(Equipment equipment);
  int deleteEquipmentById(int id);
  Equipment retrieveById(int id);
  List<EquipmentType> retrieveTypes();
  List<Equipment> retrieveAll();

}
