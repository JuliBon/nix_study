<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
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

    <form action="register" class="form-user" method="POST" id="userForm" onsubmit="return validateForm()">
        <div class="form-group row">
            <s:label for="user.login" class="col-xs-2 col-form-label" value="Login"/>
            <div class="col-xs-10">
                <s:textfield name="user.login" cssClass="form-control" title="Login"/>
                    <%-- value="${user.login}"--%>

                <s:fielderror fieldName="user.login" cssClass="incorrect"/>
            </div>
        </div>

        <div class="form-group row">
            <s:label for="user.password" class="col-xs-2 col-form-label" value="Password"/>
            <div class="col-xs-10">
                <s:password name="user.password" cssClass="form-control" title="password"/>
                    <%--value="${user.password}"--%>

                <s:fielderror fieldName="user.password" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <s:label for="passwordConfirm" class="col-xs-2 col-form-label" value="Confirm password"/>
            <div class="col-xs-10">
                <s:password name="passwordConfirm" cssClass="form-control" title="confirm password"/>
                    <%--value="${passwordConfirm}"--%>

                <s:fielderror fieldName="passwordConfirm" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <s:label for="user.email" class="col-xs-2 col-form-label" value="Email"/>
            <div class="col-xs-10">
                <s:textfield name="user.email" cssClass="form-control" title="email"/>
                    <%--value="${user.email}"--%>

                <s:fielderror fieldName="user.email" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <s:label for="user.firstName" class="col-xs-2 col-form-label" value="First name"/>
            <div class="col-xs-10">
                <s:textfield name="user.firstName" cssClass="form-control" title="first name"/>
                    <%--value="${user.firstName}"--%>

                <s:fielderror fieldName="user.firstName" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <s:label for="user.lastName" class="col-xs-2 col-form-label" value="Last name"/>
            <div class="col-xs-10">
                <s:textfield name="user.lastName" cssClass="form-control" title="last name"/>
                    <%--value="${user.lastName}"--%>

                <s:fielderror fieldName="user.lastName" cssClass="incorrect"/>
            </div>
        </div>
        <div class="form-group row">
            <s:label for="birthdayStr" class="col-xs-2 col-form-label" value="Birhday"/>
            <div class="col-xs-10">
                <s:textfield type="date" name="birthdayStr" cssClass="form-control" title="birthdayStr"/>
                    <%-- value="${birthdayStr}"--%>

                <s:fielderror fieldName="birthdayStr" cssClass="incorrect"/>
            </div>
        </div>

        <%--        <c:if test="${isRegister}">
                    <div class="form-group row" style="margin: 0 auto; width: 300px;">
                        <div class="g-recaptcha"
                             data-sitekey="6LcNyQ4UAAAAADeZmMXsnP_5JxZkN4MJvyQEjuCO">
                        </div>
                        <p class="incorrect">${captchaError}</p>
                    </div>
                    <div class="g-recaptcha" data-sitekey="6LcNyQ4UAAAAADeZmMXsnP_5JxZkN4MJvyQEjuCO"></div>
                </c:if>--%>

        <div class="form-group row">
            <div class="btns-center">
                <button type="submit" class="btn btn-primary">Ok</button>
                <button type="button" onclick="location.href = 'login';" class="btn btn-primary ">Cancel</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
