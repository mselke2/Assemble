package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Inventory;

public interface InventoryDao {
  
  int addInventory(Inventory inventory);
  void updateInventory(Inventory inventory);
  int deleteInventory(Inventory inventory);
  Inventory retrieve(int id);
}
