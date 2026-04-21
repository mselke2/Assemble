package com.assemble.java.assemblecodebase.dao;

public interface SessionDao {
  
  String createSession(int userId);
  
  boolean removeSession(String sessionId);
  
}
