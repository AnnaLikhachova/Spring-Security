<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <title >Unvalid link</title>
</head>
<body>
<div class="container">
    <h1 class="alert alert-danger">${message}</h1>
    <br/>
    <a class="btn btn-default" href="/registration">Sign up </a>

    <div>
        <input type="hidden" id="token" value="${token}">
        <c:if test="${expired}">
            <br/>
            <h1><c:out value="${label.form.resendRegistrationToken}"> </c:out>resend</h1>
            <button onclick="tokenResend()">resend</button>
        </c:if>
    </div>
</div>
</body>
</html>
