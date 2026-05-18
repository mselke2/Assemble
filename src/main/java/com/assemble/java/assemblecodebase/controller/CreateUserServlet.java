package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.UserDao;
import com.assemble.java.assemblecodebase.dao.UserDaoImpl;
import com.assemble.java.assemblecodebase.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CreateUserServlet", value = "/CreateUser")
public class CreateUserServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User requester = Authenticate.RetrieveRequestingUser(request);
    if (requester == null || !requester.clearanceAtLeast("admin")) {
      response.sendRedirect("Calendar");
      return;
    }

    UserDao userDao = new UserDaoImpl();
    request.setAttribute("userPermissionTypes", userDao.retrievePermissions());

    getServletContext().getRequestDispatcher("/add-account.jsp").forward(request, response);
  }
}