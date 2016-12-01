<%@ page import="com.nixsolutions.bondarenko.study.jsp.servlets.AdminServlet" %>
<%@ page import="com.nixsolutions.bondarenko.study.jsp.user.library.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>

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
    <div class="adminLogout">Admin ${currentUser.login}
        <a href="logout">(logout)</a></div>
    <h3>
        <c:choose>
            <c:when test="${action.equals(\"create_user\")}">
                <c:set var="isCreate" value="true" scope="page"/>
                Add user
            </c:when>
            <c:otherwise>
                <c:set var="isCreate" value="false" scope="page"/>
                Edit user
            </c:otherwise>
        </c:choose>
    </h3>

    <c:if test="${error_message != null}">
        <div class="message errorMessage" id="errorMessageLabel">${error_message}</div>
    </c:if>
    <c:if test="${message != null}">
        <div class="message">${message}</div>
    </c:if>
    <form action="admin" class="form-user" method="post" id="createUserForm"
          onsubmit="return checkPasswordConfirm()">
        <input type="hidden" name="action"
               value="${action}">
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Login</label>
            <div class="col-xs-10">
                <input name="login" id="login" type="text" class="form-control" placeholder="Login"
                       value="${user.login}" required
                       <c:if test="${!isCreate}">readonly="readonly"</c:if>  />
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Password</label>
            <div class="col-xs-10">
                <input name="password" id="password" type="password" class="form-control" placeholder="password"
                       required/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Confirm password</label>
            <div class="col-xs-10">
                <input id="confirmPassword" type="password" class="form-control" placeholder="confirm password"
                       required>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Email</label>
            <div class="col-xs-10">
                <input name="email" id="email" type="email" class="form-control" placeholder="email"
                       value="${user.email}"
                       required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">First name</label>
            <div class="col-xs-10">
                <input name="firstName" id="firstName" type="text" class="form-control" placeholder="first name"
                       value="${user.firstName}"
                       required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Last name</label>
            <div class="col-xs-10">
                <input name="lastName" id="lastName" type="text" class="form-control" placeholder="last name"
                       value="${user.lastName}"
                       required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Birhday</label>
            <div class="col-xs-10">
                <input name="birthday" id="birthday" type="date" class="form-control" placeholder="birthday"
                       value="${user.birthday}"
                       required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Role</label>
            <div class="col-xs-10">
                <ex:RoleDropDownSelect roleList="${roleList}" styleClass="form-control"
                                       selectedRoleName="${user.role.name}"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="btns-center">
                <button class="btn btn-primary" type="submit">Ok</button>
                <button onclick="location.href = '/admin';" class="btn btn-primary ">Cancel</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>

<script>
    function validateForm(form) {
        var login1 = document.getElementById("login").value;
        var password1 = document.getElementById("password").value;
        var confirmPassword1 = document.getElementById("confirmPassword").value;
        var email1 = document.getElementById("email").value;
        var firstName1 = document.getElementById("firstName").value;
        var lastName1 = document.getElementById("lastName").value;
        var birthday1 = document.getElementById("birthday").value;
        var roleName1 = document.getElementById("roleName").value;

        var login = form.getElementsByName("login").value;
        var password = form.getElementsByName("password").value;
        var confirmPassword = form.getElementsByName("confirmPassword").value;
        var email = form.getElementsByName("email").value;
        var firstName = form.getElementsByName("firstName").value;
        var lastName = form.getElementsByName("lastName").value;
        var birthday = form.getElementsByName("birthday").value;
        var roleName = form.getElementsByName("roleName").value;

        if (checkPasswordConfirm()) {
            return true;
        }
    }

    function checkPasswordConfirm() {
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirmPassword").value;
        var errorMessageLabel = document.getElementById("errorMessageLabel");
        if (password != confirmPassword) {
            errorMessageLabel.innerHTML = "Error! Passwords do not match!";
            return false;
        } else {
            return true;
        }
    }
</script>
