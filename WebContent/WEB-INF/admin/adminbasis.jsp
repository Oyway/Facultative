<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<a class="nav-link" id="locales"
	href="${pageContext.request.contextPath}/admin?locales=en">en</a>
<a class="nav-link" id="locales"
	href="${pageContext.request.contextPath}/admin?locales=ru">ru</a>

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

<title><fmt:message key="admin_basis_jsp.title.header" /></title>
</head>
<body>
	<div class="container">
		<form method="post">
			<main class="m-3">
				<div class="row col-md-6">
					<table class="table table-dark table-hover table-striped">
						<tr>
							<th><fmt:message key="admin_basis_jsp.table.colname.id" /></th>
							<th><fmt:message key="admin_basis_jsp.table.colname.course" /></th>
							<th><fmt:message key="admin_basis_jsp.table.colname.topic" /></th>
							<th><fmt:message key="admin_basis_jsp.table.colname.teacher" /></th>
							<th><fmt:message
									key="admin_basis_jsp.table.colname.start_date" /></th>
							<th><fmt:message
									key="admin_basis_jsp.table.colname.stop_date" /></th>
							<th><fmt:message key="admin_basis_jsp.table.colname.descr" /></th>
						</tr>
						<c:forEach items="${allCourses}" var="allcources"
							varStatus="status">
							<tr>
								<td>${allcources.courseid}</td>
								<td>${allcources.course}</td>
								<td>${allcources.topic.topic}</td>
								<td>${allcources.teacher.firstname}
									${allcources.teacher.surname}</td>
								<td>${allcources.dateStart}</td>
								<td>${allcources.dateEnd}</td>
								<td>${allcources.description}</td>
								<td><input type="submit" formmethod="post"
									class="btn btn-warning"
									formaction="${pageContext.request.contextPath}/courseedit?edit=${allcources.courseid}"
									name="edit"
									value="<fmt:message key="admin_basis_jsp.button.edit" />"></td>
								<td><input type="submit" formmethod="post"
									class="btn btn-danger"
									formaction="${pageContext.request.contextPath}/admin?delete=${allcources.courseid}"
									name="delete"
									value="<fmt:message key="admin_basis_jsp.button.delete" />"></td>
							</tr>
						</c:forEach>
						<td></td>
						<td><input type="text" name="course"></td>
						<td><select name="optionTopics"><c:forEach
									items="${allTopics}" var="alltopics" varStatus="status">
									<option value="${alltopics.topicId}">${alltopics.topic}</option>
								</c:forEach>
						</select></td>
						<td><select name="optionTeacher"><c:forEach
									items="${allTeachers}" var="allteachers" varStatus="status">
									<option value="${allteachers.userid}">${allteachers.userid}
										${allteachers.firstname} ${allteachers.surname}</option>
								</c:forEach>
						</select></td>
						<td><input id="dateStart" type="date" value="${currentDate}"
							min="${currentDate}" max="2022-12-31" name="dateStart"></td>
						<td><input type="date" value="${currentDate}"
							min="${dateStart} " max="2023-12-31" name="dateEnd"></td>
						<td><input type="text" name="description"></td>
						<td><input class="btn btn-success"
							formaction="${pageContext.request.contextPath}/addCourse"
							formmethod="post" type="submit"
							value="<fmt:message key="admin_basis_jsp.button.add" />"></td>
					</table>
				</div>

				<nav aria-label="Navigation for courses">
					<ul class="pagination justify-content-center">
						<c:if test="${currentPage != 1}">
							<li class="page-item"><a class="page-link"
								href="${pageContext.request.contextPath}/admin?page=${currentPage - 1}">Previous</a>
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
										href="${pageContext.request.contextPath}/admin?page=${i}">${i}</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:if test="${currentPage lt noOfPages}">
							<li class="page-item"><a class="page-link"
								href="${pageContext.request.contextPath}/admin?page=${currentPage + 1}">Next</a>
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