<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inventory</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<%@ include file="navigation.jsp" %>
<div class="panel">
    <h1>Inventory</h1>
    <p class="warning"  style="color: red">Warning: Decreasing inventory counts will delete all jobs associated with the deleted inventory type.</p>
    <c:if test="${requestingUser.clearanceAtLeast('editor')}">
    <form action="Inventory" method="post" class="new-entry-bar">
        <label for="inventory-type">Inventory Type</label>
        <select name="inventoryTypeId" id="inventory-type">
            <c:if test="${not empty inventoryTypes}">
                <c:forEach var="inventoryType" items="${inventoryTypes}">
                    <option value="${inventoryType.id}">${inventoryType.description}</option>
                </c:forEach>
            </c:if>
        </select>
        <input type="submit" name="submit" value="Submit">
    </form>
    </c:if>
    <table>
        <tr>
            <th></th>
            <th>InventoryId</th>
            <th>Type</th>
            <th>Quantity</th>
            <th></th>
        </tr>
        <c:if test="${not empty inventory}">
            <c:forEach var="resource" items="${inventory}">
                <tr resource-id="${resource.id}">
                  <td><c:if test="${requestingUser.clearanceAtLeast('editor')}"><button class="submit-btn">Submit</button></c:if></td>
                    <td>${resource.id}<input type="hidden" name="typeId" value="${resource.typeId}"> </td>
                    <td>${resource.typeDescription}</td>
                  <td class="resource-count"><input type="number" name="count" required min="0" value="${resource.count}" <c:if test="${!requestingUser.clearanceAtLeast('editor')}">disabled</c:if>></td>
                  <td><c:if test="${requestingUser.clearanceAtLeast('editor')}"><button class="delete-btn">Delete</button></c:if></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
<script src="assets/js/resource-manager.js"></script>
<script src="assets/js/inventory-manager.js"></script>
</body>
</html>
