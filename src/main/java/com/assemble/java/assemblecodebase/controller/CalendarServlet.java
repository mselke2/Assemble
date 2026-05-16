package com.assemble.java.assemblecodebase.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CalendarServlet", value = {"/Calendar", "/"})
public class CalendarServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/calendar.jsp").forward(request, response);
  }

}