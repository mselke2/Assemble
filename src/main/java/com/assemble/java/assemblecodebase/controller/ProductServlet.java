package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.ProductDao;
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
}