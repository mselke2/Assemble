<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Equipment</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<%@ include file="navigation.jsp" %>
<div class="panel">
    <h1>Equipment</h1>
    <p class="warning" style="color: red;">Warning: Setting an equipment status to lost/broken will delete all jobs with the associated equipment type.</p>
    <c:if test="${requestingUser.clearanceAtLeast('editor')}">
    <form action="Equipment" method="post" class="new-entry-bar">
        <label for="tool-type-id">Equipment Type</label>
        <select name="equipmentTypeId" id="tool-type-id">
            <c:if test="${not empty equipmentTypes}">
                <c:forEach var="equipmentType" items="${equipmentTypes}">
                    <option value="${equipmentType.id}">${equipmentType.description}</option>
                </c:forEach>
            </c:if>
        </select>
        <input type="submit" value="Submit">
    </form>
    </c:if>
    <table>
        <tr>
            <th></th>
            <th>Equipment Id</th>
            <th>Type</th>
            <th>Condition</th>
            <th></th>
        </tr>
        <c:if test="${not empty equipmentList}">
            <c:forEach var="equipment" items="${equipmentList}">
                <tr resource-id="${equipment.id}">
                    <td>
                      <c:if test="${requestingUser.clearanceAtLeast(\"editor\")}"><button class="submit-btn">Submit</button></c:if>
                    </td>
                    <td>${equipment.id}</td>
                    <td>${equipment.typeDescription}</td>
                    <td class="resource-count">
                      <select name="status" <c:if test="${!requestingUser.clearanceAtLeast(\"editor\")}">disabled</c:if>>
                        <option value="0" <c:if test="${equipment.status == 0}">selected</c:if>>Lost/Damaged</option>
                        <option value="1" <c:if test="${equipment.status == 1}">selected</c:if>>Good</option>
                      </select>
                    </td>
                    <td>
                      <c:if test="${requestingUser.clearanceAtLeast(\"editor\")}"><button class="delete-btn">Delete</button></c:if>
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
