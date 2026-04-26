package com.assemble.java.assemblecodebase.controller;


import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;

@WebServlet(name = "LoginServlet", value = "/Login")
public class LoginServlet extends HttpServlet {
  
  public void init() {
    
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    // Get username from client
    
    // Get password from client
    
    // Validate and sanitize username and password.
    
    // Create UserDAO object
    
    // Run retrieve() and pass in username and password
    
    // IF retrieve() returns a positive integer (a userID),
      // Create a SessionDao object
      // run createSession() and pass in the userId returned from retrieve()
    
      // IF createSession returns a String
        // Create a cookie with the name "loginToken" and the value of the String returned from createSession()
        // Set the max age of the cookie to 24 hours
        // Add the cookie to the response
        // Return success: true as JSON
    
      // ELSE return success: false with error message from exception.
      // ENDIF
    
    // ELSE return success: false as JSON with an error message back to the client.
    // ENDIF
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void destroy() {
    
  }
  
}