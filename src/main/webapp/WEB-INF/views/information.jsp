<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
    <title >Information</title>
</head>
<body>
<div class="container">
<h2 class="text-center">Check you email to proceed with registration</h2>
<div class="navigation-panel-link"><a href="${contextPath}/login" class="txt1">login</a> </div>
</div>
</body>
</html>
