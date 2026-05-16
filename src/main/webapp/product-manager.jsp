<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product</title>
    <link rel="stylesheet" href="assets/css/product.css">
</head>
<body>
<%@ include file="navigation.jsp" %>
<div class="panel">
    <h1>Product</h1>
    <form action="Product" method="post" class="new-entry-bar">

        <label for="description" class="productLabel">Description</label>
        <input type="text" class="productInput" name="description" id="description"><br>

        <label for="duration" class="productLabel">Duration (Minutes)</label>
        <input type="text" name="duration" class="productInput" id="duration"><br>

        <label for="personnel-count" class="productLabel">Personnel Count</label>
        <input type="text" name="personnelCount" class="productInput" id="personnel-count"><br><br>

        <input class="submit" type="submit" name="submit" value="Submit">

    </form>

    <br><br>

    <table>
        <tr>
            <th></th>
            <th class="header">ID</th>
            <th class="header">Description</th>
            <th class="header">Duration (Minutes)</th>
            <th class="header">Personnel Count (Target)</th>
            <th class="header">Inventory</th>
            <th class="header">Equipment</th>
            <th></th>
        </tr>

        <c:if test="${not empty products}">
            <c:forEach var="product" items="${products}">
                <tr resource-id="${product.id}">

                    <td><button class="submit-btn">Submit</button></td>

                    <td class="data">${product.id}</td>

                    <td class="data"><input type="text" name="description" required class="description" pattern="^.{1,50}$"  value="${product.description}"></td>

                    <td class="data"><input type="number" name="duration" required class="duration" min="1" value="${product.minutesDuration}"></td>

                    <td class="resource-count data"><input type="number" name="personnelCount" required class="personnel-count" min="1" value="${product.targetPersonnelCount}"></td>

                    <td>
                        <button class="inventory-btn">Edit</button>
                        <input type="hidden" name="inventoryIds" class="inventory-ids" value="${product.requiredInventoryIds}">
                        <input type="hidden" name="inventoryCounts" class="inventory-counts" value="${product.requiredInventoryCounts}">
                    </td>

                    <td>
                        <button class="equipment-btn">Edit</button>
                        <input type="hidden" name="equipmentIds" class="equipment-ids" value="${product.requiredEquipmentIds}">
                        <input type="hidden" name="equipmentCounts" class="equipment-counts" value="${product.requiredEquipmentCounts}">
                    </td>

                    <td><button class="delete-btn">Delete</button></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
<div id="inventory-editor" class="editor" hidden>
    <div class="editor-panel">
        <h1>Required Inventory Counts</h1>
        <ul>
        <c:if test="${not empty inventoryTypes}">
            <c:forEach var="inventoryType" items="${inventoryTypes}">
                <li>
                    <label>${inventoryType.description}:</label>
                    <input type="number" inventory-type-id="${inventoryType.id}" min="0" class="inventory-count">
                </li>
            </c:forEach>
        </c:if>
        </ul>
        <button class="editor-submit">Ok</button>
    </div>
</div>
<div id="equipment-editor" class="editor" hidden>
    <div class="editor-panel">
        <h1>Required Equipment Counts</h1>
        <ul>
        <c:if test="${not empty equipmentTypes}">
            <c:forEach var="equipmentType" items="${equipmentTypes}">
                <li>
                    <label>${equipmentType.description}:</label>
                    <input type="number" equipment-type-id="${equipmentType.id}" min="0" class="equipment-count">
                </li>
            </c:forEach>
        </c:if>
        </ul>
        <button class="editor-submit">Ok</button>
    </div>
</div>
<script src="assets/js/resource-manager.js"></script>
<script src="assets/js/product-manager.js"></script>
</body>
</html>
