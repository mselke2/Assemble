package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.EquipmentDao;
import com.assemble.java.assemblecodebase.dao.EquipmentDaoException;
import com.assemble.java.assemblecodebase.dao.EquipmentDaoImpl;
import com.assemble.java.assemblecodebase.model.Equipment;
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

@WebServlet(name = "EquipmentServlet", value = "/Equipment/*")
public class EquipmentServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    EquipmentDao equipmentDao = new EquipmentDaoImpl();

    List<Equipment> equipment = new ArrayList<>();
    List<EquipmentType> types = new ArrayList<>();

    try {
      equipment = equipmentDao.retrieveAll();
      types = equipmentDao.retrieveTypes();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    request.setAttribute("equipmentList", equipment);
    request.setAttribute("equipmentTypes", types);
    getServletContext().getRequestDispatcher("/tool-manager.jsp").forward(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int typeId;
      try {
        typeId = Integer.parseInt(request.getParameter("equipmentTypeId"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Equipment Type Id");
      }

      EquipmentDao dao = new EquipmentDaoImpl();
      Equipment equipment = new Equipment();
      equipment.setTypeId(typeId);
      equipment.setStatus(1);
      dao.addEquipment(equipment);
    } catch (EquipmentDaoException e) {
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
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int id = Integer.parseInt(request.getPathInfo().substring(1));
      EquipmentDao equipmentDao = new EquipmentDaoImpl();
      Equipment equipment = equipmentDao.retrieveById(id);

      Gson gson = new Gson();
      JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);

      int status = json.get("status").getAsInt();
      equipment.setStatus(status);

      equipmentDao.updateEquipment(equipment);
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write(e.getMessage());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write(e.getMessage());
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int equipmentId = Integer.parseInt(request.getPathInfo().substring(1));
      EquipmentDao equipmentDao = new EquipmentDaoImpl();
      equipmentDao.deleteEquipmentById(equipmentId);
    } catch (NumberFormatException | EquipmentDaoException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}