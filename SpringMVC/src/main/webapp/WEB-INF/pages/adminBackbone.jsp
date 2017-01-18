<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin page</title>

    <script src="${pageContext.request.contextPath}/resources/lib/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/underscore.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backbone.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/backbone/router.js"></script>

    <link href="${pageContext.request.contextPath}/resources/css/adminBackbone.css" media="all" rel="stylesheet"/>
</head>
<body>
    <div id="menu">
        <ul>
            <li><a href="#!/">Users</a></li>
            <li><a href="#!/create">Create user</a></li>
            <li><a href="#!/edit">Edit user</a></li>
        </ul>
    </div>
    <div id="users" class="block">
        User list
    </div>
    <div id="createUser" class="block">
        Create user
    </div>
    <div id="editUser" class="block">
        Edit user
    </div>
</body>
</html>
