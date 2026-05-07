package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.InventoryDao;
import com.assemble.java.assemblecodebase.dao.InventoryDaoException;
import com.assemble.java.assemblecodebase.dao.InventoryDaoImpl;
import com.assemble.java.assemblecodebase.model.Inventory;
import com.assemble.java.assemblecodebase.model.InventoryType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "InventoryServlet", value = "/Inventory/*")
public class InventoryServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    InventoryDao inventoryDao = new InventoryDaoImpl();

    List<Inventory> inventory = new ArrayList<>();
    List<InventoryType> types = new ArrayList<>();

    try {
      inventory = inventoryDao.retrieveAll();
      types = inventoryDao.retrieveTypes();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    request.setAttribute("inventory", inventory);
    request.setAttribute("inventoryTypes", types);
    getServletContext().getRequestDispatcher("/inventory-manager.jsp").forward(request, response);
  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int id = Integer.parseInt(request.getPathInfo().substring(1));
      InventoryDao inventoryDao = new InventoryDaoImpl();
      Inventory inventory = inventoryDao.retrieveById(id);

      Gson gson = new Gson();
      JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);

      String actionString = json.get("action").getAsString();
      if (actionString.equals("add"))
        inventory.setCount(inventory.getCount() + 1);
      else if (actionString.equals("remove"))
        inventory.setCount(inventory.getCount() - 1);
      else {
        throw new IllegalArgumentException("Invalid action: " + actionString);
      }

      if (inventory.getCount() > 0)
        inventoryDao.updateInventory(inventory);
      else
        inventoryDao.deleteInventoryById(id);
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write(e.getMessage());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write(e.getMessage());
    }
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
    try{
      int inventoryId = Integer.parseInt(request.getPathInfo().substring(1));
      InventoryDao inventoryDao = new InventoryDaoImpl();
      inventoryDao.deleteInventoryById(inventoryId);
    } catch (NumberFormatException | InventoryDaoException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}