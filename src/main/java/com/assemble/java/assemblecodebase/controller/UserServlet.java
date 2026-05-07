package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.UserDao;
import com.assemble.java.assemblecodebase.dao.UserDaoException;
import com.assemble.java.assemblecodebase.dao.UserDaoImpl;
import com.assemble.java.assemblecodebase.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UserServlet", value = "/User/*")
public class UserServlet extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO: Ensure user has the authority to create users.

    String username;
    int permissionId;
    String fName;
    String lName;
    String password;

    response.setContentType("text/plain");

    try {
      UserDao dao = new UserDaoImpl();

      username = request.getParameter("username");
      if (username == null
          || !(username = username.toLowerCase()).matches("^[a-z0-9]{3,50}$"))
        throw new RuntimeException("Invalid Username");

      var permissions = dao.retrievePermissions();

      try {
        permissionId =  Integer.parseInt(request.getParameter("type"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Permission Id");
      }
      if (permissions.stream().noneMatch(p -> permissionId == p.getId()))
        throw new RuntimeException("Invalid Permission Id");

      fName = request.getParameter("fName");
      if (fName == null
          || !(fName = fName.toLowerCase()).matches("^[a-zA-Z]{1,50}$"))
        throw new RuntimeException("Invalid First Name");

      lName = request.getParameter("lName");
      if (lName == null
          || !(lName = lName.toLowerCase()).matches("^[a-zA-Z]{1,50}$"))
        throw new RuntimeException("Invalid Last Name");

      password = request.getParameter("password");
      if (password == null
          || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$"))
        throw new RuntimeException("Invalid Password");

      if (!password.equals(request.getParameter("password-repeat")))
        throw new RuntimeException("Passwords Do Not Match");

      User user = new User(username, permissionId, fName, lName, password);
      dao.addUser(user);
    } catch (UserDaoException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.getWriter().write(e.getMessage());
      return;
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_UNPROCESSABLE_CONTENT);
      response.getWriter().write(e.getMessage());
      return;
    }

    response.getWriter().write("User added successfully.");
  }

  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO: Ensure user has the authority to delete users

    try{
      int userId = Integer.parseInt(request.getPathInfo().substring(1));
      UserDao userDao = new UserDaoImpl();
      userDao.deleteUser(userId);
    } catch (NumberFormatException | UserDaoException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}