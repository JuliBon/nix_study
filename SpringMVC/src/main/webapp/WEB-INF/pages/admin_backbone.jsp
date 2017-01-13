<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User management</title>

    <script src="${pageContext.request.contextPath}/resources/lib/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/underscore.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backbone.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backgrid/backgrid.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/models.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

    <link href="${pageContext.request.contextPath}/resources/lib/backgrid/backgrid.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>

<div id="usersApp">
    <div class="title">
        <h1>Users</h1>
    </div>

    <div id="userGrid">
    </div>
</div>
</body>
</html>
