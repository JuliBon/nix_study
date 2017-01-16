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

<div id="usersApp">
    <div class="title">
        <h1>Users</h1>
    </div>

    <div class="content">
        <table id="users">
            <thead>
            <tr>
                <th>ID</th>
                <th>Login</th>
                <th>Email</th>
                <th>Password</th>
                <th>Password confirm</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Birthday</th>
                <th>Role</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="table-body"></tbody>
        </table>
    </div>
</div>

<script type="text/template" id="itemTemplate">
    <td><span class="user-id"></span></td>
    <td><input type="text" placeholder="login" class=".user-input user-login"></td>
    <td><input type="password" placeholder="password" class=".user-input user-password"></td>
    <td><input type="password" placeholder="confirm password" class=".user-input user-password-confirm"></td>
    <td><input type="email" placeholder="email" class=".user-input user-email"></td>
    <td><input type="text" placeholder="first name" class=".user-input user-first-name"></td>
    <td><input type="text" placeholder="last name" class=".user-input user-last-name"></td>
    <td><input type="text" placeholder="birthday" class=".user-input user-birthday"></td>
    <td>
        <select title="Role" class="user-role">
            <option value="1">ADMIN</option>
            <option value="2">USER</option>
        </select>
    </td>
    <td><button class="btn-delete">Delete</button></td>
</script>
</body>
</html>
