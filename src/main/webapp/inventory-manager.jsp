<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inventory</title>
    <link rel="stylesheet" href="assets/css/resource-manager.css">
</head>
<body>
<div class="panel">
    <h1>Inventory</h1>
    <div class="new-entry-bar">
        <label for="inventory-type">Inventory Type</label>
        <select id="inventory-type">
            <option>Rivets</option>
            <option>Washers</option>
            <option>Nails</option>
        </select>
        <input type="submit" value="Submit">
    </div>
    <table>
        <tr>
            <th></th>
            <th>Id</th>
            <th>Quantity</th>
            <th></th>
        </tr>
        <tr>
            <td>
                <button class="count-control">+</button>
                <button class="count-control">-</button>
            </td>
            <td>0123</td>
            <td>25</td>
            <td>
                <button class="delete-btn">Delete</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
