package com.assemble.java.assemblecodebase.model;

public class Job {
  private int id;
  private int productId;
  private String startTime;
  private String projectedEndTime;
  private String actualEndTime;
  private int personnelCount;
  
  public Job(int id, int productId, String startTime, int personnelCount) {
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
  
  public String getStartTime() {
    return startTime;
  }
  
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  
  public String getProjectedEndTime() {
    return projectedEndTime;
  }
  
  public void setProjectedEndTime(String projectedEndTime) {
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
