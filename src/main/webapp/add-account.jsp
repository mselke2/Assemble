<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Account</title>
    <link rel="stylesheet" href="assets/css/add-account.css">
</head>
<body>
<div class="panel">
    <h1>Add Account</h1>
    <form>
        <label for="username">Username</label>
        <input type="text" id="username">
        <label for="password">Password</label>
        <input type="password" id="password">
        <label for="password-repeat">Confirm Password</label>
        <input type="password" id="password">
        <label for="type">Editor/Viewer</label>
        <select id="type">
            <option>Editor</option>
            <option>Viewer</option>
        </select>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>
