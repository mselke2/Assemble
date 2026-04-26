<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inventory</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<div class="panel">
    <h1>Inventory</h1>
    <div class="new-entry-bar">
        <label for="inventory-type">Inventory Type</label>
        <select id="inventory-type">
            <option>Rivets</option>
            <option>Washers</option>
            <option>Nails</option>
        </select>
        <input type="submit" value="Submit">
    </div>
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
