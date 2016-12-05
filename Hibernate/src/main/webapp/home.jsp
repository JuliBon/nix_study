<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.nixsolutions.bondarenko.study.jsp.user.library.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>User home</title>
    <link href="css/shared.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Hello, ${currentUser.login}</h2>
    <div>Click <a href="logout">here</a> to logout</div>
</div>
</body>
</html>