package com.assemble.java.assemblecodebase.model;

import java.time.LocalDateTime;

public class Session {
  
  int sessionId;
  String loginToken;
  int userId;
  LocalDateTime lastUsed;
  
  public Session() {
  }
  
  public Session(String loginToken, int userId) {
    setLoginToken(loginToken);
    setUserId(userId);
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
  
  @Override
  public String toString() {
    return "Session [sessionId=" + sessionId + ", loginToken=" + loginToken + ", userId=" + userId + "]";
  }
}
