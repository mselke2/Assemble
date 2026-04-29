package com.assemble.java.assemblecodebase.test;

import com.assemble.java.assemblecodebase.dao.UserDaoImpl;
import com.assemble.java.assemblecodebase.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.security.DigestException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {
  
  @Test
  void addUser() {
    
    String passwordHash = DigestUtils.sha256Hex("password");
    
    User user1 = new User("Test1", 1, "Test", "One", passwordHash);
    User user2 = new User("Test2", 1, "Test", "Two", passwordHash);
    User user3 = new User("Test3", 1, "Test", "Three", passwordHash);
    User user4 = new User("Test4", 1, "Test", "Four", passwordHash);
    User user5 = new User("Test5", 1, "Test", "Five", passwordHash);
    User user6 = new User("Test6", 1, "Test", "Six", passwordHash);
    
    UserDaoImpl userDao = new UserDaoImpl();
    
    assertDoesNotThrow(() -> {
      
      assertTrue(userDao.addUser(user1) > 0);
      assertTrue(userDao.addUser(user2) > 0);
      assertTrue(userDao.addUser(user3) > 0);
      assertTrue(userDao.addUser(user4) > 0);
      assertTrue(userDao.addUser(user5) > 0);
      assertTrue(userDao.addUser(user6) > 0);
    
    });
    
    assertThrows(Exception.class, () -> userDao.addUser(user1));
    assertThrows(Exception.class, () -> userDao.addUser(user2));
    assertThrows(Exception.class, () -> userDao.addUser(user3));
    assertThrows(Exception.class, () -> userDao.addUser(user4));
    assertThrows(Exception.class, () -> userDao.addUser(user5));
    assertThrows(Exception.class, () -> userDao.addUser(user6));

  }
}