package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.*;
import com.assemble.java.assemblecodebase.model.EquipmentType;
import com.assemble.java.assemblecodebase.model.InventoryType;
import com.assemble.java.assemblecodebase.model.Job;
import com.assemble.java.assemblecodebase.model.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/Product/*")
public class ProductServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String json = request.getParameter("json");
    if (json != null && json.equals("true")) {
      doGetJson(request, response);
    } else {
      doGetHtml(request, response);
    }
  }

  private void doGetJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    try {
      int productId = Integer.parseInt(request.getPathInfo().substring(1));
      ProductDao productDao = new ProductDaoImpl();
      Product product = productDao.retrieve(productId);

      JsonObject productJson = new JsonObject();
      productJson.addProperty("id", product.getId());

      productJson.addProperty("description", product.getDescription());

      productJson.addProperty("targetDuration", product.getMinutesDuration());

      productJson.addProperty("targetPersonnel", product.getTargetPersonnelCount());

      response.getWriter().write(productJson.toString());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  private void doGetHtml(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProductDao productDao = new ProductDaoImpl();
    InventoryDao inventoryDao = new InventoryDaoImpl();
    EquipmentDao equipmentDao = new EquipmentDaoImpl();

    List<Product> products = new ArrayList<>();
    List<InventoryType> inventoryTypes = new ArrayList<>();
    List<EquipmentType> equipmentTypes = new ArrayList<>();

    try {
      products = productDao.retrieveAll();
      inventoryTypes = inventoryDao.retrieveTypes();
      equipmentTypes = equipmentDao.retrieveTypes();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    request.setAttribute("products", products);
    request.setAttribute("inventoryTypes", inventoryTypes);
    request.setAttribute("equipmentTypes", equipmentTypes);
    getServletContext().getRequestDispatcher("/product-manager.jsp").forward(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String description = request.getParameter("description");
      if (description == null || description.length() > 50)
        throw new RuntimeException("Invalid Description");

      int duration;
      try {
        duration = Integer.parseInt(request.getParameter("duration"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Duration");
      }

      int personnel;
      try {
        personnel = Integer.parseInt(request.getParameter("personnelCount"));
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

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int id = Integer.parseInt(request.getPathInfo().substring(1));

      Gson gson = new Gson();
      JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);

      String description = json.get("description").getAsString();
      if (description.length() > 50)
        throw new RuntimeException("Invalid Description");

      int duration = json.get("duration").getAsInt();
      int personnelCount = json.get("personnelCount").getAsInt();

      Product product = new Product(id, description, duration, personnelCount);

      String inventoryIdsStr = json.get("inventoryIds").getAsString();
      product.setRequiredInventoryIds(parseArrayString(inventoryIdsStr));

      String inventoryCountsStr = json.get("inventoryCounts").getAsString();
      product.setRequiredInventoryCounts(parseArrayString(inventoryCountsStr));

      String equipmentIdsStr = json.get("equipmentIds").getAsString();
      product.setRequiredEquipmentIds(parseArrayString(equipmentIdsStr));

      String equipmentCountsStr = json.get("equipmentCounts").getAsString();
      product.setRequiredEquipmentCounts(parseArrayString(equipmentCountsStr));

      ProductDao productDao = new ProductDaoImpl();
      productDao.updateProduct(product);
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write(e.getMessage());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write(e.getMessage());
    }
  }

  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int productId = Integer.parseInt(request.getPathInfo().substring(1));
      ProductDao productDao = new ProductDaoImpl();
      productDao.deleteProductById(productId);
    } catch (NumberFormatException | ProductDaoException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  private List<Integer> parseArrayString(String arrayString) {
    arrayString = arrayString.substring(1, arrayString.length() - 1);
    if (arrayString.isEmpty()) return new ArrayList<>();

    String[] arrayStringSplit = arrayString.split(",");
    return Arrays.stream(arrayStringSplit).map(String::strip).map(Integer::parseInt).toList();
  }
}