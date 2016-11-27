<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>User home</title>

    <link href="css/shared.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Hello, ${user.login}</h2>
    <div>Click <a href="/logout">here</a> to logout</div>
</div>
</body>
</html>