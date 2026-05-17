package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {
  
  @Test
  void addUser() {
    
    String testPass = "Password!";
    
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
    String newPassword = "passwordUpdated";
    
    User user1 = new User("Test1", 1, "Test", "One", newPassword);
    User user2 = new User("Test2", 1, "Test", "Two", newPassword);
    User user3 = new User("Test3", 1, "Test", "Three", newPassword);
    User user4 = new User("Test4", 1, "Test", "Four", newPassword);
    User user5 = new User("Test5", 1, "Test", "Five", newPassword);
    User user6 = new User("Test6", 1, "Test", "Six", newPassword);
    
    UserDaoImpl userDao = new UserDaoImpl();
    
    assertDoesNotThrow(() -> {
      
      userDao.updateUser("Test1", user1);
      userDao.updateUser("Test2",user2);
      userDao.updateUser("Test3",user3);
      userDao.updateUser("Test4",user4);
      userDao.updateUser("Test5",user5);
      userDao.updateUser("Test6",user6);
    
    });
    
  }
  
  @Test
  void deleteUser() {
    
    UserDaoImpl userDao = new UserDaoImpl();
    assertDoesNotThrow(() -> {
      
      userDao.deleteUser(1);
      userDao.deleteUser(2);
      userDao.deleteUser(3);
      userDao.deleteUser(4);
      userDao.deleteUser(5);
      userDao.deleteUser(6);
      
      assertThrows(Exception.class, () -> userDao.deleteUser(7));
    });
  }
  
  @Test
  void retrieveWithLogin() {
    UserDaoImpl userDao = new UserDaoImpl();
    String password = "passwordUpdated";
    assertDoesNotThrow(() -> {
      assertEquals(1, userDao.retrieveWithLogin("Test1", password));
      assertEquals(2, userDao.retrieveWithLogin("Test2", password));
      assertEquals(3, userDao.retrieveWithLogin("Test3", password));
      assertEquals(4, userDao.retrieveWithLogin("Test4", password));
      assertEquals(5, userDao.retrieveWithLogin("Test5", password));
      assertEquals(6, userDao.retrieveWithLogin("Test6", password));
      
      assertThrows(Exception.class, () -> userDao.retrieveWithLogin("Test1", "wrong"));
      assertThrows(Exception.class, () -> userDao.retrieveWithLogin("Test2", "wrong"));
      assertThrows(Exception.class, () -> userDao.retrieveWithLogin("Test3", "wrong"));
      assertThrows(Exception.class, () -> userDao.retrieveWithLogin("Test4", "wrong"));
      assertThrows(Exception.class, () -> userDao.retrieveWithLogin("Test5", "wrong"));
      assertThrows(Exception.class, () -> userDao.retrieveWithLogin("Test6", "wrong"));
    });
  }
}