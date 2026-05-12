package com.assemble.java.assemblecodebase.controller;


import java.io.*;

import com.assemble.java.assemblecodebase.dao.SessionDaoImpl;
import com.assemble.java.assemblecodebase.dao.UserDao;
import com.assemble.java.assemblecodebase.dao.UserDaoException;
import com.assemble.java.assemblecodebase.dao.UserDaoImpl;
import com.assemble.java.assemblecodebase.model.User;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;
import org.apache.commons.text.StringEscapeUtils;

@WebServlet(name = "LoginServlet", value = "/Login")
public class LoginServlet extends HttpServlet {
  
  public void init() {
    
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Get username from client
      String username = StringEscapeUtils.escapeHtml4(request.getParameter("username"));
    // Get password from client
      String password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));
    // Validate and sanitize username and password.
    
    // Create UserDAO object
    UserDaoImpl userDao = new UserDaoImpl();
    // Run retrieve() and pass in username and password
    int userID = -1;
    try {
      userID = userDao.retrieveWithLogin(username, password);
    } catch (UserDaoException e) {

      // return failure
      request.setAttribute("message", e.getMessage());
      request.setAttribute("color", "red");
      
      getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }


    // IF retrieve() returns a positive integer (a userID),
    if (userID > 0) {
      // Create a SessionDao object
      SessionDaoImpl session = new SessionDaoImpl();
      // run createSession() and pass in the userId returned from retrieve()

      // IF createSession returns a String
      try {
        String loginTokenID = session.createSession(userID);
        // Create a cookie with the name "loginToken" and the value of the String returned from createSession()
        Cookie loginTokenCookie = new Cookie("loginToken", loginTokenID);
        // Set the max age of the cookie to 24 hours
        loginTokenCookie.setMaxAge(60 * 60 * 24);
        // Add the cookie to the response
        response.addCookie(loginTokenCookie);
        // Return success
        request.setAttribute("message", "success");
        request.setAttribute("color", "green");
        
        getServletContext().getRequestDispatcher("/calendar.jsp").forward(request, response);
        
      } catch (Exception e) {
        // ELSE return failure
        request.setAttribute("message", e.getMessage());
        request.setAttribute("color", "red");
        
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);

      } // ENDIF

    }// ENDIF
    
    
    
  }
  
  public void destroy() {
    
  }
  
}