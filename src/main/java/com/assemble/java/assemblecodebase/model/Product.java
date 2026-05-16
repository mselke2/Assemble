package com.assemble.java.assemblecodebase.model;

import java.io.Serializable;
import java.util.List;

public class  Product implements Serializable {
  private int id;
  private String description;
  private int minutesDuration;
  private int targetPersonnelCount;
  private List<Integer> requiredInventoryIds;
  private List<String> requiredInventoryDescriptions;
  private List<Integer> requiredInventoryCounts;
  private List<Integer> requiredEquipmentIds;
  private List<String> requiredEquipmentDescriptions;
  private List<Integer> requiredEquipmentCounts;

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

  public List<Integer> getRequiredInventoryIds() {
    return requiredInventoryIds;
  }

  public void setRequiredInventoryIds(List<Integer> requiredInventoryIds) {
    this.requiredInventoryIds = requiredInventoryIds;
  }

  public List<String> getRequiredInventoryDescriptions() {
    return requiredInventoryDescriptions;
  }

  public void setRequiredInventoryDescriptions(List<String> requiredInventoryDescriptions) {
    this.requiredInventoryDescriptions = requiredInventoryDescriptions;
  }

  public List<Integer> getRequiredInventoryCounts() {
    return requiredInventoryCounts;
  }

  public void setRequiredInventoryCounts(List<Integer> requiredInventoryCounts) {
    this.requiredInventoryCounts = requiredInventoryCounts;
  }

  public List<Integer> getRequiredEquipmentIds() {
    return requiredEquipmentIds;
  }

  public void setRequiredEquipmentIds(List<Integer> requiredEquipmentIds) {
    this.requiredEquipmentIds = requiredEquipmentIds;
  }

  public List<String> getRequiredEquipmentDescriptions() {
    return requiredEquipmentDescriptions;
  }

  public void setRequiredEquipmentDescriptions(List<String> requiredEquipmentDescriptions) {
    this.requiredEquipmentDescriptions = requiredEquipmentDescriptions;
  }

  public List<Integer> getRequiredEquipmentCounts() {
    return requiredEquipmentCounts;
  }

  public void setRequiredEquipmentCounts(List<Integer> requiredEquipmentCounts) {
    this.requiredEquipmentCounts = requiredEquipmentCounts;
  }

  @Override
  public String toString() {
    return "Product [id=" + id + ", description=" + description + ", duration=" + minutesDuration +
      ", targetPersonnelCount=" + targetPersonnelCount + "]";
  }
}
