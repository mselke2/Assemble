<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                        <div style="--start-time: 5; --end-time: 15; --lane: 0; background-color: orange;">
                            <p>01</p>
                        </div>
                        <div style="--start-time: 17; --end-time: 24; --lane: 1; background-color: green;">
                            <p>02</p>
                        </div>
                        <div style="--start-time: 20; --end-time: 22; --lane: 0; background-color: darkblue;">
                            <p>03</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="right-panel">
            <div class="job-info-wrapper">
                <div class="job-info">
                    <h1>Date: MM/DD/YYYY</h1>
                    <label for="job-id" class="info-left">JobId:</label>
                    <p>XX</p><br>
                    <label for="product-choice" class="info-left">Product:</label>
                    <select id="product-choice">
                        <option>ProductA</option>
                        <option>ProductB</option>
                        <option>ProductC</option>
                    </select><br>
                    <label for="start-time" class="info-left">From:</label>
                    <input id="start-time" type="time"><br>
                    <label for="end-time" class="info-left">To:</label>
                    <input id="end-time" type="time"><br>
                    <label for="num-members" class="info-left">#OfTeamMembers:</label>
                    <input id="num-members" type="number" min="1">
                    <label for="line-num">Line #:</label>
                    <input id="line-num" type="number" min="1"><br>
                    <button>Cancel</button>
                    <input type="submit" value="Submit">
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>
