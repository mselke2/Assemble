package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;
import java.time.LocalTime;

public class Product implements Serializable {
  private int id;
  private String description;
  private LocalTime duration;
  private int targetPersonnelCount;
  
  public Product(int id, String description, LocalTime duration, int targetPersonnelCount) {
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
  
  public LocalTime getDuration() {
    return duration;
  }
  
  public void setDuration(LocalTime duration) {
    this.duration = duration;
  }
  
  public int getTargetPersonnelCount() {
    return targetPersonnelCount;
  }
  
  public void setTargetPersonnelCount(int targetPersonnelCount) {
    this.targetPersonnelCount = targetPersonnelCount;
  }
}
