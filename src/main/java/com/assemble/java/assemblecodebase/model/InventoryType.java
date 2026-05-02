package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;

public class InventoryType implements Serializable {
  private int id;
  private String description;

  public InventoryType() {
  }

  public InventoryType(int id, String description) {
    setId(id);
    setDescription(description);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  @Override
  public String toString() {
    return "InventoryType [id=" + id + ", description=" + description + "]";
  }
}
