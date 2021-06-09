<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>

<head>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<title><fmt:message key="login_jsp.title.header"/></title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container shadow-lg p-4 mb-4 bg-white">
		<h1>
			<fmt:message key="login_jsp.button.login" />
		</h1>
		<br />
		<form method="post" action="${pageContext.request.contextPath}/login"
			class="needs-validation" novalidate>
			<div class="form-group">
				<label for="login"><fmt:message key="login_jsp.label.login" /></label><br />
				<input type="text" class="form-control" id="login"
					placeholder="Enter username" name="login" required><br />
				<div class="valid-feedback"><fmt:message key="login_jsp.text.validation" /></div>
				<div class="invalid-feedback"><fmt:message key="Please fill out this field" /></div>
			</div>
			<div class="form-group">
				<label for="password"><fmt:message
						key="login_jsp.label.password" /></label><br /> <input type="password"
					name="pass" class="form-control" id="pass"
					placeholder="Enter password" required><br /> <br />
				<div class="valid-feedback"><fmt:message key="login_jsp.text.validation" /></div>
				<div class="invalid-feedback"><fmt:message key="Please fill out this field" /></div>
			</div>
			<button class="btn btn-primary" type="submit">
				<fmt:message key="login_jsp.button.login" />
			</button>


		</form>
		<br />
		<p class="bg-danger text-white">${errorMessage}</p>
	</div>
	<script>
		// Disable form submissions if there are invalid fields
		(function() {
			'use strict';
			window.addEventListener('load',
					function() {
						// Get the forms we want to add validation styles to
						var forms = document
								.getElementsByClassName('needs-validation');
						// Loop over them and prevent submission
						var validation = Array.prototype.filter.call(forms,
								function(form) {
									form.addEventListener('submit', function(
											event) {
										if (form.checkValidity() === false) {
											event.preventDefault();
											event.stopPropagation();
										}
										form.classList.add('was-validated');
									}, false);
								});
					}, false);
		})();
	</script>
</body>
</html>