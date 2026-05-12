<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Brendan04
  Date: 4/29/2026
  Time: 9:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="com.google.gson.Gson" %>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="assets/css/add-account.css">
</head>
<body>
<div class="panel">
    <h1>Login</h1>
    <form action="Login" method="post">
        <label for="username">Username</label>
        <input type="text" name="username" id="username">
        <label for="password">Password</label>
        <input type="password" name="password" id="password">
        <h2 style="color: ${color}">${message}</h2>
        <input type="submit" name="submit" value="Submit">
    </form>
</div>

<script></script>
</body>
</html>
