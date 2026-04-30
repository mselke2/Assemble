package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Inventory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryDaoImplTest {
  
  @Test
  void addInventory() {
    
    Inventory inventory = new Inventory(2, 5);
    
    assertDoesNotThrow(() -> {
      InventoryDao inventoryDao = new InventoryDaoImpl();
      System.out.println(inventoryDao.addInventory(inventory));
    });
    
  }
}