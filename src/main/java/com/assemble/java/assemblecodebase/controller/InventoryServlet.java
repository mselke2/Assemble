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

      Gson gson = new Gson();
      JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);

      int typeId = json.get("typeId").getAsInt();
      int count = json.get("count").getAsInt();
      Inventory inventory = new Inventory(typeId, count);
      inventory.setId(id);

      InventoryDao inventoryDao = new InventoryDaoImpl();
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
    try {
      int typeId;
      try {
        typeId = Integer.parseInt(request.getParameter("inventoryTypeId"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Inventory Type Id");
      }

      InventoryDao dao = new InventoryDaoImpl();
      Inventory inventory = new Inventory(typeId, 1);
      dao.addInventory(inventory);
    } catch (InventoryDaoException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

      response.setContentType("text/plain");
      response.getWriter().write(e.getMessage());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_UNPROCESSABLE_CONTENT);

      response.setContentType("text/plain");
      response.getWriter().write(e.getMessage());
    }

    doGet(request, response);
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int inventoryId = Integer.parseInt(request.getPathInfo().substring(1));
      InventoryDao inventoryDao = new InventoryDaoImpl();
      inventoryDao.deleteInventoryById(inventoryId);
    } catch (NumberFormatException | InventoryDaoException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}