<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Brendan04
  Date: 5/11/2026
  Time: 10:43 PM
  To change this template use File | Settings | File Templates.
--%>

<%
  if (Authenticate.RetrieveRequestingUser(request) == null) {
%>
<c:redirect url="Calendar"/>
<%}%>


<header>
  <link rel="stylesheet" href="assets/css/navigation.css">
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
    <c:if test="${requestingUser.clearanceAtLeast('admin')}">
      <a href="AdminTools">Admin Tools</a>
    </c:if>
  </nav>
</header>