<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Add new user</title>
    <meta charset="utf-8">

    <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>

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
        <div class="adminLogout">Admin ${userName}
            <a href="/logout">(logout)</a>
        </div>
    </c:if>
    <h3>
        <c:set var="cancelRef" value="${contextPath}/admin" scope="page"/>

        <c:set var="isEdit" value="false" scope="page"/>
        <c:set var="isCreate" value="false" scope="page"/>
        <c:set var="isRegister" value="false" scope="page"/>
        <c:choose>
            <c:when test="${action.equals(\"create_user\")}">
                <c:set var="isCreate" value="true" scope="page"/>
                <c:set var="formAction" value="/admin/create" scope="page"/>
                Add user
            </c:when>
            <c:when test="${action.equals(\"edit_user\")}">
                <c:set var="isEdit" value="true" scope="page"/>
                <c:set var="readonly" value="true" scope="page"/>
                <c:set var="formAction" value="/admin/edit" scope="page"/>
                Edit user
            </c:when>
            <c:otherwise>
                <c:set var="isRegister" value="true" scope="page"/>
                <c:set var="formAction" value="/register" scope="page"/>
                <c:set var="cancelRef" value="${contextPath}/login" scope="page"/>
                Registration
            </c:otherwise>
        </c:choose>
    </h3>

    <c:if test="${message != null}">
        <div class="message">${message}</div>
    </c:if>

    <form action="${formAction}" class="form-user" method="post" id="userForm" onsubmit="return validateForm()">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <c:if test="${isEdit}">
            <forms:input type="hidden" path="user.id" value="${user.id}"/>
        </c:if>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Login</label>
            <div class="col-xs-10">
                <forms:input path="user.login" cssClass="form-control" title="Login" readonly="${readonly}"
                             value="${user.login}"/>
                <forms:errors path="user.login" cssClass="incorrect"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Password</label>
            <div class="col-xs-10">
                <forms:input path="user.password" cssClass="form-control" title="password" value="${user.password}"/>
                <forms:errors path="user.password" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Confirm password</label>
            <div class="col-xs-10">
                <forms:input path="user.passwordConfirm" cssClass="form-control" title="confirm password"
                             value="${user.passwordConfirm}"/>
                <forms:errors path="user.passwordConfirm" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Email</label>
            <div class="col-xs-10">
                <forms:input path="user.email" cssClass="form-control" title="email" value="${user.email}"/>
                <forms:errors path="user.email" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">First name</label>
            <div class="col-xs-10">
                <forms:input path="user.firstName" cssClass="form-control" title="first name"
                             value="${user.firstName}"/>
                <forms:errors path="user.firstName" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Last name</label>
            <div class="col-xs-10">
                <forms:input path="user.lastName" cssClass="form-control" title="last name" value="${user.lastName}"/>
                <forms:errors path="user.lastName" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2 col-form-label">Birhday</label>
            <div class="col-xs-10">
                <forms:input type="date" path="user.birthday" cssClass="form-control" title="birthday"
                             value="${user.birthday}"/>
                <forms:errors path="user.birthday" cssClass="incorrect"/>
            </div>
        </div>

        <c:if test="${isCreate || isEdit}">
            <div class="form-group row">
                <label class="col-xs-2 col-form-label">Role</label>
                <div class="col-xs-10">
                    <ex:RoleDropDownSelect roleNameList="${roleNameList}" styleClass="form-control"
                                           selectedRoleName="${user.roleName}"/>
                </div>
            </div>
        </c:if>

        <c:if test="${isRegister}">
            <div class="form-group row" style="margin: 0 auto; width: 300px;">
                <div class="g-recaptcha"
                     data-sitekey="6LdOMg4UAAAAAHr5SzMrguTatonrxpohXkE9OyKH">
                </div>
                <p class="incorrect">${captchaError}</p>
            </div>
            <div class="g-recaptcha" data-sitekey="6LdOMg4UAAAAAHr5SzMrguTatonrxpohXkE9OyKH"></div>
        </c:if>

        <div class="form-group row">
            <div class="btns-center">
                <button type="submit" class="btn btn-primary">Ok</button>
                <button type="button" onclick="location.href = '${cancelRef}';" class="btn btn-primary ">Cancel</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
