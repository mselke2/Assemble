package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Equipment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentDaoImplTest {
  
  @Test
  void addEquipment() {
    
    assertDoesNotThrow(() -> {
      Equipment equipment0 = new Equipment(1, 2, 1);
      Equipment equipment1 = new Equipment(2, 3, 1);
      Equipment equipment2 = new Equipment(3, 5, 0);
      Equipment equipment3 = new Equipment(4, 1, 1);
      Equipment equipment4 = new Equipment(5, 7, 1);
      Equipment equipment5 = new Equipment(6, 6, 1);
      Equipment equipment6 = new Equipment(7, 5, 0);
      Equipment equipment7 = new Equipment(8, 4, 1);
      Equipment equipment8 = new Equipment(9, 2, 0);
      Equipment equipment9 = new Equipment(10, 2, 1);
      
      Equipment[] equipments = {equipment0, equipment1, equipment2,
        equipment3, equipment4,  equipment5,
        equipment6, equipment7, equipment8,
        equipment9};
      
      EquipmentDaoImpl equipmentDaoImpl = new EquipmentDaoImpl();
      for (int i = 0; i < equipments.length; i++) {
        assertEquals(i+1, equipmentDaoImpl.addEquipment(equipments[i]));
      }
      
    });
    
  }
  
  @Test
  void updateEquipment() {
  
    assertDoesNotThrow(() -> {
      Equipment equipment0 = new Equipment(1, 2, 0);
      Equipment equipment1 = new Equipment(2, 3, 0);
      Equipment equipment2 = new Equipment(3, 5, 0);
      Equipment equipment3 = new Equipment(4, 1, 1);
      Equipment equipment4 = new Equipment(5, 7, 1);
      Equipment equipment5 = new Equipment(6, 6, 1);
      Equipment equipment6 = new Equipment(7, 5, 1);
      Equipment equipment7 = new Equipment(8, 4, 1);
      Equipment equipment8 = new Equipment(9, 2, 1);
      Equipment equipment9 = new Equipment(10, 2, 1);
      
      EquipmentDaoImpl equipmentDaoImpl = new EquipmentDaoImpl();
      Equipment[] equipments = {equipment0, equipment1, equipment2,
        equipment3, equipment4,  equipment5,
        equipment6, equipment7, equipment8,
        equipment9};
      
      for (Equipment value : equipments) {
        assertTrue(equipmentDaoImpl.updateEquipment(value));
      }
      
      assertThrows(Exception.class, () -> {
        Equipment equipment = new Equipment(11, 4, 1);
        equipmentDaoImpl.updateEquipment(equipment);
      });
      
    });
  }
  
  @Test
  void deleteEquipmentById() {
    
    assertDoesNotThrow(() -> {
      EquipmentDaoImpl equipmentDaoImpl = new EquipmentDaoImpl();
      
      for (int i = 1; i <= 10; i++) {
        assertEquals(i, equipmentDaoImpl.deleteEquipmentById(i));
      }
      
      assertThrows(Exception.class, () -> equipmentDaoImpl.deleteEquipmentById(1));
      
    });
  }
  
  @Test
  void retrieveById() {
    
    EquipmentDaoImpl equipmentDaoImpl = new EquipmentDaoImpl();
    
    assertDoesNotThrow(() -> {
      
      for (int i = 1; i <= 10; i++) {
        Equipment equipment = equipmentDaoImpl.retrieveById(i);
        assertEquals(i, equipment.getId());
        assertTrue(equipment.getTypeId() > 0);
        assertTrue(equipment.getStatus() == 0 || equipment.getStatus() == 1);
        System.out.println("Equipment ID: " + equipment.getId() + ", Type ID: " + equipment.getTypeId() + ", Status: " + equipment.getStatus());
      }
      
      assertThrows(Exception.class, () -> {
        Equipment equipment = equipmentDaoImpl.retrieveById(15);
      });
    });
  }
}