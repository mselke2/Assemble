package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Job implements Serializable {
  private int id;
  private int productId;
  private LocalTime startTime;
  private LocalDate startDate;
  private LocalTime projectedEndTime;
  private String actualEndTime;
  private int personnelCount;
  
  public Job(int id, int productId, LocalTime startTime, LocalDate startDate, int personnelCount) {
    setId(id);
    setProductId(productId);
    setStartTime(startTime);
    setStartDate(startDate);
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
  
  public LocalTime getStartTime() {
    return startTime;
  }
  
  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }
  
  public LocalDate getStartDate() {
    return startDate;
  }
  
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }
  
  public LocalTime getProjectedEndTime() {
    return projectedEndTime;
  }
  
  public void setProjectedEndTime(LocalTime projectedEndTime) {
    this.projectedEndTime = projectedEndTime;
  }
  
  public String getActualEndTime() {
    return actualEndTime;
  }
  
  public void setActualEndTime(String actualEndTime) {
    this.actualEndTime = actualEndTime;
  }
  
  public int getPersonnelCount() {
    return personnelCount;
  }
  
  public void setPersonnelCount(int personnelCount) {
    this.personnelCount = personnelCount;
  }
}
