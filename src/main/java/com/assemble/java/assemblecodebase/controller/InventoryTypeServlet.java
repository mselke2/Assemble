package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.InventoryDao;
import com.assemble.java.assemblecodebase.dao.InventoryDaoImpl;
import com.assemble.java.assemblecodebase.model.InventoryType;
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
}