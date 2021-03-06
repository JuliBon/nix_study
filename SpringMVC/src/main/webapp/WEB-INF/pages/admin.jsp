<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.nixsolutions.bondarenko.study.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>

<html>
<head>
    <title>Admin home</title>
    <link href="<c:url value="/resources/lib/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/shared.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <div class="adminLogout">Admin ${userName} <a href="/logout">(logout)</a></div>
    <a href="/admin/create"><h3>Add new user</h3></a>
    <ex:UserTable userList="${userList}"/>
</div>
</body>
</html>

