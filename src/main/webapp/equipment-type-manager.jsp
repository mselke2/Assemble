<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Equipment Type</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<%@ include file="navigation.jsp" %>
<div class="panel">
    <h1>Equipment Type</h1>
    <c:if test="${requestingUser.clearanceAtLeast('admin')}">
    <p class="warning" style="color: red">Warning: Deleting equipment types will delete all jobs associated with the deleted equipment type.</p>
    <form action="EquipmentType" method="post" class="new-entry-bar">
        <label for="equipment-type-description">Description</label>
        <input type="text" name="description" maxlength="50" required id="equipment-type-description">
        <input type="submit" name="submit" value="Submit">
    </form>
    </c:if>
    <table>
        <tr>
            <th></th>
            <th>Id</th>
            <th>Description</th>
            <th></th>
        </tr>
        <c:if test="${not empty equipmentTypes}">
            <c:forEach var="equipmentType" items="${equipmentTypes}">
                <tr resource-id="${equipmentType.id}">
                  <td><c:if test="${requestingUser.clearanceAtLeast('admin')}"><button class="submit-btn">Submit</button></c:if></td>
                    <td>${equipmentType.id}</td>
                  <td><input type="text" name="description" maxlength="50" required value="${equipmentType.description}" <c:if test="${!requestingUser.clearanceAtLeast('admin')}">disabled</c:if> ></td>
                  <td><c:if test="${requestingUser.clearanceAtLeast('admin')}"><button class="delete-btn">Delete</button></c:if></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
<script src="assets/js/resource-manager.js"></script>
<script src="assets/js/equipment-type-manager.js"></script>
</body>
</html>
