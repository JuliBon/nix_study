<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Log in</title>

    <link href="<c:url value="/resources/lib/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/shared.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/singin.css"/>" rel="stylesheet" type="text/css">
</head>

<body>
<div class="container">
    <form:form action="/login" method="post" class="form-signin" modelAttribute="user">
        <div class="centerLabel"><h2 class="form-signin-heading">Please sing in</h2></div>
        <c:if test="${errorMessage != null}">
            <div class="fieldError">${errorMessage}</div>
        </c:if>
        <label for="inputLogin" class="sr-only">Login: </label>
        <input name="login" id="inputLogin" class="form-control" placeholder="Login" required autofocus
               value="${user.login}">
        <label for="inputPassword" class="sr-only">Password: </label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
        <div class="centerLabel"><a href="/register"><h5>Register</h5></a></div>
    </form:form>
</div>
</body>
</html>