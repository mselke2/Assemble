package com.assemble.java.assemblecodebase.controller;


import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;

@WebServlet(name = "DeleteJobServlet", value = "/DeleteJobServlet")
public class DeleteJobServlet extends HttpServlet {
  
  public void init() {
    
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    // Get the JobId from the client
    
    // Create JobDao Object
    
    // Run the deleteJob method in JobDao Object and pass the JobId to it
    
    // Set response to JSON
    
    // Set success to true or false depending on status of delete operation
    
    // Send the response back to the client
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void destroy() {
    
  }
  
}