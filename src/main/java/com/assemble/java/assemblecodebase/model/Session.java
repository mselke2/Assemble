package com.assemble.java.assemblecodebase.model;

public class Session {
  
  String sessionId;
  int userId;
  
  public Session() {
  }
  
  public Session(String sessionId, int userId) {
    setSessionId(sessionId);
    setUserId(userId);
  }
  
  public String getSessionId() {
    return sessionId;
  }
  
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  public int getUserId() {
    return userId;
  }
  
  public void setUserId(int userId) {
    this.userId = userId;
  }
}
