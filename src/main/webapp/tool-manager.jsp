<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tools</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<div class="panel">
    <h1>Tools</h1>
    <form action="Tools" method="post" class="new-entry-bar">
        <label for="tool-type-id">Tool Type</label>
        <select name="tool-type-id" id="tool-type">
            <c:if test="${not empty toolTypes}">
                <c:forEach var="toolType" items="${toolTypes}">
                    <option value="${toolType.id}">${toolType.description}</option>
                </c:forEach>
            </c:if>
        </select>
        <input type="submit" value="Submit">
    </form>
    <table>
        <tr>
            <th></th>
            <th>Tool Id</th>
            <th>Quantity</th>
            <th></th>
        </tr>
        <c:if test="${not empty tools}">
            <c:forEach var="tool" items="${tools}">
                <tr resource-id="${tool.id}">
                    <td>
                        <button class="count-control add">+</button>
                        <button class="count-control remove">-</button>
                    </td>
                    <td>${tool.id}</td>
                    <td class="resource-count">${tool.count}</td>
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
