<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<title>reset</title>
</head>
<body>
<div class="container">
    <h1>Reset password</h1>
    <br/>
    <div class="form-group ${error != null ? 'has-error' : ''}">
        <span>${message}</span>
    </div>
    <div class="row">
      <form action="/forgetPassword" method="post">
        <label class="col-sm-1">Enter your email</label>
        <span class="col-sm-5"><input class="form-control" id="email" name="email" type="email" value="" required="required" /></span>
          <button class="btn btn-primary" type="submit">reset</button>
      </form>
    </div>

<br/> 
<a class="btn btn-default" href="${contextPath}/registration">registration</a>
<br/><br/>
<a class="btn btn-default" href="${contextPath}/login">login</a>
</div>
</body>
</html>

