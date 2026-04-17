package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.User;

public interface UserDao {
  
  void addUser(User user);
  void updateUser(User user);
  void deleteUser(User user);
  User retrieve(User user);
}
