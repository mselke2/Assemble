package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

public class Job implements Serializable {
  private int id = -1;
  private int productId;
  private int lineNumber;
  private Timestamp startTime;
  private Timestamp projectedEndTime;
  private Timestamp actualEndTime;
  private int personnelCount;
  
  // These are 2D arrays used as a kind of database table on the server.
  // The structure looks like this:
  // {
  //   {Col 0,           Col 1,           Col 2,           ...}
  //   ------------------------------------------------------------
  //   {TypeID1,         TypeID2,         TypeID3,         ...},  |   {Row 0}
  //   {RequiredCount1,  RequiredCount2,  RequiredCount3,  ...},  |   {Row 1}
  //   {AvailableCount1, AvailableCount2, AvailableCount3, ...},  |   {Row 2}
  //   {Leftover1,       Leftover2,       Leftover3,       ...}   |   {Row 3}
  // }
  
  private int[][] inventoryCounts;
  private int[][] equipmentCounts;
  
  public Job() {
  }
  
  public Job(int productId, int lineNumber, Timestamp startTime) {
    setProductId(productId);
    setLineNumber(lineNumber);
    setStartTime(startTime);
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
  
  public void setProjectedEndTime(Timestamp startTime, int duration) {
    Calendar cal =  Calendar.getInstance();
    cal.setTime(startTime);
    System.out.println("Start time: " + startTime);
    cal.add(Calendar.MINUTE, duration);
    System.out.println("Projected end time: " + new Timestamp(cal.getTime().getTime()));
    this.projectedEndTime = new Timestamp(cal.getTime().getTime());
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
  
  public int[][] getInventoryCounts() {
    return inventoryCounts;
  }
  
  public void setInventoryCounts(int[][] inventoryCounts) {
    this.inventoryCounts = inventoryCounts;
  }
  
  public int[][] getEquipmentCounts() {
    return equipmentCounts;
  }
  
  public void setEquipmentCounts(int[][] equipmentCounts) {
    this.equipmentCounts = equipmentCounts;
  }
  
  @Override
  public String toString() {
    return "Job [id=" + id + ", productId=" + productId + ", lineNumber=" +
      lineNumber + ", startTime=" + startTime + ", projectedEndTime=" +
      projectedEndTime + ", actualEndTime=" + actualEndTime +
      ", personnelCount=" + personnelCount + "]";
  }
}
