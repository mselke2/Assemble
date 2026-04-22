<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calendar</title>
    <link rel="stylesheet" href="assets/css/calendar.css">
</head>
<body>
<script src="assets/js/calendar.js"></script>
<main>
    <div id="calendar-header">
        <button class="prev-btn">Previous</button>
        <h1 id="month-name">January</h1>
        <button class="next-btn">Next</button>
    </div>
    <ol id="calendar-days">
        <% for (int i = 0; i < 35; i++) { %>
        <li class="calendar-day">
            <p class="day-number">1</p>
        </li>
        <% } %>
    </ol>
</main>
</body>
</html>
