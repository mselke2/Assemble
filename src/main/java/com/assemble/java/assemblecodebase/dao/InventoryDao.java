package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Inventory;
import com.assemble.java.assemblecodebase.model.InventoryType;

import java.util.List;

public interface InventoryDao {

  int addInventory(Inventory inventory);

  boolean updateInventory(Inventory inventory);

  int deleteInventoryById(int id);

  Inventory retrieveById(int id);

  List<InventoryType> retrieveTypes();

  List<Inventory> retrieveAll();

  boolean updateInventoryType(InventoryType inventoryType);

  int addInventoryType(InventoryType inventoryType);

  int deleteInventoryTypeById(int id);

  InventoryType retrieveTypeById(int id);
}
