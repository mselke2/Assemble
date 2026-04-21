package com.assemble.java.assemblecodebase.dao;

public interface SessionDao {
  
  String createSession(int userId);
  
  int removeSession(String sessionId);
  
  int retrieve(String sessionId);
  
}
