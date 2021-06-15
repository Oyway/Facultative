package ua.svinkov.controller.Command;

import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.enums.Role;
import ua.svinkov.service.CoursesService;

/**
 * Get all courses for student
 * 
 * @author R.Svinkov
 *
 */
public class GetAllCoursesCommand extends Command {

	private static final long serialVersionUID = 5510148241507031801L;
	private static final Logger log = Logger.getLogger(GetAllCoursesCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String forward = Path.PAGE_STUD_ALL_COURSES;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			if (user.getRole().equals(Role.ADMIN)) {
				forward = Path.REDIRECT + Path.PAGE_ADMIN;
			} else if (user.getRole().equals(Role.TEACHER)) {
				forward = Path.REDIRECT + Path.PAGE_ADMIN;
			} else if (user.getRole().equals(Role.STUDENT)) {
				forward = Path.REDIRECT + Path.PAGE_USER_BASIS;
			} else if (user.getRole().equals(Role.UNKNOWN)) {
				forward = Path.REDIRECT + Path.PAGE_ERROR;
			}
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

	/**
	 * Search list of course by topic
	 * 
	 * @param course      list of courses
	 * @param searchTopic name of topic
	 * @return list of courses with defined topic
	 */
	private List<Course> searchByTopic(List<Course> course, String searchTopic) {
		return course.stream().filter(t -> t.getTopic().getTopic().equals(searchTopic)).collect(Collectors.toList());
	}

	/**
	 * Search list of courses by teacher surname
	 * 
	 * @param course        list of courses
	 * @param searchSurname surname of teacher
	 * @return list of courses with defined teacher
	 */
	private List<Course> searchBySurname(List<Course> course, String searchSurname) {
		return course.stream().filter(t -> t.getTeacher().getSurname().equals(searchSurname))
				.collect(Collectors.toList());
	}

	/**
	 * Sort list of courses by chosen sort type
	 * 
	 * @param course   list of courses
	 * @param sortType sort type
	 * @return sorted list of courses
	 */
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
