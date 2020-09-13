<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta name="description" content="">
	<meta name="author" content="">

	<title>Create an account</title>
	<link href="${contextPath}/resources/css/thisapp.css" rel="stylesheet">

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
    <script src='https://www.google.com/recaptcha/api.js?l=en'></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</head>

<body>

<div class="container">

	<form:form method="POST" modelAttribute="userForm" class="form-signin">
		<h2 class="form-signin-heading">Create your account</h2>
		<spring:bind path="username">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:input type="text" path="username" class="form-control" placeholder="Username"
							autofocus="true"></form:input>
				<form:errors path="username"></form:errors>
			</div>
		</spring:bind>

		<spring:bind path="email">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:input type="text" path="email" class="form-control" placeholder="Email"></form:input>
				<form:errors path="email"></form:errors>
			</div>
		</spring:bind>

		<spring:bind path="password">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
				<form:errors path="password"></form:errors>
			</div>
		</spring:bind>

		<spring:bind path="passwordConfirm">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:input type="password" path="passwordConfirm" class="form-control"
							placeholder="Confirm your password"></form:input>
				<form:errors path="passwordConfirm"></form:errors>
			</div>
		</spring:bind>

        <div class="g-recaptcha col-sm-5"
             data-sitekey="${captchaSettings}" data-callback="onReCaptchaSuccess" data-expired-callback="onReCaptchaExpired"></div>
        <span id="captchaError" class="alert alert-danger col-sm-4"
              style="display: none"></span>

		<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
	</form:form>

</div>
</body>
</html>