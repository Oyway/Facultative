<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<a id="locales"
	href="${pageContext.request.contextPath}/reg?locales=en">en</a>
<a id="locales"
	href="${pageContext.request.contextPath}/reg?locales=ru">ru</a>
<head>
<title><fmt:message key="reg_jsp.title.header" /></title>
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
			<fmt:message key="reg_jsp.title.header" />
		</h1>
		<br />
		<form method="post" action="${pageContext.request.contextPath}/reg">
			<label for="login"><fmt:message key="reg_jsp.label.login" /></label><br />
			<input type="text" name="login"><br /> <label for="pass"><fmt:message
					key="reg_jsp.label.password" /></label><br /> <input type="password"
				name="pass"><br /> <br /> <label for="email"><fmt:message
					key="reg_jsp.label.email" /></label><br /> <input type="text" name="email"><br />
			<label for="firstname"><fmt:message key="reg_jsp.lebel.name" /></label><br />
			<input type="text" name="firstname"><br /> <label
				for="surname"><fmt:message key="reg_jsp.label.surname" /></label><br />
			<input type="text" name="surname"><br /> <input
				class="button" type="submit"
				value="<fmt:message key="reg_jsp.button.registr"/>">

		</form>
		<p>${errorMessage}</p>
		<br /> <a href="${pageContext.request.contextPath}/logout"><fmt:message
				key="reg_jsp.href.login" /></a>
	</div>
</body>
</html>