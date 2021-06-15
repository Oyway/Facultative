<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<a id="locales"
	href="${pageContext.request.contextPath}/users?locales=en">en</a>
<a id="locales"
	href="${pageContext.request.contextPath}/users?locales=ru">ru</a>
<head>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
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
	<div class="container">
		<form method="post"
			action="${pageContext.request.contextPath}/users?update=${users.userid}">
			<main class="m-3">
				<div class="row col-md-6">
					<table class="table table-dark table-hover table-striped">
						<tr>
							<th><fmt:message key="users_jsp.table.colname.id" /></th>
							<th><fmt:message key="users_jsp.table.colname.login" /></th>
							<th><fmt:message key="users_jsp.table.colname.email" /></th>
							<th><fmt:message key="users_jsp.table.colname.name" /></th>
							<th><fmt:message key="users_jsp.table.colname.surname" /></th>
							<th><fmt:message key="users_jsp.table.colname.role" /></th>
							<th><fmt:message key="users_jsp.table.colname.status" /></th>
						</tr>
						<c:forEach items="${allUsers}" var="users" varStatus="status">
							<tr>
								<td>${users.userid}</td>
								<td>${users.login}</td>
								<td>${users.email}</td>
								<td>${users.firstName}</td>
								<td>${users.surname}</td>
								<td>${users.role}</td>
								<td>${users.status}</td>

								<td><select name="optionRoles${users.userid}">
										<option value="TEACHER">TEACHER</option>
										<option value="STUDENT">STUDENT</option>
								</select></td>
								<td><input class="button" type="submit" formmethod="post"
									formaction="${pageContext.request.contextPath}/users?update=${users.userid}"
									name="update"
									value="<fmt:message key="users_jsp.button.update" />"></td>
								<td><c:choose>
										<c:when test="${users.status == true}">
											<input type="submit" formmethod="post"
												formaction="${pageContext.request.contextPath}/users?block=${users.userid}"
												name="status"
												value="<fmt:message key="users_jsp.button.block" />">
										</c:when>
										<c:when test="${users.status == false}">
											<input type="submit" formmethod="post"
												formaction="${pageContext.request.contextPath}/users?unblock=${users.userid}"
												name="status"
												value="<fmt:message key="users_jsp.button.unblock" />">
										</c:when>
									</c:choose></td>
							</tr>
						</c:forEach>
					</table>
				</div>

				<nav aria-label="Navigation for courses">
					<ul class="pagination justify-content-center">
						<c:if test="${currentPage != 1}">
							<li class="page-item"><a class="page-link"
								href="${pageContext.request.contextPath}/users?page=${currentPage - 1}">Previous</a>
							</li>
						</c:if>

						<c:forEach begin="1" end="${noOfPages}" var="i">
							<c:choose>
								<c:when test="${currentPage eq i}">
									<li class="page-item active"><a class="page-link">
											${i} <span class="sr-only">(current)</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link"
										href="${pageContext.request.contextPath}/users?page=${i}">${i}</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:if test="${currentPage lt noOfPages}">
							<li class="page-item"><a class="page-link"
								href="${pageContext.request.contextPath}/users?page=${currentPage + 1}">Next</a>
							</li>
						</c:if>
					</ul>
				</nav>
			</main>
		</form>
		<p class="bg-danger text-white">${errorMessage}</p>
	</div>
</body>
</html>