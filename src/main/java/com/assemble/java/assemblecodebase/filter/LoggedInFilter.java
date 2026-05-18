package com.assemble.java.assemblecodebase.filter;

import com.assemble.java.assemblecodebase.dao.SessionDao;
import com.assemble.java.assemblecodebase.dao.SessionDaoImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "LoggedInFilter", value = "/*", initParams =
  {@WebInitParam(name = "pathsToIgnore", value = "/assets/,/login.jsp,/Login,")})
public class LoggedInFilter implements Filter {
  
  FilterConfig filterConfig;
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
    this.filterConfig = filterConfig;
  }
  
  @Override
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {
    
    if(ignorePath(request) || isLoggedIn(request)) {
      // Allow access by moving up the filter chain
      chain.doFilter(request, response);
    } else {
      // Restrict access
      request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    
  }
  
  private boolean isLoggedIn(ServletRequest request) {
    
    Cookie[] cookies = ((HttpServletRequest) request).getCookies();
    
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("loginToken")) {
          
          String loginToken = cookie.getValue();
          SessionDao sessionDao = new SessionDaoImpl();
          
          if(sessionDao.retrieve(loginToken) > 0) {
            return true;
          }
        }
      }
    }
    
    return false;
  }
  
  private boolean ignorePath(ServletRequest servletRequest) {
    String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
    System.out.println("******** Requested URI *********" + requestURI);
    
    String[] ignoredPaths = filterConfig.getInitParameter("pathsToIgnore").split(",");
    
    for (String path : ignoredPaths) {
      if(requestURI.contains(path)) {
        return true;
      }
    }
    
    return false;
  }
}
