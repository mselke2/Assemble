<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <title>Personnel</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<%@ include file="navigation.jsp" %>
<div class="panel">
    <h1>Personnel</h1>
    <form action="Personnel" method="post" class="new-entry-bar">
        <span>
            <label for="date">Add Personnel:</label>
            <input type="date" name="date" id="date" required>
            <input type="submit" name="submit" value="Submit">
        </span>
    </form>
    <table>
        <tr>
            <th></th>
            <th>Date</th>
            <th>Quantity</th>
            <th></th>
        </tr>
        <c:if test="${not empty personnel}">
            <c:forEach var="scheduledPersonnel" items="${personnel}">
                <tr resource-id="${scheduledPersonnel.id}">
                    <td><button class="submit-btn">Submit</button></td>
                    <td>${scheduledPersonnel.date.toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))}</td>
                    <td class="resource-count"><input type="number" name="personnelCount" required min="1" value="${scheduledPersonnel.count}"></td>
                    <td><button class="delete-btn">Delete</button></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
<script src="assets/js/resource-manager.js"></script>
<script src="assets/js/personnel-manager.js"></script>
</body>
</html>
