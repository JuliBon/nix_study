<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User management</title>
    <script src="${pageContext.request.contextPath}/resources/lib/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/underscore.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backbone.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/models.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</head>
<body>
<button onclick="save()">Save default</button>
<button onclick="get()">Get all</button>


<div id="usersApp">
    <div class="title">
        <h1>Users</h1>
    </div>

    <div class="content">
        <div id="users">
            <ul id="userList"></ul>
        </div>
    </div>
</div>

<script type="text/template" id="itemTemplate">
    <div class="user-item">
        <div class="display">
            <div class="user-content">
                <div class="user-id"></div>
                <div class="user-login"></div>
                <div class="user-password"></div>
                <div class="user-email"></div>
                <div class="user-first-name"></div>
                <div class="user-last-name"></div>
                <div class="user-birthday"></div>
                <div class="user-role-id"></div>
                <div class="user-role-name"></div>
                <span class="user-destroy"></span>
            </div>
        </div>
    </div>
</script>
</body>
</html>
