package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

public class Job implements Serializable {
  private int id = -1;
  private int productId;
  private String productName;
  private int lineNumber;
  private Timestamp startTime;
  private Timestamp projectedEndTime;
  private int personnelCount;

  Product product;

  public Job() {
  }

  public Job(int productId, int lineNumber, Timestamp startTime, int personnelCount) {
    setProductId(productId);
    setLineNumber(lineNumber);
    setStartTime(startTime);
    setProjectedEndTime(startTime);
    setPersonnelCount(personnelCount);
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
    cal.add(Calendar.MINUTE, duration);
    this.projectedEndTime = new Timestamp(cal.getTime().getTime());
  }
  
  public void setProjectedEndTime(Timestamp startTime) {
    this.projectedEndTime = startTime;
  }
  
  public int getPersonnelCount() {
    return personnelCount;
  }
  
  public void setPersonnelCount(int personnelCount) {
    this.personnelCount = personnelCount;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public String toString() {
    return "Job [id=" + id + ", productId=" + productId + ", lineNumber=" +
      lineNumber + ", startTime=" + startTime + ", projectedEndTime=" +
      projectedEndTime +
      ", personnelCount=" + personnelCount + "]";
  }
}
