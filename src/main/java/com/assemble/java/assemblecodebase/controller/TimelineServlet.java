package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.model.Job;
import com.assemble.java.assemblecodebase.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "TimelineServlet", value = "/Timeline")
public class TimelineServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    if (Objects.equals(request.getParameter("format"), "json")) {
      response.setContentType("application/json");
      // todo: this is sample data, pull the actual data from the db
      response.getWriter().write("""
          [
            {
              "productName": "car"
            }
          ]""");
      response.getWriter().flush();
    } else {
      Timestamp startTime = new Timestamp(2026, 5, 3, 3, 0, 0, 0);
      Timestamp projectedEndTime = new Timestamp(2026, 5, 3, 5, 45, 0, 0);
      Job job1 = new Job(67, 1, startTime, 20);
      job1.setProjectedEndTime(projectedEndTime);
      List<Job> jobList = List.of(job1);

      // todo: this is sample data, pull the real data from the db
      request.setAttribute("jobs", jobList);
      request.setAttribute("productTypes", List.of(
          new Product(1, "Car", LocalTime.of(5, 0), 5),
          new Product(2, "Desk", LocalTime.of(1, 20), 2)
      ));

      getServletContext().getRequestDispatcher("/scheduler.jsp").forward(request, response);
    }
  }
}