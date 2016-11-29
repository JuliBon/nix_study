<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.nixsolutions.bondarenko.study.jsp.user.library.Role" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>

<%
    List<Role> roleList = (List<Role>) request.getAttribute("roleList");
    pageContext.setAttribute("roleList", roleList);
%>

<html>
<head>
    <title>Add new user</title>
    <meta charset="utf-8">

    <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/shared.css" rel="stylesheet">
    <link href="css/user.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <h3>Add user</h3>
    <form name="createUserForm" action="admin" class="form-user" method="post">
        <input type="hidden" name="action" value="create_user">
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Login</label>
            <div class="col-xs-10">
                <input name="login" type="text" class="form-control" placeholder="Login"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Password</label>
            <div class="col-xs-10">
                <input name="password" type="password" class="form-control" placeholder="password"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Confirm password</label>
            <div class="col-xs-10">
                <input type="password" class="form-control" placeholder="confirm password">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Email</label>
            <div class="col-xs-10">
                <input name="email" type="email" class="form-control" placeholder="email">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">First name</label>
            <div class="col-xs-10">
                <input name="first_name" type="text" class="form-control" placeholder="first name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Last name</label>
            <div class="col-xs-10">
                <input name="last_name" type="text" class="form-control" placeholder="last name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Birhday</label>
            <div class="col-xs-10">
                <input name="birthday" type="date" class="form-control" placeholder="birthday"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Role</label>
            <div class="col-xs-10">
                <ex:RoleDropDownSelect roleList="${roleList}" styleClass="form-control"></ex:RoleDropDownSelect>
            </div>
        </div>
        <div class="form-group row">
            <div class="btns-center">
                <button type="submit" class="btn btn-primary">Ok</button>
                <button onclick="window.location.reload();" class="btn btn-primary ">Cancel</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
