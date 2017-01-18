<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin page</title>

    <script src="${pageContext.request.contextPath}/resources/lib/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/underscore.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backbone.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/backbone/models.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/backbone/main.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/backbone/router.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backbone-validation.js"></script>

    <link href="${pageContext.request.contextPath}/resources/css/adminBackbone.css" media="all" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet"
          type="text/css">
    <link href="${pageContext.request.contextPath}/resources/css/shared.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container" id="usersApp">
    <div class="adminLogout">Admin ${userName} <a href="/logout">(logout)</a></div>
    <div id="menu">
        <ul class="list-inline">
            <li><a href="#!/">Users</a></li>
            <li><a href="#!/create">Create user</a></li>
        </ul>
    </div>
    <div id="users" class="block">
        <h2>Users</h2>
    </div>
    <div id="createUser" class="block">
        <h2>Create user</h2>
    </div>
    <div id="editUser" class="block">
        <h2>Edit user</h2>
    </div>
</div>

<script type="text/template" id="itemTemplate">
    <td class="login"></td>
    <td class="email"></td>
    <td class="first-name"></td>
    <td class="last-name"></td>
    <td class="age"></td>
    <td class="role"></td>
    <td class="actions">
        <button class="btn-delete">Delete</button>
        <a class="link-edit">Edit user</a>
    </td>
</script>

<script type="text/template" id="usersTemplate">
    <table id="usersTable" class="table">
        <thead>
        <tr>
            <th>Login</th>
            <th>Email</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Age</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="tableBody">
        </tbody>
    </table>
</script>

</body>
</html>
