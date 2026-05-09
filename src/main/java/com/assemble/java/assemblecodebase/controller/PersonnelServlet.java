package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.PersonnelDao;
import com.assemble.java.assemblecodebase.dao.PersonnelDaoImpl;
import com.assemble.java.assemblecodebase.model.Personnel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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
}