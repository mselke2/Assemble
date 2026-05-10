<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Equipment</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<div class="panel">
    <h1>Equipment</h1>
    <form action="Equipment" method="post" class="new-entry-bar">
        <label for="tool-type-id">Equipment Type</label>
        <select name="tool-type-id" id="tool-type-id">
            <c:if test="${not empty equipmentTypes}">
                <c:forEach var="equipmentType" items="${equipmentTypes}">
                    <option value="${equipmentType.id}">${equipmentType.description}</option>
                </c:forEach>
            </c:if>
        </select>
        <input type="submit" value="Submit">
    </form>
    <table>
        <tr>
            <th></th>
            <th>Equipment Id</th>
            <th>Status</th>
            <th></th>
        </tr>
        <c:if test="${not empty equipmentList}">
            <c:forEach var="equipment" items="${equipmentList}">
                <tr resource-id="${equipment.id}">
                    <td>
                    </td>
                    <td>${equipment.id}</td>
                    <td class="resource-count">${equipment.status}</td>
                    <td>
                        <button class="delete-btn">Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
<script src="assets/js/resource-manager.js"></script>
<script src="assets/js/tool-manager.js"></script>
</body>
</html>
