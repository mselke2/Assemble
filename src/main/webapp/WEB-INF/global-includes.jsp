<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.assemble.java.assemblecodebase.controller.Authenticate" %>
<%@ page import="com.assemble.java.assemblecodebase.model.User" %>
<script src="https://code.jquery.com/jquery-4.0.0.min.js"
        integrity="sha256-OaVG6prZf4v69dPg6PhVattBXkcOWQB62pdZ3ORyrao="
        crossorigin="anonymous"></script>
<link rel="stylesheet" href="assets/css/global.css">
<%
  User requestingUser = Authenticate.RetrieveRequestingUser(request);
  if (requestingUser == null)
    requestingUser = new User();
  request.setAttribute("requestingUser", requestingUser);
%>