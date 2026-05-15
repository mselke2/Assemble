package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.JobDao;
import com.assemble.java.assemblecodebase.dao.JobDaoException;
import com.assemble.java.assemblecodebase.dao.JobDaoImpl;
import com.assemble.java.assemblecodebase.model.Job;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    int productId;
    Timestamp startTime;
    Timestamp projectedEndTime;
    int numMembers;
    int lineNum;

    try {
      JobDao dao = new JobDaoImpl();

      try {
        productId =  Integer.parseInt(request.getParameter("productId"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Product Id");
      }

      try {
        startTime = Timestamp.valueOf(request.getParameter("startTime"));
      } catch (RuntimeException e) {
        throw new RuntimeException("Invalid Start Time");
      }

      try {
        projectedEndTime = Timestamp.valueOf(request.getParameter("projectedEndTime"));
      } catch (RuntimeException e) {
        throw new RuntimeException("Invalid End Time");
      }

      try {
        numMembers =  Integer.parseInt(request.getParameter("numMembers"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Personnel Count");
      }

      try {
        lineNum =  Integer.parseInt(request.getParameter("lineNum"));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid Line Number");
      }

      Job job = new Job(productId, lineNum, startTime, numMembers);
      job.setProjectedEndTime(projectedEndTime);
      int newId = dao.addJob(job);

      response.setContentType("application/json");
      response.getWriter().write("{ \"jobId\": %d }".formatted(newId));
    } catch (JobDaoException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

      response.setContentType("text/plain");
      response.getWriter().write(e.getMessage());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_UNPROCESSABLE_CONTENT);

      response.setContentType("text/plain");
      response.getWriter().write(e.getMessage());
    }
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try{
      Job job = new Job();
      job.setId(Integer.parseInt(request.getPathInfo().substring(1)));

      Gson gson = new Gson();
      JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);

      job.setProductId(json.get("productId").getAsInt());

      job.setStartTime(Timestamp.valueOf(json.get("startTime").getAsString()));

      job.setProjectedEndTime(Timestamp.valueOf(json.get("projectedEndTime").getAsString()));

      job.setLineNumber(json.get("lineNum").getAsInt());

      JobDao jobDao = new JobDaoImpl();
      int newId = jobDao.updateJob(job);
      response.setContentType("application/json");
      response.getWriter().write("{ \"newId\": %s }".formatted(newId));
    } catch (NumberFormatException | JobDaoException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try{
      int jobId = Integer.parseInt(request.getPathInfo().substring(1));
      JobDao jobDao = new JobDaoImpl();
      jobDao.deleteJob(jobId);
    } catch (NumberFormatException | JobDaoException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  // get list filtered by query parameters
  private void getQueried(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String dateString = request.getParameter("d");
      if (dateString == null || !dateString.matches("^\\d{8}$")) {
        throw new RuntimeException("Invalid date parameter.");
      }
      int year = Integer.parseInt(dateString.substring(0, 4));
      int month = Integer.parseInt(dateString.substring(4, 6));
      int day = Integer.parseInt(dateString.substring(6));

      JobDao jobDao = new JobDaoImpl();
      List<Job> jobs = jobDao.retrieveForDate(LocalDate.of(year, month, day));

      JsonArray jobsJson = new JsonArray();
      for (Job job : jobs)
        jobsJson.add(buildJobJson(job));

      response.setContentType("application/json");
      response.getWriter().write(jobsJson.toString());
    } catch (JobDaoException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.getWriter().write(e.getMessage());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_UNPROCESSABLE_CONTENT);
      response.getWriter().write(e.getMessage());
    }
  }

  private void getById(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    try {
      int jobId = Integer.parseInt(request.getPathInfo().substring(1));
      JobDao jobDao = new JobDaoImpl();
      Job job = jobDao.retrieve(jobId);

      response.getWriter().write(buildJobJson(job).toString());
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  private JsonObject buildJobJson(Job job) {
    JsonObject jobJson = new JsonObject();
    jobJson.addProperty("jobId", job.getId());

    jobJson.addProperty("productId", job.getProductId());

    jobJson.addProperty("productName", job.getProductName());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    String startTimeString = formatter.format(job.getStartTime().toLocalDateTime());
    jobJson.addProperty("startTime", startTimeString);

    String endTimeString = formatter.format(job.getProjectedEndTime().toLocalDateTime());
    jobJson.addProperty("projectedEndTime", endTimeString);

    jobJson.addProperty("numMembers", job.getPersonnelCount());

    jobJson.addProperty("lineNum", job.getLineNumber());

    return jobJson;
  }
}