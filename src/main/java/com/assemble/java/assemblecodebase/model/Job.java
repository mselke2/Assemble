package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Job implements Serializable {
  private int id;
  private int productId;
  private LocalDateTime startTime;
  private LocalDateTime projectedEndTime;
  private LocalDateTime actualEndTime;
  private int personnelCount;
  
  public Job() {
  }
  
  public Job(int id, int productId, LocalDateTime startTime, int personnelCount) {
    setId(id);
    setProductId(productId);
    setStartTime(startTime);
    setProjectedEndTime(startTime);
    setPersonnelCount(personnelCount);
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getProductId() {
    return productId;
  }
  
  public void setProductId(int productId) {
    this.productId = productId;
  }
  
  public LocalDateTime getStartTime() {
    return startTime;
  }
  
  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }
  
  public LocalDateTime getProjectedEndTime() {
    return projectedEndTime;
  }
  
  public void setProjectedEndTime(LocalDateTime projectedEndTime) {
    this.projectedEndTime = projectedEndTime;
  }
  
  public LocalDateTime getActualEndTime() {
    return actualEndTime;
  }
  
  public void setActualEndTime(LocalDateTime actualEndTime) {
    this.actualEndTime = actualEndTime;
  }
  
  public int getPersonnelCount() {
    return personnelCount;
  }
  
  public void setPersonnelCount(int personnelCount) {
    this.personnelCount = personnelCount;
  }
}
