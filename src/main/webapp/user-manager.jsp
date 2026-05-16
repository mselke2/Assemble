<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: amber
  Date: 5/10/26
  Time: 11:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
    <link rel="stylesheet" type="text/css" href="assets/css/user.css"/>
</head>
<body>
<%@ include file="navigation.jsp" %>
<div id="logged-in-user" user-id="${requestingUser.id}" hidden></div>
<div class="panel" id="formPanel">
  <h1>Edit User</h1>

  <form class="userForm" action="User" method="POST">

    <label for="username">Username:</label>
    <input class="userFormInput" type="text" id="username" name="username"/><br>

    <label for="password">Password: (Leave blank to keep old password)</label>
    <input class="userFormInput" type="password" id="password" name="password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$"/><br>

    <label for="password-repeat">Confirm Password:</label>
    <input class="userFormInput" type="password" id="password-repeat" name="password-repeat" /><br>

    <label for="fName">First Name:</label>
    <input class="userFormInput" type="text" id="fName" name="fName" /><br>

    <label for="lName">Last Name:</label>
    <input class="userFormInput" type="text" id="lName" name="lName" /><br>

    <div id="permission-field">
      <label for="type">Permission Level</label>
      <select id="type" name="type" class="userFormInput">
        <c:if test="${not empty userPermissionTypes}">
          <c:forEach var="permissionType" items="${userPermissionTypes}">
            <option value="${permissionType.id}">${permissionType.description}</option>
          </c:forEach>
        </c:if>
      </select>
    </div>

    <label for="userToEdit" typeof="hidden"></label>
    <input class="userFormInput" type="hidden" id="userToEdit" name="userToEdit" /><br>

    <input type="submit" value="Submit"/><br>
  </form>
  <button id="delete-user-btn">Delete User</button>

</div>

<c:if test="${not empty users}">

  <div class="panel" id="tablePanel">
    <h2>Existing Users</h2>

    <table class="userTable">
      <tr class="headerRow">
        <th class="tableHeader">Username (Click to edit user)</th>
        <th class="tableHeader">First Name</th>
        <th class="tableHeader">Last Name</th>
        <th class="tableHeader">Permission</th>
      </tr>

      <c:forEach var="user" items="${users}">
        <tr class="userRow">
          <td class="tableData username">${user.username}</td>
          <td class="tableData">${user.firstName}</td>
          <td class="tableData">${user.lastName}</td>
          <td class="tableData">
            <c:if test="${user.permissionId == 1}">Administrator</c:if>
            <c:if test="${user.permissionId == 2}">Editor</c:if>
            <c:if test="${user.permissionId == 3}">Viewer</c:if>
          </td>
        </tr>
      </c:forEach>

    </table><br>

    <h2 class="message" style="color: ${color}">${message}</h2>
  </div>

</c:if>

<script src="assets/js/user-manager.js"></script>
</body>
</html>
