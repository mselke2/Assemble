<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Brendan04
  Date: 5/11/2026
  Time: 10:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="assets/css/navigation.css">
</head>
<body>
<%
  Cookie[] cookies = request.getCookies();

  Cookie permissionCookie = null;
  if (cookies != null && cookies.length >= 3) {
    for (Cookie cookie : cookies) {
      String name = cookie.getName();
      if (name.equals("permissionLevel")) {
        permissionCookie = cookie;
      }
    }
  } else {

%>
<c:redirect url="Calendar"/>
<%}%>

<header>
  <div class="navDiv">
    <h1 id="navH1">Assemble</h1>
    <form action="Logout" method="post"><input type="submit" name="logout" value="Logout"></form>
  </div>
  <nav id="navBar">
    <a href="Calendar">Calendar</a>
    <a href="Personnel">Personnel</a>
    <a href="Product">Products</a>
    <a href="Equipment">Equipment</a>
    <a href="EquipmentType">Equipment Types</a>
    <a href="Inventory">Inventory</a>
    <a href="InventoryType">Inventory Types</a>
    <% if (permissionCookie != null && permissionCookie.getValue().equals("1")) {%>
    <a href="admin-tools.jsp">Admin Tools</a>
    <% } %>
  </nav>
</header>
</body>
</html>
