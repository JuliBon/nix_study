<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User management</title>

    <script src="${pageContext.request.contextPath}/resources/lib/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/underscore.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backbone.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backbone-validation.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/models.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

    <link href="${pageContext.request.contextPath}/resources/css/admin_backbone.css" media="all" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value="/resources/lib/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">

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
                <th>Password</th>
                <th>Password confirm</th>
                <th>Email</th>
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
    <td class="form-group">
        <span class="user-id"></span>
    </td>
    <td class="form-group">
        <input type="text" name="login" placeholder="login" class="form-control">
        <span class="help-block"></span>
    </td>
    <td class="form-group">
        <input type="password" name="password" placeholder="password" class="form-control">
        <span class="help-block"></span>
    </td>
    <td class="form-group">
        <input type="password" name="passwordConfirm" placeholder="confirm password"
               class="form-control">
        <span class="help-block"></span>
    </td>
    <td class="form-group">
        <input type="email" name="email" placeholder="email" class="form-control">
        <span class="help-block"></span>
    </td>
    <td class="form-group">
        <input type="text" name="firstName" placeholder="first name" class="form-control">
        <span class="help-block"></span>
    </td>
    <td class="form-group">
        <input type="text" name="lastName" placeholder="last name" class="form-control">
        <span class="help-block"></span>
    </td>
    <td class="form-group">
        <input type="text" name="birthday" placeholder="birthday" class="form-control">
        <span class="help-block"></span>
    </td>
    <td class="form-group">
        <select name="role" title="Role" class="form-control">
            <option value="1">ADMIN</option>
            <option value="2">USER</option>
        </select>
        <span class="help-block"></span>
    </td>
    <td class="form-group">
        <button class="btn-delete">Delete</button>
    </td>
</script>
</body>
</html>
