package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.*;
import com.assemble.java.assemblecodebase.model.Job;
import com.assemble.java.assemblecodebase.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "TimelineServlet", value = "/Timeline")
public class TimelineServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String dateString = request.getParameter("d");
      if (dateString == null || !dateString.matches("^\\d{8}$")) {
        throw new IllegalArgumentException("Invalid date parameter.");
      }
      int year = Integer.parseInt(dateString.substring(0, 4));
      int month = Integer.parseInt(dateString.substring(4, 6));
      int day = Integer.parseInt(dateString.substring(6));

      JobDao jobDao = new JobDaoImpl();
      List<Job> jobs = jobDao.retrieveForDate(LocalDate.of(year, month, day));

      request.setAttribute("jobs", jobs);

      ProductDao productDao = new ProductDaoImpl();
      List<Product> products = productDao.retrieveAll();

      request.setAttribute("productTypes", products);

      getServletContext().getRequestDispatcher("/scheduler.jsp").forward(request, response);
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_UNPROCESSABLE_CONTENT);
      response.getWriter().write(e.getMessage());
    } catch (JobDaoException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.getWriter().write(e.getMessage());
    }
  }
}