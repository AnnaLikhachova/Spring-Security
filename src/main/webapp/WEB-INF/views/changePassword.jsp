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
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#" >home</a>
    </div>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="${contextPath}\logout" >logout</a> </li>
      </ul>
    </div>
</nav>
    <div class="container">
        <div class="row">
            <div class="form-group ${error != null ? 'has-error' : ''}">
                <span>${message}</span>
            </div>
        <div id="errormsg" class="alert alert-danger" style="display:none"></div>
            <h1 >change password </h1>
            <form:form method="POST" modelAttribute="passwordDto" >
                <br/>
                
                    <label class="col-sm-2" >old</label>
                    <span class="col-sm-5"><form:input class="form-control" path="oldPassword" type="password" value="" ></form:input></span>
                    <span class="col-sm-5"></span>
<br/><br/>
<spring:bind path="newPassword">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2" >new</label>
                    <span class="col-sm-5"><form:input class="form-control" path="newPassword" type="password" value=""></form:input></span>
                    <div class="col-sm-12"></div>
                    <form:errors path="newPassword"></form:errors>
    </spring:bind>
<br/><br/>
<spring:bind path="matchingPassword">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2" >confirm</label>
                    <div class="col-sm-5"><form:input class="form-control" path="matchingPassword" type="password" value=""></form:input></div>
                        <form:errors path="matchingPassword"></form:errors>
    </spring:bind>
                
                <div class="col-sm-12">
                <br/><br/>
                <button class="btn btn-primary" type="submit">change
                </button>
                </div>
            </form:form>
            
        </div>
    </div>
</body>
</html>

