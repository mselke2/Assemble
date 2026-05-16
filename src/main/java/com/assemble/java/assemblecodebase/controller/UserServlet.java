package com.assemble.java.assemblecodebase.controller;


import com.assemble.java.assemblecodebase.dao.UserDao;
import com.assemble.java.assemblecodebase.dao.UserDaoException;
import com.assemble.java.assemblecodebase.dao.UserDaoImpl;
import com.assemble.java.assemblecodebase.model.User;
import com.assemble.java.assemblecodebase.model.UserPermission;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/User/*")
public class UserServlet extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO: Ensure user has the authority to create users.

    if(request.getParameter("userToEdit") != null){
      String userToEdit = request.getParameter("userToEdit");
      
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      String confirmPassword = request.getParameter("password-repeat");
      String firstName = request.getParameter("fName");
      String lastName = request.getParameter("lName");
      int permissionId = Integer.parseInt(request.getParameter("type"));
      
      if(username == null || firstName == null || lastName == null || permissionId == 0){
        request.setAttribute("message", "Username, First Name, Last Name and Permission Level are required.");
        request.setAttribute("color", "red");
        doGet(request,response);
//        getServletContext().getRequestDispatcher("/user-manager.jsp").forward(request, response);
      } else if(!password.isEmpty() && !password.equals(confirmPassword)){
        
        request.setAttribute("message", "Passwords do not match.");
        request.setAttribute("color", "red");
        doGet(request,response);
//        getServletContext().getRequestDispatcher("/user-manager.jsp").forward(request, response);
      } else {
        
        UserDao userDao = new UserDaoImpl();
        
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPermissionId(permissionId);
        
        if(!password.isEmpty()) {
          user.setPasswordHash(password);
        }
        
        userDao.updateUser(userToEdit, user);
        request.setAttribute("message", "User successfully updated.");
        request.setAttribute("color", "green");
        doGet(request,response);
      }
    } else {
      String username;
      int permissionId;
      String fName;
      String lName;
      String password;
      
      response.setContentType("text/plain");
      
      try {
        UserDao dao = new UserDaoImpl();
        
        username = request.getParameter("username");
        if (username == null
          || !(username = username.toLowerCase()).matches("^[a-z0-9]{3,50}$"))
          throw new RuntimeException("Invalid Username");
        
        var permissions = dao.retrievePermissions();
        
        try {
          permissionId = Integer.parseInt(request.getParameter("type"));
        } catch (NumberFormatException e) {
          throw new RuntimeException("Invalid Permission Id");
        }
        if (permissions.stream().noneMatch(p -> permissionId == p.getId()))
          throw new RuntimeException("Invalid Permission Id");
        
        fName = request.getParameter("fName");
        if (fName == null
          || !(fName = fName.toLowerCase()).matches("^[a-zA-Z]{1,50}$"))
          throw new RuntimeException("Invalid First Name");
        
        lName = request.getParameter("lName");
        if (lName == null
          || !(lName = lName.toLowerCase()).matches("^[a-zA-Z]{1,50}$"))
          throw new RuntimeException("Invalid Last Name");
        
        password = request.getParameter("password");
        if (password == null
          || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$"))
          throw new RuntimeException("Invalid Password");
        
        if (!password.equals(request.getParameter("password-repeat")))
          throw new RuntimeException("Passwords Do Not Match");
        
        User user = new User(username, permissionId, fName, lName, password);
        dao.addUser(user);
        
        
      } catch (UserDaoException e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(e.getMessage());
        return;
      } catch (RuntimeException e) {
        response.setStatus(HttpServletResponse.SC_UNPROCESSABLE_CONTENT);
        response.getWriter().write(e.getMessage());
        return;
      }
      
      response.getWriter().write("User added successfully.");
    }
  }

  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO: Ensure user has the authority to delete users

    try{
      int userId = Integer.parseInt(request.getPathInfo().substring(1));
      UserDao userDao = new UserDaoImpl();
      userDao.deleteUser(userId);
    } catch (NumberFormatException | UserDaoException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("loggedInUser", Authenticate.RetrieveRequestingUser(request));

    if (request.getParameter("userToDisplay") == null) {
      User[] users;
      
      try {
        UserDaoImpl dao = new UserDaoImpl();
        users = dao.retrieveAll();
        List<UserPermission> userPermissionTypes = dao.retrievePermissions();
        
        request.setAttribute("users", users);
        request.setAttribute("userPermissionTypes", userPermissionTypes);
        
        // Send the response.
        getServletContext().getRequestDispatcher("/user-manager.jsp").forward(request, response);
      } catch (UserDaoException e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(e.getMessage());
      }
    } else {
      
      try  {
        UserDaoImpl dao = new UserDaoImpl();
        
        User user = dao.retrieveByUsername((String) request.getParameter("userToDisplay"));
        
        response.setContentType("application/json");
        String json = String.format("{\"id\":%d,\"username\":\"%s\",\"permissionId\":%d,\"fName\":\"%s\",\"lName\":\"%s\"}",
            user.getId(), user.getUsername(), user.getPermissionId(), user.getFirstName(), user.getLastName());
        
        // Send the response
        PrintWriter out = response.getWriter();
        out.write(json);
        out.flush();
        
      }  catch (UserDaoException e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(e.getMessage());
      }
    }
    
    
  }
}