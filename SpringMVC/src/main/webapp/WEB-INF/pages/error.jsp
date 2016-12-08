<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link href="<c:url value="/resources/css/shared.css"/>" rel="stylesheet" type="text/css">
</head>
<body>

<div class="container errorContainer">
    <h1>Error! </h1>
    <br/>
    <p>${error.getMessage()}</p>
</div>

</body>
</html>


