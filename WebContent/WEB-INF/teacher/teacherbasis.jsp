<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<title><fmt:message key="teacherbasis_jsp.title.header" /></title>
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
		<main class="m-3">
			<form method="get">
				<select name="optionSort">
					<option value="StudCount Decs"><fmt:message
							key="teacherbasis_jsp.option.sort.desc" /></option>
					<option value="StudCount Asc"><fmt:message
							key="teacherbasis_jsp.option.sort.asc" /></option>
				</select> <input class="button" type="submit" formmethod="post"
					formaction="${pageContext.request.contextPath}/teacher?update=${optionSort}"
					name="update"
					value="<fmt:message key="teacherbasis_jsp.button.update"/>">
			</form>
			<div class="row col-md-6">

				<table class="table table-striped table-bordered table-sm">
					<thead>${courseName}</thead>
					<tr>
						<th><fmt:message key="teacherbasis_jsp.table.colname.id" /></th>
						<th><fmt:message key="teacherbasis_jsp.table.colname.name" /></th>
						<th><fmt:message key="teacherbasis_jsp.table.colname.mark" /></th>
						<th><fmt:message
								key="teacherbasis_jsp.table.colname.change_mark" /></th>
					</tr>

					<c:forEach items="${students}" var="student">
						<tr>
							<td>${student.user.userid}</td>
							<td>${student.user.firstName}${student.user.surname}</td>
							<td>${student.mark}</td>
							<td><form method="post" action="${pageContext.request.contextPath}/setMark?page=${currentPage}">
									<input type="hidden" name="command" value="setMark" /> <input
										type="hidden" name="studentid" value="${student.user.userid}" />
									<input type="hidden" name="courseid"
										value="${student.course.courseid}" /> <input type="number"
										name="mark" size="3" min="0" max="100" value="0" /><input
										class="button" type="submit" value="<fmt:message key="teacherbasis_jsp.button.change" />">
								</form></td>
						</tr>
					</c:forEach>
				</table>
			</div>

			<nav aria-label="Navigation for courses">
				<ul class="pagination" style="margin:20px 0">
					<c:if test="${currentPage != 1}">
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath}/teacher?page=${currentPage - 1}">	Previous	</a>
						</li>
					</c:if>

					<c:forEach begin="1" end="${noOfPages}" var="i">
						<c:choose>
							<c:when test="${currentPage eq i}">
								<li class="page-item active"><a class="page-link">	${i}
										<span class="sr-only"> (current) </span>
								</a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item"><a class="page-link"
									href="${pageContext.request.contextPath}/teacher?page=${i}">${i}</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<c:if test="${currentPage lt noOfPages}">
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath}/teacher?page=${currentPage + 1}">	Next</a>
						</li>
					</c:if>
				</ul>
			</nav>
		</main>
		<form method="post" action="${pageContext.request.contextPath}/logout">
			<input type="hidden" name="command" value="logout" /> <input
				class="button" type="submit" value="<fmt:message key="teacherbasis_jsp.button.logout" />">
		</form>
	</div>
</body>
</html>