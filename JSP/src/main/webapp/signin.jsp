<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    pageContext.setAttribute("errorMessage", errorMessage);
%>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Log in</title>

    <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/shared.css" rel="stylesheet">
    <link href="css/singin.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <form class="form-signin" action="signin" method="post">
        <h2 class="form-signin-heading">Please sing in</h2>
        <c:if test="${errorMessage != null}">
            <div class="errorMessage">${errorMessage}</div>
        </c:if>
        <label for="inputLogin" class="sr-only">Login: </label>
        <input name="login" id="inputLogin" class="form-control" placeholder="Login" required autofocus
               value="<c:if test="${login != null}">${login}</c:if>">
        <label for="inputPassword" class="sr-only">Password: </label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</div>
</body>
</html>