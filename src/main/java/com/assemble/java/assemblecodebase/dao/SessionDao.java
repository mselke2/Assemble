package com.assemble.java.assemblecodebase.dao;

public interface SessionDao {

  String createSession(int userId);

  String removeSession(int userId);

  int retrieve(String sessionId);

  String retrieve(int userId);

  boolean stampSession(int userId);
}
