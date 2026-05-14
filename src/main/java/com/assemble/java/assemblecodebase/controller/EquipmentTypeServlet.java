package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.EquipmentDao;
import com.assemble.java.assemblecodebase.dao.EquipmentDaoImpl;
import com.assemble.java.assemblecodebase.model.EquipmentType;
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
}