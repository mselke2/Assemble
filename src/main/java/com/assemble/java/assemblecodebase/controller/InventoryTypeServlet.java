package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.InventoryDao;
import com.assemble.java.assemblecodebase.dao.InventoryDaoImpl;
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

@WebServlet(name = "InventoryTypeServlet", value = "/InventoryType/*")
public class InventoryTypeServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    InventoryDao inventoryDao = new InventoryDaoImpl();

    List<InventoryType> types = new ArrayList<>();

    try {
      types = inventoryDao.retrieveTypes();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    request.setAttribute("inventoryTypes", types);
    getServletContext().getRequestDispatcher("/inventory-type-manager.jsp").forward(request, response);
  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int id = Integer.parseInt(request.getPathInfo().substring(1));

      Gson gson = new Gson();
      JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);

      String description = json.get("description").getAsString();
      if (description.length() > 50)
        throw new IllegalArgumentException("Invalid Description");
      
      InventoryType inventoryType = new InventoryType(id, description);

      InventoryDao inventoryDao = new InventoryDaoImpl();
      inventoryDao.updateInventoryType(inventoryType);
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write(e.getMessage());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write(e.getMessage());
    }
  }
}