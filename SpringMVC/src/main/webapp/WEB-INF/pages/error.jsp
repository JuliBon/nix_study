<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="th" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link href="<c:url value="/resources/css/shared.css"/>" rel="stylesheet" type="text/css">
</head>
<body>

<div class="container errorContainer">
    <h2>Error ${status}</h2>
    <p>Some error occurred while processing request</p>
    <br/>
    <a href="javascript:history.back()">Go back</a>
</div>
</body>
</html>


