<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inventory</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<script src="assets/js/inventory-manager.js"></script>
<div class="panel">
    <h1>Inventory</h1>
    <form action="Inventory" method="post" class="new-entry-bar">
        <label for="inventory-type-id">Inventory Type</label>
        <select name="inventory-type-id" id="inventory-type">
            <c:if test="${not empty inventoryTypes}">
                <c:forEach var="inventoryType" items="${inventoryTypes}">
                    <option value="${inventoryType.id}">${inventoryType.description}</option>
                </c:forEach>
            </c:if>
        </select>
        <input type="submit" name="submit" value="Submit">
    </form>
    <table>
        <tr>
            <th></th>
            <th>InventoryId</th>
            <th>Quantity</th>
            <th></th>
        </tr>
        <c:if test="${not empty inventory}">
            <c:forEach var="resource" items="${inventory}">
                <tr resource-id="${resource.id}">
                    <td>
                        <button class="count-control add">+</button>
                        <button class="count-control remove">-</button>
                    </td>
                    <td>${resource.id}</td>
                    <td class="resource-count">${resource.count}</td>
                    <td>
                        <button class="delete-btn">Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
</body>
</html>
