<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.nixsolutions.bondarenko.study.jsp.user.library.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>

<%
    List<User> userList = (List<User>) request.getAttribute("userList");
    pageContext.setAttribute("userList", userList);

    User currentUser = (User) request.getSession().getAttribute("currentUser");
    if (currentUser != null) {
        pageContext.setAttribute("currentUserLogin", currentUser.getLogin());
    }
%>
<html>
<head>
    <title>Admin home</title>
    <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/shared.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="adminLogout">Admin ${currentUserLogin} <a href="logout">(logout)</a></div>
    <a href="admin?action=create_user"><h3>Add new user</h3></a>
    <ex:UserTable userList="${userList}"/>
</div>
</body>
</html>

