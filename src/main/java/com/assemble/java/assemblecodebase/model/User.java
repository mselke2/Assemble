package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;

public class User implements Serializable {
  private int id;
  private int permissionId;
  private String firstName;
  private String lastName;
  private String passwordHash;
  
  public User() {
  }
  
  public User(int id, int permissionId, String firstName, String lastName, String passwordHash) {
    setId(id);
    setPermissionId(permissionId);
    setFirstName(firstName);
    setLastName(lastName);
    setPasswordHash(passwordHash);
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getPermissionId() {
    return permissionId;
  }
  
  public void setPermissionId(int permissionId) {
    this.permissionId = permissionId;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getPasswordHash() {
    return passwordHash;
  }
  
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }
}
