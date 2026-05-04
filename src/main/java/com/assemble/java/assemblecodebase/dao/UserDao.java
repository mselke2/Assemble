package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.User;

public interface UserDao {
  
  int addUser(User user);
  void updateUser(User user, String oldPassword);
  void deleteUser(int userId);
  int retrieve(String username, String password);
}
