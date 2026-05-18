package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;

public class Inventory implements Serializable {
  private int id;
  private int typeId;
  private String typeDescription;
  private int count;

  public Inventory() {
  }

  public Inventory(int typeId, int count) {
    setTypeId(typeId);
    setCount(count);
  }

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

  public String getTypeDescription() {
    return typeDescription;
  }

  public void setTypeDescription(String typeDescription) {
    this.typeDescription = typeDescription;
  }

  @Override
  public String toString() {
    return "Inventory [id=" + id + ", typeId=" + typeId + ", count=" + count + "]";
  }
}
