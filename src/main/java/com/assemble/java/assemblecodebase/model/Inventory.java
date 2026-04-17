package com.assemble.java.assemblecodebase.model;

public class Inventory {
  private int id;
  private int typeId;
  private int count;
  
  public Inventory(int id, int typeId, int count) {
    setId(id);
    setTypeId(typeId);
    setCount(count);
  }
  
  public int getCount() {
    return count;
  }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public int getTypeId() {
    return typeId;
  }
  
  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
}
