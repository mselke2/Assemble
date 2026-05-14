<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product</title>
    <link rel="stylesheet" href="assets/css/product.css">
</head>
<body>
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

                    <td><button class="delete-btn">Delete</button></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
<script src="assets/js/resource-manager.js"></script>
<script src="assets/js/product-manager.js"></script>
</body>
</html>
