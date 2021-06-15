<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<a id="locales"
	href="${pageContext.request.contextPath}/teacher?locales=en">en</a>
<a id="locales"
	href="${pageContext.request.contextPath}/teacher?locales=ru">ru</a>
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
		<h1>
			<fmt:message key="courseedit_jsp.title.header" />
		</h1>
		<br />
		<form method="post"
			action="${pageContext.request.contextPath}/courseedit?edit">
			<input type="hidden" name="courseId" value="${courseId}"> <label
				for="courseName"><fmt:message
					key="courseedit_jsp.lable.course" /></label><br /> <input type="text"
				name="courseName" value="${courseName}"><br /> <label
				for="optionTopics"><fmt:message
					key="courseedit_jsp.lable.topic" /></label><br /> <select
				class="form-control" name="optionTopics"><c:forEach
					items="${allTopics}" var="alltopics" varStatus="status">
					<option value="${alltopics.topicId}">${alltopics.topic}</option>
				</c:forEach>
			</select><br /> <label for="optionTeacher"><fmt:message
					key="courseedit_jsp.lable.teacher" /></label><br /> <select
				class="form-control" name="optionTeacher"><c:forEach
					items="${allTeachers}" var="allteachers" varStatus="status">
					<option value="${allteachers.userid}">${allteachers.userid}
						${allteachers.firstName} ${allteachers.surname}</option>
				</c:forEach>
			</select><br /> <label for="dateStart"><fmt:message
					key="courseedit_jsp.lable.date_start" /></label><br /> <input
				id="dateStart" type="date" name="dateStart" value="${dateStart}"
				min="${currentDate}" max="2022-12-31" name="datestart"><br />
			<label for="dateEnd"><fmt:message
					key="courseedit_jsp.lable.date_end" /></label><br /> <input type="date"
				name="dateEnd" min="${dateStart}" value="${dateEnd}"
				max="2023-12-31" name="dateend"><br /> <label for="description"><fmt:message
					key="courseedit_jsp.lable.descr" /></label><br /> <input type="text"
				name="description" value="${description}" max="200"><br /> <input
				class="button" type="submit"
				value="<fmt:message key="courseedit_jsp.button.descr" />">

		</form>
		<p class="bg-danger text-white">${errorMessage}</p>
	</div>
</body>
</html>