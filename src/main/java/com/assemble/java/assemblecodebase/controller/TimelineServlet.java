package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.ProductDaoImpl;
import com.assemble.java.assemblecodebase.model.Job;
import com.assemble.java.assemblecodebase.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "TimelineServlet", value = "/Timeline")
public class TimelineServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Timestamp startTime = new Timestamp(2026, 5, 3, 3, 0, 0, 0);
	Timestamp projectedEndTime = new Timestamp(2026, 5, 3, 5, 45, 0, 0);
	Job job1 = new Job(1, 1, startTime);
	ProductDaoImpl productDao = new ProductDaoImpl();
	job1.setProjectedEndTime(startTime, productDao.retrieve(job1.getProductId()).getMinutesDuration());
	job1.setPersonnelCount(productDao.retrieve(job1.getProductId()).getTargetPersonnelCount());
	List<Job> jobList = List.of(job1);
	
	// todo: this is sample data, pull the real data from the db
	request.setAttribute("jobs", jobList);
	request.setAttribute("productTypes", List.of(
	 new Product(1, "Car", 60*5, 4),
	 new Product(2, "Desk", 80, 5)
	));
	
	getServletContext().getRequestDispatcher("/scheduler.jsp").forward(request, response);
  }
}