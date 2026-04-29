package com.assemble.java.assemblecodebase.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UserServlet", value = "/User/*")
public class UserServlet extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Get username, password, confirmed password,
    // first name, last name and permission level from client
    // !!! GUI design doc doesn't include first/last names,
    // and they are not included in the page at this time --Adam

    // Make sure all fields are filled in

    // Make sure password and confirmed password match

    // Create a UserDAO object to handle User data

    // Create a User object and initialize with sanitized parameters from client

    // Run addUser() and pass in the User object.

    // set response status to HttpServletResponse.SC_UNPROCESSABLE_CONTENT if any
    // fields are invalid (e.g. passwords do not match)
    // response.setStatus(HttpServletResponse.SC_UNPROCESSABLE_CONTENT);
    // getServletContext().getRequestDispatcher("<<<page that user should be redirected to after creating account>>>").forward(request, response);
  }

  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Get userId from the client

    // Validate and sanitize userId

    // Create a UserDAO object

    // Run deleteUser() and pass in the userId

    // Return success: true if successful and success: false if not.

  }
}