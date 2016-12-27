<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>User home</title>
    <link href="<s:url value="/resources/css/shared.css"/>" rel="stylesheet"/>
</head>
<body>
<div class="container hello-container">
    <h2>Hello, <s:property value="userName"/></h2>
    <div>Click <s:a href="/logout">here</s:a> to logout</div>
</div>
</body>
</html>