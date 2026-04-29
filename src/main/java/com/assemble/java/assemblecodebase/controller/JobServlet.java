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
    response.setContentType("application/json");
    // TODO: this is sample JSON data, replace with info from DB
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
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // get data like this:
    int productId = Integer.parseInt(request.getParameter("productId"));

    // Get productID from client as an int

    // Get startTime from the client as a LocalDateTime

    // Validate and sanitize the productID and startTime

    // Create a JobDao object

    // run addJob() and pass in the productId and startTime


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

      var dataMap = new HashMap<String, String>();
      dataMap = gson.fromJson(reader, dataMap.getClass());

      // get data like so:
      int productId = Integer.parseInt(dataMap.get("productId"));

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
}