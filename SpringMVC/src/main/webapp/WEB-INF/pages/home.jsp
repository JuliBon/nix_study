<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.nixsolutions.bondarenko.study.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>User home</title>

    <link href="<c:url value="/resources/css/shared.css"/>" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container helloContainer">
        <h2>Hello, ${userName}</h2>
        <div>Click <a href="/logout">here</a> to logout</div>
</div>
</body>
</html>