<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Personnel</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<div class="panel">
    <h1>Personnel</h1>
    <div class="new-entry-bar">
        <span>
            Date Range
            <label for="start-date" class="hidden">Start Date</label>
            <input type="date" id="start-date">
            -
            <label for="end-date" class="hidden">End Date</label>
            <input type="date" id="end-date">
            <input type="submit" value="Submit">
        </span>
    </div>
    <table>
        <tr>
            <th></th>
            <th>Date</th>
            <th>Quantity</th>
            <th></th>
        </tr>
        <tr>
            <td>
                <button class="count-control">+</button>
                <button class="count-control">-</button>
            </td>
            <td>05/01/2026</td>
            <td>25</td>
            <td>
                <button class="delete-btn">Delete</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
