package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.EquipmentDao;
import com.assemble.java.assemblecodebase.dao.EquipmentDaoImpl;
import com.assemble.java.assemblecodebase.model.EquipmentType;
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

@WebServlet(name = "EquipmentTypeServlet", value = "/EquipmentType/*")
public class EquipmentTypeServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    EquipmentDao equipmentDao = new EquipmentDaoImpl();

    List<EquipmentType> types = new ArrayList<>();

    try {
      types = equipmentDao.retrieveTypes();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    request.setAttribute("equipmentTypes", types);
    getServletContext().getRequestDispatcher("/equipment-type-manager.jsp").forward(request, response);
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

      EquipmentType equipmentType = new EquipmentType(id, description);

      EquipmentDao equipmentDao = new EquipmentDaoImpl();
      equipmentDao.updateEquipmentType(equipmentType);
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write(e.getMessage());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write(e.getMessage());
    }
  }
}