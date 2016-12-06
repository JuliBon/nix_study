<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>

    <link href="css/shared.css" rel="stylesheet">
</head>
<body>

<div class="container errorContainer">
    <h1>Error! </h1>
    <br/>
    <p>${error.getMessage()}</p>
</div>

</body>
</html>


