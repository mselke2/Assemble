package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Job implements Serializable {
  private int id;
  private int productId;
  private int lineNumber;
  private Timestamp startTime;
  private Timestamp projectedEndTime;
  private Timestamp actualEndTime;
  private int personnelCount;
  
  public Job() {
  }
  
  public Job(int id, int productId, int lineNumber, Timestamp startTime, int personnelCount) {
    setId(id);
    setProductId(productId);
    setLineNumber(lineNumber);
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
  
  public int getLineNumber() {
    return lineNumber;
  }
  
  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }
  
  public Timestamp getStartTime() {
    return startTime;
  }
  
  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }
  
  public Timestamp getProjectedEndTime() {
    return projectedEndTime;
  }
  
  public void setProjectedEndTime(Timestamp projectedEndTime) {
    this.projectedEndTime = projectedEndTime;
  }
  
  public Timestamp getActualEndTime() {
    return actualEndTime;
  }
  
  public void setActualEndTime(Timestamp actualEndTime) {
    this.actualEndTime = actualEndTime;
  }
  
  public int getPersonnelCount() {
    return personnelCount;
  }
  
  public void setPersonnelCount(int personnelCount) {
    this.personnelCount = personnelCount;
  }
}
