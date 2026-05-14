package com.assemble.java.assemblecodebase.controller;


import java.io.*;

import com.assemble.java.assemblecodebase.dao.SessionDaoImpl;
import com.assemble.java.assemblecodebase.model.Session;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;

@WebServlet(name = "LogoutServlet", value = "/Logout")
public class LogoutServlet extends HttpServlet {
  
  public void init() {
    
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    getServletContext().getRequestDispatcher("").forward(request, response);
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    // Get sessionId from client cookies
    Cookie[] cookies = request.getCookies();
    Cookie loginTokenCookie = null;
    Cookie permissionCookie = null;
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        String name = cookie.getName();


        if (name.equals("loginToken")) {
          loginTokenCookie = cookie;
        }
        if (name.equals("permissionLevel")){
          permissionCookie = cookie;
        }
      }

      if (permissionCookie != null) {
        permissionCookie.setMaxAge(0);
        response.addCookie(permissionCookie);
      }
      if (loginTokenCookie != null) {
        // Use SessionDao to delete the session from the database
        SessionDaoImpl session = new SessionDaoImpl();
        int userID = session.retrieve(loginTokenCookie.getValue());
        session.removeSession(userID);
        // Delete the sessionId cookie from the client by setting its max age to 0 and adding it to the response
        loginTokenCookie.setMaxAge(0);
        response.addCookie(loginTokenCookie);

        // Return success: true as JSON to the client if successful, otherwise return success: false with an error message as JSON.
        request.setAttribute("message", "you have been logged out");
        request.setAttribute("color", "green");
      } else {
        //there is no loginTokenCookie
      }
    } else {
      // No cookies were sent with this request

    }
    getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    
  }
  
  public void destroy() {
    
  }
  
}