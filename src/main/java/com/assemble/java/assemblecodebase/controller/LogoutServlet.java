package com.assemble.java.assemblecodebase.controller;


import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;

@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
  
  public void init() {
    
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    // Get sessionId from client cookies
    
    // Use SessionDao to delete the session from the database
    
    // Delete the sessionId cookie from the client by setting its max age to 0 and adding it to the response
    
    // Return success: true as JSON to the client if successful, otherwise return success: false with an error message as JSON.
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void destroy() {
    
  }
  
}