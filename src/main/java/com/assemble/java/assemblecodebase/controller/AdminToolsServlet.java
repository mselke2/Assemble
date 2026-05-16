package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminToolsServlet", value = "/AdminTools")
public class AdminToolsServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User requester = Authenticate.RetrieveRequestingUser(request);
    if (requester == null || !requester.clearanceAtLeast("admin")) {
      response.sendRedirect("Calendar");
      return;
    }

    getServletContext().getRequestDispatcher("/admin-tools.jsp").forward(request, response);
  }
}