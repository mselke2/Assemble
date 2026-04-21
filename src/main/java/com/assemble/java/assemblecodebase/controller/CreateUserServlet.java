package com.assemble.java.assemblecodebase.controller;


import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;

@WebServlet(name = "CreateUserServlet", value = "/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
  
  public void init() {
    
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    // Get username, password, confirmed password,
    // first name, last name and permission level from client
    
    // Make sure all fields are filled in
    
    // Make sure password and confirmed password match
    
    // Create a UserDAO object to handle User data
    
    // Create a User object and initialize with sanitized parameters from client
    
    // Run addUser() and pass in the User object.
    
    // Return success: true as JSON if successful, and success: false with an error message if not.
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void destroy() {
    
  }
  
}