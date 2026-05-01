package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Inventory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryDaoImplTest {
  
  @Test
  void addInventory() {
    
    Inventory inventory1 = new Inventory(1, 1, 20);
    Inventory inventory2 = new Inventory(2, 3, 15);
    Inventory inventory3 = new Inventory(3, 7, 10);
    Inventory inventory4 = new Inventory(4, 4, 5);
    Inventory inventory5 = new Inventory(5, 6, 8);
    Inventory inventory6 = new Inventory(6, 4, 9);
    Inventory inventory7 = new Inventory(7, 3, 13);
    Inventory inventory8 = new Inventory(8, 3, 20);
    Inventory inventory9 = new Inventory(9, 6, 25);
    Inventory inventory10 = new Inventory(10, 1, 5);
    
    Inventory[] inventories = {inventory1, inventory2, inventory3, inventory4, inventory5, inventory6, inventory7, inventory8, inventory9,  inventory10};
    
    assertDoesNotThrow(() -> {
      InventoryDao inventoryDao = new InventoryDaoImpl();
      for (Inventory inventory : inventories) {
        assertEquals(inventoryDao.addInventory(inventory), inventory.getId());
      }
    });
    
    assertThrows(Exception.class, () -> {
      InventoryDao inventoryDao = new InventoryDaoImpl();
      for (Inventory inventory : inventories) {
        inventoryDao.addInventory(inventory);
      }
    });
    
  }
  
  @Test
  void updateInventory() {
  
    Inventory inventory1 = new Inventory(1, 1, 25);
      Inventory inventory2 = new Inventory(2, 3, 10);
      Inventory inventory3 = new Inventory(3, 7, 12);
      Inventory inventory4 = new Inventory(4, 4, 9);
      Inventory inventory5 = new Inventory(5, 6, 25);
      Inventory inventory6 = new Inventory(6, 4, 7);
      Inventory inventory7 = new Inventory(7, 3, 18);
      Inventory inventory8 = new Inventory(8, 3, 12);
      Inventory inventory9 = new Inventory(9, 6, 20);
      Inventory inventory10 = new Inventory(10, 1, 14);
      
      Inventory doesNotExist = new Inventory(11, 1, 5);
      
      Inventory[] inventories = {inventory1, inventory2, inventory3, inventory4, inventory5, inventory6, inventory7, inventory8, inventory9,  inventory10};
      
      assertDoesNotThrow(() -> {
        InventoryDao inventoryDao = new InventoryDaoImpl();
        
        for (Inventory inventory : inventories) {
          assertTrue(inventoryDao.updateInventory(inventory));
        }
        
        assertThrows(Exception.class, () -> {
          inventoryDao.updateInventory(doesNotExist);
        });
        
      });
  }
  
  @Test
  void deleteInventoryById() {
  
    assertDoesNotThrow(() -> {
      InventoryDao inventoryDao = new InventoryDaoImpl();
      for (int i = 1; i <= 10; i++) {
        assertEquals(inventoryDao.deleteInventoryById(i), i);
      }
      
      assertThrows(Exception.class, () -> {
        inventoryDao.deleteInventoryById(1);
      });
    });
  
    
  }
}