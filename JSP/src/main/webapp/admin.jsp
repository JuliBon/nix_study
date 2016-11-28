<%@ page import="com.nixsolutions.bondarenko.study.jsp.user.library.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

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
    <ex:UserTable userList="${userList}"></ex:UserTable>
    <a href=/admin/delete@id=3>Delete</a>
    <a href=/admin/delete&id=3>Delete</a>
    <a href=/admin/delete$id=3>Delete</a>
    <div>Click <a href="/logout">here</a> to logout</div>
</div>
</body>
</html>