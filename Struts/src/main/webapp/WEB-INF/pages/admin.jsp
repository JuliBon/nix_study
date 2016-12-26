<%@ page import="com.nixsolutions.bondarenko.study.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ taglib prefix="ex" uri="UserLibrary" %>

<html>
<head>
    <title>Admin home</title>
    <link href="<s:url value="/resources/lib/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<s:url value="/resources/css/shared.css"/>" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <%--    <div class="adminLogout">Admin ${userName} <a href="/logout">(logout)</a></div>
        <a href="/admin/create"><h3>Add new user</h3></a>--%>
    <ex:UserTable userList="${userList}"/>
</div>
</body>
</html>

