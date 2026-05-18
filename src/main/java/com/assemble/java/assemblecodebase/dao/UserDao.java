package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.User;
import com.assemble.java.assemblecodebase.model.UserPermission;

import java.util.List;

public interface UserDao {

  int addUser(User user) throws UserDaoException;

  void updateUser(String username, User user);

  void deleteUser(int userId);

  int retrieveWithLogin(String username, String password);

  List<UserPermission> retrievePermissions();

  User[] retrieveAll();

  User retrieveByUsername(String userToGrab);

  User retrieveById(int id);
}
