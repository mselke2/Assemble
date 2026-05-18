package com.assemble.java.assemblecodebase.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionDaoImplTest {

  @Test
  void createSession() {

    SessionDao sessionDaoImpl = new SessionDaoImpl();

    assertDoesNotThrow(() -> {
      String sessionId = sessionDaoImpl.createSession(3275);
      System.out.println("Session ID created: " + sessionId);
    });
  }

  @Test
  void removeSession() {
    SessionDao sessionDaoImpl = new SessionDaoImpl();

    assertDoesNotThrow(() -> {
      String sessionId = sessionDaoImpl.removeSession(3275);
      System.out.println("Session ID removed for user ID 3275: " + sessionId);
    });

  }

  @Test
  void retrieve() {
    SessionDao sessionDaoImpl = new SessionDaoImpl();

    assertDoesNotThrow(() -> {
      String sessionId = sessionDaoImpl.retrieve(3275);

      assertNotNull(sessionId);
      System.out.println("Session ID retrieved: " + sessionId);

      assertTrue(sessionDaoImpl.retrieve(sessionId) > 0);
      System.out.println("Session ID is valid and associated with user ID: " + sessionDaoImpl.retrieve(sessionId));
    });
  }

  @Test
  void stampSession() {
    SessionDao sessionDaoImpl = new SessionDaoImpl();
    assertDoesNotThrow(() -> {
      sessionDaoImpl.stampSession(3275);
    });
  }
}