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
    
    String passwordHash = DigestUtils.sha256Hex("testingPasswordOnly");
    
    User user = new User("aselke2", 2, "Amber", "Selke", passwordHash);
    
    UserDaoImpl userDao = new UserDaoImpl();
    
    assertDoesNotThrow(() -> assertTrue(userDao.addUser(user) > 0));
    
    assertThrows(Exception.class, () -> userDao.addUser(user));

  }
}