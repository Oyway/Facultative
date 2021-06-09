package ua.svinkov.controller.Command;

import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.User;
import ua.svinkov.service.CoursesService;

public class GetAllCoursesCommand implements Command {

	private static final Logger log = Logger.getLogger(GetAllCoursesCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String forward = "/WEB-INF/user/studentAllCourses.jsp";

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			forward = "/WEB-INF/error.jsp";
			return forward;
		}
		List<Course> courses = new CoursesService().findAll();
		if (Objects.nonNull(request.getQueryString()) && request.getQueryString().equals("update")) {
			String sortType = request.getParameter("optionSort");
			String sTopic = request.getParameter("topic");
			String sSurname = request.getParameter("teacher");
			if (Objects.nonNull(sTopic) && !sTopic.equals(""))
				courses = searchByTopic(courses, sTopic);
			if (Objects.nonNull(sSurname) && !sSurname.equals(""))
				courses = searchBySurname(courses, sSurname);

			courses = sort(courses, sortType);
		}
		log.trace("Found in DB: courses --> " + courses);

		session.setAttribute("allCourses", courses);
		return forward;
	}

	private List<Course> searchByTopic(List<Course> course, String searchTopic) {
		return course.stream().filter(t -> t.getTopic().getTopic().equals(searchTopic)).collect(Collectors.toList());
	}

	private List<Course> searchBySurname(List<Course> course, String searchSurname) {
		return course.stream().filter(t -> t.getTeacher().getSurname().equals(searchSurname))
				.collect(Collectors.toList());
	}

	private List<Course> sort(List<Course> course, String sortType) {
		switch (sortType) {
		case "CourseAz":
			course.sort((c1, c2) -> c1.getCourse().compareTo(c2.getCourse()));
			break;
		case "CourseZa":
			course.sort((c1, c2) -> c2.getCourse().compareTo(c1.getCourse()));
			break;
		case "Time":
			course.sort((c1, c2) -> Period.between(c1.getDateStart(), c1.getDateEnd()).getDays()
					- Period.between(c2.getDateStart(), c2.getDateEnd()).getDays());
			break;
		}
		return course;
	}

}
