package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Inventory;

public interface InventoryDao {
  
  int addInventory(Inventory inventory);
  boolean updateInventory(Inventory inventory);
  int deleteInventoryById(int id);
  Inventory retrieve(int id);
}
