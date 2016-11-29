<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.nixsolutions.bondarenko.study.jsp.user.library.Role" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="ex" uri="UserLibrary" %>

<%List<Role> roleList = (List<Role>) request.getAttribute("roleList");
pageContext.setAttribute("roleList", roleList);%>

<html>
<head>
    <title>Add new user</title>
</head>
<body>
Add user
<form action="create_user">
    <input name="login"/>
    <input name="password" type="password"/>
    <input name="passwor_confirm" type="password"/>
    <input type="email">
    <input name="first_name"/>
    <input name="last_name"/>
    <input name="birthday" type="date"/>
    <ex:RoleDropDownSelect roleList="${roleList}"></ex:RoleDropDownSelect>
    <button type="submit">Ok</button>
    <button onclick="window.location.reload();">Cancel</button>
</form>
</body>
</html>
