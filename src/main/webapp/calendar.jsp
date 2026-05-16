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
<script src="assets/js/job-color.js"></script>
<main>
    <div id="calendar-header">
        <button id="prev-btn">Previous</button>
        <h1 id="month-name"></h1>
        <button id="next-btn">Next</button>
    </div>
    <ol id="calendar-days">
        <li class="weekday-header">Sunday</li>
        <li class="weekday-header">Monday</li>
        <li class="weekday-header">Tuesday</li>
        <li class="weekday-header">Wednesday</li>
        <li class="weekday-header">Thursday</li>
        <li class="weekday-header">Friday</li>
        <li class="weekday-header">Saturday</li>
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
