package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {
  
  @Test
  void addUser() {
    
    String testPass = "password";
    
    User user1 = new User("Test1", 1, "Test", "One", testPass);
    User user2 = new User("Test2", 1, "Test", "Two", testPass);
    User user3 = new User("Test3", 1, "Test", "Three", testPass);
    User user4 = new User("Test4", 1, "Test", "Four", testPass);
    User user5 = new User("Test5", 1, "Test", "Five", testPass);
    User user6 = new User("Test6", 1, "Test", "Six", testPass);
    
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
  
  @Test
  void updateUser() {
    
    String testPass = "password";
    String newPassword = "passwordUpdated";
    
    User user1 = new User("Test1", 1, "Test", "One", newPassword);
    User user2 = new User("Test2", 1, "Test", "Two", newPassword);
    User user3 = new User("Test3", 1, "Test", "Three", newPassword);
    User user4 = new User("Test4", 1, "Test", "Four", newPassword);
    User user5 = new User("Test5", 1, "Test", "Five", newPassword);
    User user6 = new User("Test6", 1, "Test", "Six", newPassword);
    
    UserDaoImpl userDao = new UserDaoImpl();
    
    assertDoesNotThrow(() -> {
      
      userDao.updateUser(user1, DigestUtils.sha256Hex(testPass + "1"));
      userDao.updateUser(user2, DigestUtils.sha256Hex(testPass + "2"));
      userDao.updateUser(user3, DigestUtils.sha256Hex(testPass + "3"));
      userDao.updateUser(user4, DigestUtils.sha256Hex(testPass + "4"));
      userDao.updateUser(user5, DigestUtils.sha256Hex(testPass + "5"));
      userDao.updateUser(user6, DigestUtils.sha256Hex(testPass + "6"));
    
    });
    
  }
}