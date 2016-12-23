<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <s:head/>
    <title>Register</title>
    <meta charset="utf-8">

    <link href="<s:url value="/resources/lib/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<s:url value="/resources/css/shared.css"/>" rel="stylesheet"/>
    <link href="<s:url value="/resources/css/user.css"/>" rel="stylesheet"/>

    <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>

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
    <%--    <c:if test="${message != null}">
            <div class="message">${message}</div>
        </c:if>--%>

    <h3>Registration</h3>

    <s:form  action="registerUser" method="POST" id="userForm" validate="true"
            onsubmit="return validateForm()">
        <s:actionerror/>

        <s:textfield name="userModel.user.login" cssClass="form-group row form-control" label="Login" />

        <s:password name="userModel.user.password" cssClass="form-control" label="Password"/>

        <s:password name="userModel.passwordConfirm" cssClass="form-control" label="Confirm password"/>

        <s:textfield name="userModel.user.email" cssClass="form-control" label="Email"/>

        <s:textfield name="userModel.user.firstName" cssClass="form-control" label="First name"/>

        <s:textfield name="userModel.user.lastName" cssClass="form-control" label="Last name"/>

        <s:textfield name="userModel.birthdayStr" type="date" cssClass="form-control" label="Birthday"/>

        <%--        <c:if test="${isRegister}">
                    <div class="form-group row" style="margin: 0 auto; width: 300px;">
                        <div class="g-recaptcha"
                             data-sitekey="6LcNyQ4UAAAAADeZmMXsnP_5JxZkN4MJvyQEjuCO">
                        </div>
                        <p class="incorrect">${captchaError}</p>
                    </div>
                    <div class="g-recaptcha" data-sitekey="6LcNyQ4UAAAAADeZmMXsnP_5JxZkN4MJvyQEjuCO"></div>
                </c:if>--%>

        <s:submit class="btn btn-primary" value="Ok"/>
    </s:form>
        <s:a href='login' class="btn btn-primary ">Cancel</s:a>
</div>
</body>
</html>
