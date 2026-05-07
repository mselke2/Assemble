package com.assemble.java.assemblecodebase.controller;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "JobServlet", value = "/Job/*")
public class JobServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String path = request.getPathInfo();
    if (path == null || path.isEmpty() || path.equals("/")) {
      getQueried(request, response);
    } else {
      getById(request, response);
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // get data like this:
    int productId = Integer.parseInt(request.getParameter("productId"));

    // Get productID from client as an int

    // Get startTime from the client as a LocalDateTime

    // Validate and sanitize the productID and startTime

    // Create a JobDao object

    // run addJob() and pass in the productId and startTime

    int newJobId = 123;

    response.setContentType("application/json");
    response.getWriter().write("""
        {
          "jobId": %d
        }
        """.formatted(newJobId));

    // set response status to HttpServletResponse.SC_UNPROCESSABLE_CONTENT if any
    // fields are invalid (e.g. jobs are overlapping)
    // response.setStatus(HttpServletResponse.SC_UNPROCESSABLE_CONTENT);    getServletContext().getRequestDispatcher("").forward(request, response);
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // getPathInfo returns the url path after the servlet mapping
    // if the request is to "/Jobs/7"
    // getPathInfo will return "/7"
    // we then have to strip the first slash and parse it to an int
    int jobId = Integer.parseInt(request.getPathInfo().substring(1));

    try (BufferedReader reader = request.getReader()) {
      Gson gson = new Gson();

      var dataMap = new HashMap<String, Object>();
      dataMap = gson.fromJson(reader, dataMap.getClass());

      // get data like so:
      int productId = ((Double)dataMap.get("productId")).intValue();

      // do processing...

      response.setStatus(HttpServletResponse.SC_OK);
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // getPathInfo returns the url path after the servlet mapping
    // if the request is to "/Jobs/7"
    // getPathInfo will return "/7"
    // we then have to strip the first slash and parse it to an int
    int jobId = Integer.parseInt(request.getPathInfo().substring(1));

    // Get the JobId from the client

    // Create JobDao Object

    // Run the deleteJob method in JobDao Object and pass the JobId to it

    // Set response to JSON

    // Set success to true or false depending on status of delete operation

    // Send the response back to the client
  }

  // get list filtered by query parameters
  private void getQueried(HttpServletRequest request, HttpServletResponse response) {
    try {
      response.setContentType("application/json");
      response.getWriter().write("""
          [
            {
              "productName": "car"
            }
          ]""");
    } catch (IOException e) {

    }
  }

  private void getById(HttpServletRequest request, HttpServletResponse response) {
    response.setContentType("application/json");
    try {
      response.getWriter().write("""
          {
            "jobId": 67,
            "productId": 1,
            "startTime": "03:00",
            "projectedEndTime": "05:45",
            "numMembers": 20,
            "lineNum": 1
          }
          """);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}