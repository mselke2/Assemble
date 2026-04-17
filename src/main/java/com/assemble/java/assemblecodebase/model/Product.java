package com.assemble.java.assemblecodebase.model;

public class Product {
  private int id;
  private String description;
  private double duration;
  private int targetPersonnelCount;
  
  public Product(int id, String description, double duration, int targetPersonnelCount) {
    setId(id);
    setDescription(description);
    setDuration(duration);
    setTargetPersonnelCount(targetPersonnelCount);
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
  
  public double getDuration() {
    return duration;
  }
  
  public void setDuration(double duration) {
    this.duration = duration;
  }
  
  public int getTargetPersonnelCount() {
    return targetPersonnelCount;
  }
  
  public void setTargetPersonnelCount(int targetPersonnelCount) {
    this.targetPersonnelCount = targetPersonnelCount;
  }
}
