package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Inventory;

public interface InventoryDao {
  
  void addInventory(Inventory inventory);
  void updateInventory(Inventory inventory);
  void deleteInventory(Inventory inventory);
  Inventory retrieve(int id);
}
