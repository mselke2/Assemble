package com.assemble.java.assemblecodebase.model;

import java.sql.Date;

public class Personnel {

  private int id;
  private Date date;
  private int count;

  public Personnel(Date date, int count) {
    setDate(date);
    setCount(count);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
