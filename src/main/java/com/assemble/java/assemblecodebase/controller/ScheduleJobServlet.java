package com.assemble.java.assemblecodebase.controller;


import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;

@WebServlet(name = "ScheduleJobServlet", value = "/ScheduleJobServlet")
public class ScheduleJobServlet extends HttpServlet {
  
  public void init() {
    
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    // Get productID from client as an int
    
    // Get startTime from the client as a LocalDateTime
    
    // Validate and sanitize the productID and startTime
    
    // Create a JobDao object
    
    // run addJob() and pass in the productId and startTime
    
    // return success: true ass JSON if successful, and success: false with an error message if not.
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void destroy() {
    
  }
  
}