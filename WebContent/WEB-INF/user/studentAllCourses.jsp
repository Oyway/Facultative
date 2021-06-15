<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<a class="nav-link" id="locales"
	href="${pageContext.request.contextPath}/allcourses?locales=en">en</a>
<a class="nav-link" id="locales"
	href="${pageContext.request.contextPath}/allcourses?locales=ru">ru</a>
<head>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<title><fmt:message key="student_all_courses.title.header" /></title>
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
		<form method="get" action="?command=allcourses">
			<input class="button" type="submit"
				value="<fmt:message key="student_all_courses.button.get_all_courses" />">
		</form>
		<form method="get">
			<select class="form-control" name="optionSort">
				<option value="CourseAz"><fmt:message
						key="student_all_courses.option.sort.course_az" /></option>
				<option value="CourseZa"><fmt:message
						key="student_all_courses.option.sort.course_za" /></option>
				<option value="Time"><fmt:message
						key="student_all_courses.option.sort.time" /></option>
				<option value="None"><fmt:message
						key="student_all_courses.option.sort.none" /></option>
			</select> <label for="topic"><fmt:message
					key="student_all_courses.lebel.topic" /></label><input type="text"
				name="topic"> <label for="teacher"><fmt:message
					key="student_all_courses.lebel.teacher_surname" /></label><input
				type="text" name="teacher"> <input class="button"
				type="submit" formmethod="post"
				formaction="${pageContext.request.contextPath}/allcourses?update"
				name="update"
				value="<fmt:message
					key="student_all_courses.button.update" />">
			<table class="table table-dark table-hover table-striped">
				<tr>
					<th><fmt:message
							key="student_all_courses.table.colname.course" /></th>
					<th><fmt:message key="student_all_courses.table.colname.topic" /></th>
					<th><fmt:message
							key="student_all_courses.table.colname.teacher" /></th>
					<th><fmt:message
							key="student_all_courses.table.colname.start_date" /></th>
					<th><fmt:message
							key="student_all_courses.table.colname.stop_date" /></th>
					<th><fmt:message key="student_all_courses.table.colname.descr" /></th>
					<th><fmt:message
							key="student_all_courses.table.colname.registration" /></th>
				</tr>
				<c:forEach items="${allCourses}" var="allcources" varStatus="status">
					<tr>
						<td>${allcources.course}</td>
						<td>${allcources.topic.topic}</td>
						<td>${allcources.teacher.firstName}
							${allcources.teacher.surname}</td>
						<td>${allcources.dateStart}</td>
						<td>${allcources.dateEnd}</td>
						<td>${allcources.description}</td>
						<td><input type="checkbox" name="option"
							value="${allcources.courseid}"></td>
					</tr>
				</c:forEach>
			</table>
			<input class="button" type="submit" formmethod="post"
				formaction="${pageContext.request.contextPath}/reg_course"
				name="reg"
				value="<fmt:message key="student_all_courses.button.registration" />">
		</form>
		<p class="bg-info text-white">${regMessage}</p>

		<form method="post" action="${pageContext.request.contextPath}/logout">
			<input type="hidden" name="command" value="logout" /> <input
				class="button" type="submit"
				value="<fmt:message key="student_all_courses.button.logout" />">
		</form>
	</div>
</body>
</html>