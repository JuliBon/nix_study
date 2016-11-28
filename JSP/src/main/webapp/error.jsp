<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("error", request.getAttribute("error"));
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Error! ${error}</h2>
    <div></div>
</body>
</html>
