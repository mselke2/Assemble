package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.InventoryDaoException;
import com.assemble.java.assemblecodebase.dao.PersonnelDao;
import com.assemble.java.assemblecodebase.dao.PersonnelDaoImpl;
import com.assemble.java.assemblecodebase.model.Personnel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PersonnelServlet", value = "/Personnel/*")
public class PersonnelServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PersonnelDao personnelDao = new PersonnelDaoImpl();

    List<Personnel> personnelList = new ArrayList<>();

    try {
      personnelList = personnelDao.retrieveAll();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    request.setAttribute("personnel", personnelList);
    getServletContext().getRequestDispatcher("/personnel-manager.jsp").forward(request, response);
  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int id = Integer.parseInt(request.getPathInfo().substring(1));
      PersonnelDao personnelDao = new PersonnelDaoImpl();
      Personnel personnel = personnelDao.retrieve(id);

      Gson gson = new Gson();
      JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);

      int personnelCount = json.get("personnelCount").getAsInt();

      if (personnelCount > 0)
        personnelDao.set(personnel.getDate(), personnelCount);
      else
        personnelDao.delete(personnel.getDate());
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
      PersonnelDao dao = new PersonnelDaoImpl();
      Date date;

      try {
        date = Date.valueOf(request.getParameter("date"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Date");
      }

      if (dao.retrieveCount(date) == 0) {
        dao.set(date, 1);
      }
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
    try{
      int personnelId = Integer.parseInt(request.getPathInfo().substring(1));
      PersonnelDao personnelDao = new PersonnelDaoImpl();
      Personnel personnel = personnelDao.retrieve(personnelId);
      personnelDao.delete(personnel.getDate());
    } catch (NumberFormatException | InventoryDaoException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}