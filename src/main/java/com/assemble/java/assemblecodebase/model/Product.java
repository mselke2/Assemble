package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

public class Product implements Serializable {
  private int id;
  private String description;
  private Time duration;
  private int targetPersonnelCount;
  
  public Product() {
  }
  
  public Product(int id, String description, Time duration, int targetPersonnelCount) {
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
  
  public Time getDuration() {
    return duration;
  }
  
  public void setDuration(Time duration) {
    this.duration = duration;
  }
  
  public int getTargetPersonnelCount() {
    return targetPersonnelCount;
  }
  
  public void setTargetPersonnelCount(int targetPersonnelCount) {
    this.targetPersonnelCount = targetPersonnelCount;
  }
  
  @Override
  public String toString() {
    return "Product [id=" + id + ", description=" + description + ", duration=" +  duration +
      ", targetPersonnelCount=" + targetPersonnelCount + "]";
  }
}
