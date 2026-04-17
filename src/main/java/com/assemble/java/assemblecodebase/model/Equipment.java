package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;

public class Equipment implements Serializable {
  private int id;
  private int typeId;
  private String description;
  
  public Equipment(int id, String description, int typeId) {
    setId(id);
    setDescription(description);
    setTypeId(typeId);
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getTypeId() {
    return typeId;
  }
  
  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
}
