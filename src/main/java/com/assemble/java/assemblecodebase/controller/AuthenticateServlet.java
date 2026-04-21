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
    
    // Get loginToken cookie from client
    
    // Check DB to see if login token matches a session in the database
    
    // If a match is found, return authenticated: true as JSON
    
    // If a match is not found, return authenticated: false as JSON
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void destroy() {
    
  }
  
}