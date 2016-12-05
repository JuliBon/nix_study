<%@ page import="com.nixsolutions.bondarenko.study.jsp.servlets.AdminServlet" %>
<%@ page import="com.nixsolutions.bondarenko.study.jsp.user.library.User" %>
<%@ page import="com.nixsolutions.bondarenko.study.jsp.UserFieldPattern" %>
<%@ page import="java.util.Map" %>
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

    <script type="text/javascript">
        function validateForm() {
            var form = document.forms["userForm"];
            var password = form["password"].value;
            var passwordConfirm = form["passwordConfirm"].value;
            if (password != passwordConfirm) {
                var confPassMassage = document.getElementById("confirmPasswordErrorMessage");
                confPassMassage.innerHTML = "Passwords do not match!";
                form["passwordConfirm"].focus();
                return false;
            }
        }
    </script>
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

    <c:if test="${message != null}">
        <div class="message">${message}</div>
    </c:if>

    <form action="admin" class="form-user" method="post" id="userForm"
          onsubmit="return validateForm()">
        <input type="hidden" name="action"
               value="${action}">
        <input type="hidden" name="id" value="${userDto.id}">
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Login</label>
            <div class="col-xs-10">
                <input name="login" type="text" class="form-control" placeholder="Login"
                       value="${userDto.login}" required
                       pattern="${userFieldPatternMap.get("login").getPattern()}"
                       title="${userFieldPatternMap.get("login").getValidateTitle()}"

                       <c:if test="${!isCreate}">readonly="readonly"</c:if>  />
                <div class="fieldError">${errorMap.get("login")}</div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Password</label>
            <div class="col-xs-10">
                <input name="password" type="password" class="form-control" placeholder="password"
                       required
                       pattern="${userFieldPatternMap.get("password").getPattern()}"
                       title="${userFieldPatternMap.get("password").getValidateTitle()}"
                />
                <div class="fieldError">${errorMap.get("password")}</div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Confirm password</label>
            <div class="col-xs-10">
                <input name="passwordConfirm" type="password" class="form-control" placeholder="confirm password"
                       required
                       pattern="${userFieldPatternMap.get("password").getPattern()}"
                       title="${userFieldPatternMap.get("password").getValidateTitle()}r"
                />
                <div class="fieldError" id="confirmPasswordErrorMessage"></div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Email</label>
            <div class="col-xs-10">
                <input name="email" type="email" class="form-control" placeholder="email"
                       value="${userDto.email}" required
                       pattern="${userFieldPatternMap.get("email").getPattern()}"
                />
                <div class="fieldError">${errorMap.get("email")}</div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">First name</label>
            <div class="col-xs-10">
                <input name="firstName" type="text" class="form-control" placeholder="first name"
                       value="${userDto.firstName}" required
                       pattern="${userFieldPatternMap.get("firstName").getPattern()}"
                       title="${userFieldPatternMap.get("firstName").getValidateTitle()}"
                />
                <div class="fieldError">${errorMap.get("firstName")}</div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Last name</label>
            <div class="col-xs-10">
                <input name="lastName" type="text" class="form-control" placeholder="last name"
                       value="${userDto.lastName}" required
                       pattern="${userFieldPatternMap.get("lastName").getPattern()}"
                       title="${userFieldPatternMap.get("lastName").getValidateTitle()}"
                />
                <div class="fieldError">${errorMap.get("lastName")}</div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Birhday</label>
            <div class="col-xs-10">
                <input name="birthday" type="date" class="form-control" placeholder="birthday"
                       value="${userDto.birthday}" required
                pattern="${userFieldPatternMap.get("birthday").getPattern()}"
                title="${userFieldPatternMap.get("birthday").getValidateTitle()}"
                />
                <div class="fieldError">${errorMap.get("birthday")}</div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Role</label>
            <div class="col-xs-10">
                <ex:RoleDropDownSelect roleList="${roleList}" styleClass="form-control"
                                       selectedRoleName="${userDto.roleName}"/>
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
