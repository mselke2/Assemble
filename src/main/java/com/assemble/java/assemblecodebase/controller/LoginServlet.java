package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/Login")
public class LoginServlet extends HttpServlet {

  public void init() {

  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (Authenticate.RetrieveRequestingUser(request) == null)
      getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    else
      response.sendRedirect("Calendar");

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Get username from client
    String username = StringEscapeUtils.escapeHtml4(request.getParameter("username"));
    // Get password from client
    String password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));
    // Validate and sanitize username and password.

    // Create UserDAO object
    UserDao userDao = new UserDaoImpl();
    // Run retrieve() and pass in username and password
    int userID = -1;
    int userPermissionID = -1;
    try {
      userID = userDao.retrieveWithLogin(username, password);
      userPermissionID = userDao.retrieveByUsername(username).getPermissionId();
    } catch (UserDaoException e) {

      // return failure
      request.setAttribute("message", e.getMessage());
      request.setAttribute("color", "red");

      getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }


    // IF retrieve() returns a positive integer (a userID),
    if (userID > 0) {
      // Create a SessionDao object
      SessionDao session = new SessionDaoImpl();
      // run createSession() and pass in the userId returned from retrieve()

      // IF createSession returns a String
      try {
        String sessionID = session.createSession(userID);
        // Create a cookie with the name "loginToken" and the value of the String returned from createSession()
        Cookie loginTokenCookie = new Cookie("loginToken", sessionID);
        // Set the max age of the cookie to 24 hours
        loginTokenCookie.setMaxAge(60 * 60 * 24);
        // Add the cookie to the response
        response.addCookie(loginTokenCookie);

        //create permission cookie
        Cookie permissionCookie = new Cookie("permissionLevel", "" + userPermissionID);
        // Set the max age of the cookie to 24 hours
        permissionCookie.setMaxAge(60 * 60 * 24);
        // Add the cookie to the response
        response.addCookie(permissionCookie);

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