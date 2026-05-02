package com.assemble.java.assemblecodebase.model;

import java.sql.Timestamp;

public class Session {
  
  int id;
  String loginToken;
  int userId;
  Timestamp lastUsed;
  
  public Session() {
  }
  
  public Session(int sessionId, String loginToken, int userId, Timestamp lastUsed) {
    setId(sessionId);
    setLoginToken(loginToken);
    setUserId(userId);
    setLastUsed(lastUsed);
  }
  
  public String getLoginToken() {
    return loginToken;
  }
  
  public void setLoginToken(String loginToken) {
    this.loginToken = loginToken;
  }
  
  public int getUserId() {
    return userId;
  }
  
  public void setUserId(int userId) {
    this.userId = userId;
  }
  
  public Timestamp getLastUsed() {
    return lastUsed;
  }
  
  public void setLastUsed(Timestamp lastUsed) {
    this.lastUsed = lastUsed;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  @Override
  public String toString() {
    return "Session [sessionId=" + id + ", loginToken=" + loginToken + ", userId=" + userId + "]";
  }
}
