<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>

<html>
<head>
    <sb:head includeScripts="true" includeScriptsValidation="true"/>

    <title>Register</title>
    <meta charset="utf-8">

    <link href="<s:url value="/resources/lib/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<s:url value="/resources/css/shared.css"/>" rel="stylesheet"/>
    <link href="<s:url value="/resources/css/user.css"/>" rel="stylesheet"/>

    <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
</head>
<body>

<div class="container">
    <div class="center-label"><h3>Create new user</h3></div>

    <s:form action="createUserPost" method="POST" id="userForm" validate="true"
            theme="bootstrap"
            cssClass="form-vertical, form-user"
            labelCssClass="col-sm-2"
            elementCssClass="col-sm-7">

        <s:textfield name="userModel.user.login" cssClass="form-control" label="Login"/>
        <s:password name="userModel.user.password" cssClass="form-control" label="Password"/>
        <s:password name="userModel.passwordConfirm" cssClass="form-control" label="Confirm password"/>
        <s:textfield name="userModel.user.email" cssClass="form-control" label="Email"/>
        <s:textfield name="userModel.user.firstName" cssClass="form-control" label="First name"/>
        <s:textfield name="userModel.user.lastName" cssClass="form-control" label="Last name"/>
        <s:textfield name="userModel.birthdayStr" type="date" cssClass="form-control" label="Birthday"/>
        <s:select name="userModel.roleName" list="roleNameList" value="defaultRoleName" cssClass="form-control"/>

        <div class="btns-center">
            <button type="submit" class="btn btn-primary">Ok</button>
            <button type="button" onclick="location.href = 'login';" class="btn btn-primary ">Cancel</button>
        </div>
    </s:form>
</div>
</body>
</html>
