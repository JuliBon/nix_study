<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Add new user</title>
    <meta charset="utf-8">

    <link href="<c:url value="/resources/lib/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/shared.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/user.css"/>" rel="stylesheet" type="text/css">

    <script type="text/javascript">
        function validateForm() {
            var form = document.getElementById("userForm");
            var password = document.getElementById("password").value;
            var passwordConfirm = document.getElementById("passwordConfirm").value;
            if (password != passwordConfirm) {
                var confPassMassage = document.getElementById("confirmPasswordErrorMessage");
                confPassMassage.innerHTML = "Passwords do not match!";
                document.getElementById("passwordConfirm").focus();
                return false;
            }
        }
    </script>
</head>
<body>

<div class="container">
    <%--TODO change to role checking--%>
    <c:if test="${action.equals(\"create_user\") || action.equals(\"edit_user\")}">
        <div class="adminLogout">Admin ${currentUser.login}
            <a href="/logout">(logout)</a>
        </div>
    </c:if>
    <h3>
        <c:set var="isEdit" value="false" scope="page"/>
        <c:set var="isRegister" value="false" scope="page"/>
        <c:choose>
            <c:when test="${action.equals(\"create_user\")}">
                <c:set var="formAction" value="/admin/create" scope="page"/>
                Add user
            </c:when>
            <c:when test="${action.equals(\"edit_user\")}">
                <c:set var="isEdit" value="true" scope="page"/>
                <c:set var="formAction" value="/admin/edit" scope="page"/>
                Edit user
            </c:when>
            <c:otherwise>
                <c:set var="isRegister" value="true" scope="page"/>
                <c:set var="formAction" value="/register" scope="page"/>
                Registration
            </c:otherwise>
        </c:choose>
    </h3>

    <c:if test="${message != null}">
        <div class="message">${message}</div>
    </c:if>

    <form action="${formAction}" class="form-user" method="post" id="userForm" onsubmit="return validateForm()">
        <input type="hidden" name="id" value="${user.id}">
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Login</label>
            <div class="col-xs-10">
                <input name="login" type="text" class="form-control" placeholder="Login"
                       value="${user.login}" required
                       pattern="${userFieldPatternMap.get("login").getPattern()}"
                       title="${userFieldPatternMap.get("login").getValidateTitle()}"

                       <c:if test="${isEdit}">readonly="readonly"</c:if> />
                <div class="fieldError">${errorMap.get("login")}</div>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Password</label>
            <div class="col-xs-10">
                <input name="password" type="password" id="password" class="form-control"
                       placeholder="password"
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
                <input name="passwordConfirm" type="password" id="passwordConfirm" class="form-control"
                       placeholder="confirm password"
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
                       value="${user.email}" required
                       pattern="${userFieldPatternMap.get("email").getPattern()}"
                />
                <div class="fieldError">${errorMap.get("email")}</div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">First name</label>
            <div class="col-xs-10">
                <input name="firstName" type="text" class="form-control" placeholder="first name"
                       value="${user.firstName}" required
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
                       value="${user.lastName}" required
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
                       value="${user.birthday}" required
                       pattern="${userFieldPatternMap.get("birthday").getPattern()}"
                       title="${userFieldPatternMap.get("birthday").getValidateTitle()}"
                />
                <div class="fieldError">${errorMap.get("birthday")}</div>
            </div>
        </div>

        <c:if test="${!isRegister}">
            <div class="form-group row">
                <label class="col-xs-2 col-form-label">Role</label>
                <div class="col-xs-10">
                    <ex:RoleDropDownSelect roleNameList="${roleNameList}" styleClass="form-control"
                                           selectedRoleName="${user.roleName}"/>
                </div>
            </div>
        </c:if>

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
