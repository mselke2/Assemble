package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;

public class User implements Serializable {
  private int id;
  private String username;
  private int permissionId = 4;
  private String firstName;
  private String lastName;
  private String passwordHash;

  public User() {
  }

  public User(int id, String username, int permissionId, String firstName, String lastName, String passwordHash) {
    setId(id);
    setUsername(username);
    setPermissionId(permissionId);
    setFirstName(firstName);
    setLastName(lastName);
    setPasswordHash(passwordHash);
  }

  public User(String username, int permissionId, String firstName, String lastName, String passwordHash) {
    setUsername(username);
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", permissionId=" + permissionId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
  }

  public Boolean clearanceAtLeast(String requiredPermission) {
    if (requiredPermission.equals("admin") && permissionId == 1)
      return true;

    if (requiredPermission.equals("editor") && permissionId <= 2)
      return true;

    return requiredPermission.equals("viewer") && permissionId <= 3;
  }
}
