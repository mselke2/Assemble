package com.assemble.java.assemblecodebase.model;

public class Equipment {
  private int typeId;
  private String description;
  
  public Equipment(String description, int typeId) {
    setDescription(description);
    setTypeId(typeId);
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
