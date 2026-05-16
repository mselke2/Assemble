<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Scheduler</title>
    <link rel="stylesheet" href="assets/css/scheduler.css">
</head>
<body>
<main>
    <div class="split-wrapper">
        <div class="left-panel">
            <div class="timeline-wrapper">
                <div class="timeline">
                    <div class="timeline-ticks">
                        <p>12:00am</p>
                        <p>1:00am</p>
                        <p>2:00am</p>
                        <p>3:00am</p>
                        <p>4:00am</p>
                        <p>5:00am</p>
                        <p>6:00am</p>
                        <p>7:00am</p>
                        <p>8:00am</p>
                        <p>9:00am</p>
                        <p>10:00am</p>
                        <p>11:00am</p>
                        <p>12:00pm</p>
                        <p>1:00pm</p>
                        <p>2:00pm</p>
                        <p>3:00pm</p>
                        <p>4:00pm</p>
                        <p>5:00pm</p>
                        <p>6:00am</p>
                        <p>7:00pm</p>
                        <p>8:00pm</p>
                        <p>9:00pm</p>
                        <p>10:00pm</p>
                        <p>11:00pm</p>
                    </div>
                    <div id="timeline-lanes">
                        <div id="create-job-ghost" class="job-entry">
                            <div></div>
                        </div>
                        <c:if test="${not empty jobs}">
                            <c:forEach var="job" items="${jobs}">
                                <div class="job-entry" job-id="${job.id}" style="--start-time: ${job.startTime.getHours() + job.startTime.getMinutes() / 60}; --end-time: ${job.projectedEndTime.getHours() + job.projectedEndTime.getMinutes() / 60}; --lane: ${job.lineNumber}; background-color: orange;">
                                    <p>${job.id}</p>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="right-panel">
            <div class="job-info-wrapper">
                <div class="job-info">
                    <h1>Date: <span id="date-display"></span></h1>
                    <div id="job-form" hidden>
                        <input type="hidden" name="date" id="date" value="${param.d.substring(0, 4)}-${param.d.substring(4, 6)}-${param.d.substring(6)}">
                        <p id="new-job-label" class="info-left">New Job</p>
                        <label for="job-id" id="job-id-label" class="info-left">JobId:</label>
                        <p id="job-id">XX</p><br>
                        <label for="product-choice" class="info-left">Product:</label>
                        <select name="product-choice-id" id="product-choice">
                            <c:if test="${not empty productTypes}">
                                <c:forEach var="productType" items="${productTypes}">
                                    <option value="${productType.id}">${productType.description}</option>
                                </c:forEach>
                            </c:if>
                        </select><br>
                        <label for="start-time" class="info-left">From:</label>
                        <input type="time" name="start-time" id="start-time"><br>
                        <label for="end-time" class="info-left">To:</label>
                        <input type="time" name="end-time" id="end-time"><br>
                        <label for="num-members" class="info-left">#OfTeamMembers:</label>
                        <input  type="number" min="1" name="num-members" id="num-members">
                        <label for="line-num">Line #:</label>
                        <input type="number" min="1" name="line-num" id="line-num"><br>
                        <button id="cancel-btn">Cancel</button>
                        <button id="submit-btn">Submit</button><br><br>
                        <button id="delete-btn">Delete</button><br>
                        <p id="error" class="error"></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <a id="back-button" href="Calendar">Go Back</a>
</main>
<script src="assets/js/scheduler.js"></script>
<script src="assets/js/job-color.js"></script>
</body>
</html>
