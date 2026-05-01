package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;

public class Equipment implements Serializable {
  private int id;
  private int typeId;
  private int status;
  
  public Equipment() {
  }
  
  public Equipment(int id, int typeId, int status) {
    setId(id);
    setTypeId(typeId);
    setStatus(status);
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
  
  public int getStatus() {
    return status;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
}
