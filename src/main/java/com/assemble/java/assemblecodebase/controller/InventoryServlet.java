package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.model.Inventory;
import com.assemble.java.assemblecodebase.model.InventoryType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "InventoryServlet", value = "/Inventory/*")
public class InventoryServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // todo: this is sample data, pull the real data from the db
    request.setAttribute("inventory", List.of(
        new Inventory(1, 2, 3),
        new Inventory(2, 5, 7),
        new Inventory(3, 74, 3)
    ));
    request.setAttribute("inventoryTypes", List.of(
        new InventoryType(0, "Rivet"),
        new InventoryType(1, "Washer"),
        new InventoryType(2, "Screw")
    ));
    getServletContext().getRequestDispatcher("/inventory-manager.jsp").forward(request, response);

  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
//    getServletContext().getRequestDispatcher("").forward(request, response);

  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }

}