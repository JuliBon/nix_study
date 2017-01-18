<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Admin page</title>

    <script src="${pageContext.request.contextPath}/resources/lib/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/underscore.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backbone.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/backbone/models.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/backbone/main.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/backbone/view/create-view.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/backbone/view/edit-view.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/backbone/router.js"></script>
    <script src="${pageContext.request.contextPath}/resources/lib/backbone-validation.js"></script>

    <link href="${pageContext.request.contextPath}/resources/css/admin-backbone.css" media="all" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet"
          type="text/css">
    <link href="${pageContext.request.contextPath}/resources/css/shared.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/css/user.css" rel="stylesheet" type="text/css">
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

    <div id="usersBlock" class="block">
        <h2>Users</h2>
        <div id="users">
        </div>
    </div>
    <div id="createUserBlock" class="block">
        <h2>Create user</h2>
        <div id="createUser">
        </div>
    </div>
    <div id="editUserBlock" class="block">
        <h2>Edit user</h2>
        <div id="editUser">
        </div>
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


<script type="text/template" id="userCreateEditTemplate">
    <div class="form-user">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Login</label>
            <div class="col-xs-10">
                <input name="login" class="form-control" placeholder="Login"/>
                <span class="help-block"></span>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Password</label>
            <div class="col-xs-10">
                <input name="password" type="password" class="form-control" placeholder="password"/>
                <span class="help-block"></span>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Confirm password</label>
            <div class="col-xs-10">
                <input name="passwordConfirm" type="password" class="form-control" placeholder="confirm password"/>
                <span class="help-block"></span>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Email</label>
            <div class="col-xs-10">
                <input name="email" class="form-control" placeholder="email"/>
                <span class="help-block"></span>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">First name</label>
            <div class="col-xs-10">
                <input name="firstName" class="form-control" placeholder="first name"/>
                <span class="help-block"></span>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Last name</label>
            <div class="col-xs-10">
                <input name="lastName" class="form-control" placeholder="last name"/>
                <span class="help-block"></span>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Birhday</label>
            <div class="col-xs-10">
                <input name="birthday" type="date" class="form-control" placeholder="birthday"/>
                <span class="help-block"></span>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Role</label>
            <div class="col-xs-10">
                <select name="role" title="Role" class="form-control">
                    <option value='{"id":2,"name":"USER"}'>USER</option>
                    <option value='{"id":1,"name":"ADMIN"}'>ADMIN</option>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <div class="btns-center">
                <button class="btn btn-primary" id="btnOk">Ok</button>
                <button class="btn btn-primary" onclick="location.href = '#!/';" id="btnCancel">Cancel</button>
            </div>
        </div>
    </div>
</script>

</body>
</html>
