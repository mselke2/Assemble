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
    <form action="User" method="post">
        <label for="username">Username</label>
        <input type="text" name="username" id="username" minlength="3" maxlength="50" required>
        <label for="password">Password</label>
        <input type="password" name="password" id="password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$" required>
        <div id="password-requirements">
            Password must:
            <ul>
                <li>Contain at least 1 uppercase letter</li>
                <li>Contain at least 1 lowercase letter</li>
                <li>Contain at least 1 special character</li>
                <li>Be at least 8 characters long</li>
            </ul>
        </div>
        <label for="password-repeat">Confirm Password</label>
        <input type="password" name="password-repeat" id="password-repeat" required>
        <label for="type">Editor/Viewer</label>
        <select id="type" name="type" required>
            <option>Editor</option>
            <option>Viewer</option>
        </select>
        <input type="submit" name="submit" value="Submit">
    </form>
</div>
</body>
</html>
