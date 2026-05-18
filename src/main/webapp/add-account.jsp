<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Add Account</title>
  <link rel="stylesheet" href="assets/css/add-account.css">
</head>
<body>
<%@ include file="navigation.jsp" %>
<div class="panel">
  <h1>Add Account</h1>
  <form id="create-user-form">
    <label for="fName">First Name</label>
    <input type="text" name="fName" id="fName" pattern="^[a-zA-Z]{1,50}$" required>
    <label for="lName">Last Name</label>
    <input type="text" name="lName" id="lName" pattern="^[a-zA-Z]{1,50}$" required>
    <label for="username">Username</label>
    <input type="text" name="username" id="username" pattern="^[a-zA-Z0-9]{3,50}$" required>
    <label for="password">Password</label>
    <input type="password" name="password" id="password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$"
           required>
    <div id="password-requirements">
      Password must:
      <ul>
        <li>Contain at least 1 uppercase letter</li>
        <li>Contain at least 1 lowercase letter</li>
        <li>Contain at least 1 special character</li>
        <li>Be at least 8 characters long</li>
      </ul>
    </div>
    <label for="password-repeat">Confirm Password</label>
    <input type="password" name="password-repeat" id="password-repeat" required>
    <label for="type">Editor/Viewer</label>
    <select id="type" name="type" required>
      <c:if test="${not empty userPermissionTypes}">
        <c:forEach var="permissionType" items="${userPermissionTypes}">
          <option value="${permissionType.id}">${permissionType.description}</option>
        </c:forEach>
      </c:if>
    </select>
    <input type="submit" name="submit" id="submit" value="Submit">
  </form>
  <p id="message"></p>
</div>
<script src="assets/js/add-account.js"></script>
</body>
</html>
