<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
    <title >Error</title>
</head>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<h2>Error message</h2>
<div class="form-group ${expired != null ? '"expired".equals(result)' : ''}">
    <span>${message}</span>
</div>
<div class="navigation-panel-link"><a href="login" class="txt1">login</a> </div>
</body>
</html>
