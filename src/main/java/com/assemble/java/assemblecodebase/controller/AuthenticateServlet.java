package com.assemble.java.assemblecodebase.controller;


import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;

@WebServlet(name = "AuthenticateServlet", value = "/AuthenticateServlet")
public class AuthenticateServlet extends HttpServlet {
  
  public void init() {
    
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    // Get sessionID cookie from client
    
    // Create SessionDao object
    
    // Run retreive()
    
    // If a positive integer is returned, return authenticated: true as JSON
    
    // If not, return authenticated: false as JSON
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void destroy() {
    
  }
  
}