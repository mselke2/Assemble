package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;

public class  Product implements Serializable {
  private int id;
  private String description;
  private int minutesDuration;
  private int targetPersonnelCount;
  
  public Product() {
  }
  
  public Product(int id, String description, int minutesDuration, int targetPersonnelCount) {
    setId(id);
    setDescription(description);
    setMinutesDuration(minutesDuration);
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
  
  public int getMinutesDuration() {
    return minutesDuration;
  }
  
  public void setMinutesDuration(int minutesDuration) {
    this.minutesDuration = minutesDuration;
  }
  
  public int getTargetPersonnelCount() {
    return targetPersonnelCount;
  }
  
  public void setTargetPersonnelCount(int targetPersonnelCount) {
    this.targetPersonnelCount = targetPersonnelCount;
  }
  
  @Override
  public String toString() {
    return "Product [id=" + id + ", description=" + description + ", duration=" + minutesDuration +
      ", targetPersonnelCount=" + targetPersonnelCount + "]";
  }
}
