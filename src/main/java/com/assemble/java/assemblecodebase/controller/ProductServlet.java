package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.ProductDao;
import com.assemble.java.assemblecodebase.dao.ProductDaoException;
import com.assemble.java.assemblecodebase.dao.ProductDaoImpl;
import com.assemble.java.assemblecodebase.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/Product/*")
public class ProductServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProductDao productDao = new ProductDaoImpl();

    List<Product> products = new ArrayList<>();

    try {
      products = productDao.retrieveAll();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    request.setAttribute("products", products);
    getServletContext().getRequestDispatcher("/product-manager.jsp").forward(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String description = request.getParameter("description");
      if (description == null
          || description.length() > 50)
        throw new RuntimeException("Invalid Description");

      int duration;
      try {
        duration =  Integer.parseInt(request.getParameter("duration"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Duration");
      }

      int personnel;
      try {
        personnel =  Integer.parseInt(request.getParameter("personnelCount"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Personnel Count");
      }

      Product product = new Product();
      product.setDescription(description);
      product.setMinutesDuration(duration);
      product.setTargetPersonnelCount(personnel);

      ProductDao dao = new ProductDaoImpl();
      dao.addProduct(product);
    } catch (ProductDaoException e) {
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
}