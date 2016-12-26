<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link href="<s:url value="/resources/css/shared.css"/>" rel="stylesheet"/>
</head>
<body>

<div class="container error-container">
    <%--<h2>Error ${status}</h2>--%>
    <p>Some error occurred while processing request</p>
    <br/>
    <a href="javascript:history.back()">Go back</a>
</div>
</body>
</html>


