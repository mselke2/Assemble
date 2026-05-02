package com.assemble.java.assemblecodebase.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionDaoImplTest {
  
  @Test
  void createSession() {
    
    SessionDaoImpl sessionDaoImpl = new SessionDaoImpl();
    
    assertDoesNotThrow(() -> {
      String sessionId = sessionDaoImpl.createSession(1);
      System.out.println("Session ID created: " + sessionId);
    });
  }
}