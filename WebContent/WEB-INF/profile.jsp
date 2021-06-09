<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<a class="nav-link" id="locales"
	href="${pageContext.request.contextPath}/profile?locales=en">en</a>
<a class="nav-link" id="locales"
	href="${pageContext.request.contextPath}/profile?locales=ru">ru</a>
<head>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container shadow-lg p-4 mb-4 bg-white">
        <h1><fmt:message key="profile_jsp.title.header" /></h1><br/>
        <form>
			<label for="login"><fmt:message key="profile_jsp.lable.login" />: </label> <output name="login">${user.login}</output><br/>
            <label for="email"><fmt:message key="profile_jsp.lable.email" />: </label> <output name="email">${user.email}</output><br/>
            <label for="firstname"><fmt:message key="profile_jsp.lable.name" />: </label> <output name="firstname">${user.firstname}</output><br/>
            <label for="surname"><fmt:message key="profile_jsp.lable.surname" />: </label> <output name="surname">${user.surname}</output><br/>
        </form>
        <p class="bg-danger text-white">${errorMessage}</p>
        <br/>
        <a href="${pageContext.request.contextPath}/logout"><fmt:message key="profile_jsp.button.logout" /></a>
</div>
</body>
</html>