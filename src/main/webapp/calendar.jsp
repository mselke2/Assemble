<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calendar</title>
    <link rel="stylesheet" href="assets/css/calendar.css">
</head>
<body>
<%@ include file="navigation.jsp" %>
<script src="assets/js/calendar.js"></script>
<main>
    <div id="calendar-header">
        <button id="prev-btn">Previous</button>
        <h1 id="month-name"></h1>
        <button id="next-btn">Next</button>
    </div>
    <ol id="calendar-days">
        <% for (int i = 0; i < 35; i++) { %>
        <li class="calendar-day-wrapper">
            <a class="calendar-day">
                <p class="day-number"></p>
            </a>
        </li>
        <% } %>
    </ol>
</main>
</body>
</html>
