<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>


<html>
<head>
    <title>Admin home</title>
    <link href="css/shared.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <table>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td>${user.login}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.birthday}</td>
            </tr>
        </c:forEach>

    </table>

    <ex:UserLibrary count="${userList.size()}">
        <td>${userList.iterator().next().login}</td>
        <td>${userList.iterator().next().firstName}</td>
        <td>${userList.iterator().next().lastName}</td>
        <td>${userList.iterator().next().birthday}</td>
    </ex:UserLibrary>

    <div>Click <a href="/logout">here</a> to logout</div>
</div>
</body>
</html>