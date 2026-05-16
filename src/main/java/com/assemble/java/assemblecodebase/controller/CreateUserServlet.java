package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.UserDao;
import com.assemble.java.assemblecodebase.dao.UserDaoImpl;
import com.assemble.java.assemblecodebase.model.UserPermission;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CreateUserServlet", value = "/CreateUser")
public class CreateUserServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserDao userDao = new UserDaoImpl();
    request.setAttribute("userPermissionTypes", userDao.retrievePermissions());

    getServletContext().getRequestDispatcher("/add-account.jsp").forward(request, response);
  }
}