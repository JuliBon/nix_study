<%@ page import="java.util.List" %>
<%@ page import="com.nixsolutions.bondarenko.study.jsp.user.library.User" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>

<%
    List<User> userList = (List<User>) request.getAttribute("userList");
    pageContext.setAttribute("userList", userList);
%>

<html>
<head>
    <title>Admin home</title>
    <link href="css/shared.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <a href="/admin?action=create_user">Add new user</a>
    <ex:UserTable userList="${userList}" ></ex:UserTable>

    <div>Click <a href="/logout">here</a> to logout</div>
</div>
</body>
</html>