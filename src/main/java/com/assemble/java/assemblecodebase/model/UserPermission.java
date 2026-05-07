package com.assemble.java.assemblecodebase.model;

public class UserPermission {
  private int id;
  private String description;

  public UserPermission() {
  }

  public UserPermission(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public UserPermission(String description) {
    this.description = description;
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
}
