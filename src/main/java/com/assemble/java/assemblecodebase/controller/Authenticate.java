package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.SessionDao;
import com.assemble.java.assemblecodebase.dao.SessionDaoImpl;
import com.assemble.java.assemblecodebase.dao.UserDao;
import com.assemble.java.assemblecodebase.dao.UserDaoImpl;
import com.assemble.java.assemblecodebase.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Optional;

public class Authenticate {
  static public User RetrieveRequestingUser(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      Optional<Cookie> maybeCookie = Arrays.stream(cookies)
          .filter(c -> c.getName().equals("loginToken"))
          .findFirst();

      if (maybeCookie.isPresent()) {
        SessionDao session = new SessionDaoImpl();
        int userID = session.retrieve(maybeCookie.get().getValue());
        UserDao userDao = new UserDaoImpl();
        return userDao.retrieveById(userID);
      }
    }

    return null;
  }
}