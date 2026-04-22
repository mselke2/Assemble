<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tools</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<div class="panel">
    <h1>Tools</h1>
    <div class="new-entry-bar">
        <label for="tool-type">Tool Type</label>
        <select id="tool-type">
            <option>Hammer</option>
            <option>Drill</option>
            <option>Nail Gun</option>
        </select>
        <input type="submit" value="Submit">
    </div>
    <table>
        <tr>
            <th></th>
            <th>Tool Id</th>
            <th>Quantity</th>
            <th></th>
        </tr>
        <tr>
            <td>
                <button class="count-control">+</button>
                <button class="count-control">-</button>
            </td>
            <td>01</td>
            <td>25</td>
            <td>
                <button class="delete-btn">Delete</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
