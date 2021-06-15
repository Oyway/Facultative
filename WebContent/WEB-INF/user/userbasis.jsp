<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<a id="locales"
	href="${pageContext.request.contextPath}/student?locales=en">en</a>
<a id="locales"
	href="${pageContext.request.contextPath}/student?locales=ru">ru</a>
<head>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<title><fmt:message key="student_courses.title.header" /></title>
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
		<form method="get" action="?command=actcourses">
			<input class="button" type="submit" value="Get my courses">
		</form>
		<table class="table table-dark table-hover table-striped">
			<caption>
				<fmt:message key="student_courses.table.caption.not_active" />
			</caption>
			<tr>
				<th><fmt:message key="student_courses.table.colname.course" /></th>
				<th><fmt:message key="student_courses.table.colname.topic" /></th>
				<th><fmt:message key="student_courses.table.colname.teacher" /></th>
				<th><fmt:message key="student_courses.table.colname.start_date" /></th>
				<th><fmt:message key="student_courses.table.colname.stop_date" /></th>
				<th><fmt:message key="student_courses.table.colname.descr" /></th>
				<th><fmt:message key="student_courses.table.colname.mark" /></th>
			</tr>
			<c:forEach items="${userCoursesNotStarted}" var="cources"
				varStatus="status">
				<tr>
					<td>${cources.course.course}</td>
					<td>${cources.course.topic.topic}</td>
					<td>${cources.course.teacher.firstName}
						${cources.course.teacher.surname}</td>
					<td>${cources.course.dateStart}</td>
					<td>${cources.course.dateEnd}</td>
					<td>${cources.course.description}</td>
					<td>${cources.mark}</td>
				</tr>
			</c:forEach>
		</table>
		<table class="table table-dark table-hover table-striped">
			<caption>
				<fmt:message key="student_courses.table.caption.active" />
			</caption>
			<tr>
				<th><fmt:message key="student_courses.table.colname.course" /></th>
				<th><fmt:message key="student_courses.table.colname.topic" /></th>
				<th><fmt:message key="student_courses.table.colname.teacher" /></th>
				<th><fmt:message key="student_courses.table.colname.start_date" /></th>
				<th><fmt:message key="student_courses.table.colname.stop_date" /></th>
				<th><fmt:message key="student_courses.table.colname.descr" /></th>
				<th><fmt:message key="student_courses.table.colname.mark" /></th>
			</tr>
			<c:forEach items="${userCoursesActive}" var="cources"
				varStatus="status">
				<tr>
					<td>${cources.course.course}</td>
					<td>${cources.course.topic.topic}</td>
					<td>${cources.course.teacher.firstName}
						${cources.course.teacher.surname}</td>
					<td>${cources.course.dateStart}</td>
					<td>${cources.course.dateEnd}</td>
					<td>${cources.course.description}</td>
					<td>${cources.mark}</td>
				</tr>
			</c:forEach>
		</table>
		<table class="table table-dark table-hover table-striped">
			<caption>
				<fmt:message key="student_courses.table.caption.ended" />
			</caption>
			<tr>
				<th><fmt:message key="student_courses.table.colname.course" /></th>
				<th><fmt:message key="student_courses.table.colname.topic" /></th>
				<th><fmt:message key="student_courses.table.colname.teacher" /></th>
				<th><fmt:message key="student_courses.table.colname.start_date" /></th>
				<th><fmt:message key="student_courses.table.colname.stop_date" /></th>
				<th><fmt:message key="student_courses.table.colname.descr" /></th>
				<th><fmt:message key="student_courses.table.colname.mark" /></th>
			</tr>
			<c:forEach items="${userCoursesEnded}" var="cources"
				varStatus="status">
				<tr>
					<td>${cources.course.course}</td>
					<td>${cources.course.topic.topic}</td>
					<td>${cources.course.teacher.firstName}
						${cources.course.teacher.surname}</td>
					<td>${cources.course.dateStart}</td>
					<td>${cources.course.dateEnd}</td>
					<td>${cources.course.description}</td>
					<td>${cources.mark}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>