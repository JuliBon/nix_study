<%@ page import="java.util.List" %>
<%@ page import="com.nixsolutions.bondarenko.study.jsp.user.library.User" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>

<%
    List<User> userList = (List<User>) request.getAttribute("userList");
    pageContext.setAttribute("userList", userList);
%>

<script>
    $('[data-toggle=confirmation]').confirmation({
        rootSelector: '[data-toggle=confirmation]'
    });
</script>

<html>
<head>
    <title>Admin home</title>
    <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/shared.css" rel="stylesheet">

    <script src="lib/jquery.js"></script>
    <script src="lib/bootstrap/js/bootstrap.js"></script>
    <script src="lib/bootstrap/js/bootstrap-confirmation.js"></script>
</head>
<body>

<div class="container">
    <a href="admin?action=create_user">Add new user</a>
    <ex:UserTable userList="${userList}"/>
    <div>Click <a href="logout">here</a> to logout</div>
</div>
</body>
</html>