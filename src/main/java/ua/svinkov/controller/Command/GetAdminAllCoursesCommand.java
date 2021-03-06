package ua.svinkov.controller.Command;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.Topic;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.enums.Role;
import ua.svinkov.service.CoursesService;
import ua.svinkov.service.UserService;

/**
 * Get all existed courses for admin
 * 
 * @author R.Svinkov
 *
 */
public class GetAdminAllCoursesCommand extends Command {

	private static final long serialVersionUID = -3634465138162165674L;

	private static final Logger log = Logger.getLogger(GetAdminAllCoursesCommand.class);

	private static final String PARAM_PAGE = "page";
	private static final String PARAM_DELETE = "delete";
	private static final String ATTRIBUTE_USER = "user";

	@Override
	public String execute(HttpServletRequest request) {
		String forward = Path.PAGE_ADMIN_BASIS;

		CoursesService courseService = new CoursesService();
		UserService userService = new UserService();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(ATTRIBUTE_USER);

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

		if (Objects.nonNull(request.getParameter(PARAM_DELETE))) {
			Long courseId = Long.parseLong(request.getParameter(PARAM_DELETE));
			courseService.deleteCourse(courseId);
		}

		int tempPage = 1;
		int recordsPerPage = 3;
		if (Objects.nonNull(request.getParameter(PARAM_PAGE)))
			tempPage = Integer.parseInt(request.getParameter(PARAM_PAGE));
		int page = tempPage;

		List<Course> courses = courseService.findAllLimit((page - 1) * recordsPerPage, page * recordsPerPage);
		List<Topic> topics = courseService.findAllTopics();
		List<User> teachers = userService.findAllTeachers();

		Integer rowsTottal = courseService.findAllCount();
		log.trace("Found in DB: rowsTottal --> " + rowsTottal);
		log.trace("Found in DB: courses --> " + courses);
		Integer pageTotale = (int) Math.ceil(rowsTottal * 1.0 / recordsPerPage);
		log.trace("Found in DB: user --> " + user);

		log.trace("Found in DB: courses --> " + courses);
		log.trace("Found in courses: pageTotale --> " + pageTotale);

		session.setAttribute("allCourses", courses);
		session.setAttribute("allTopics", topics);
		session.setAttribute("allTeachers", teachers);
		request.setAttribute("noOfPages", pageTotale);
		request.setAttribute("currentPage", page);

		return forward;
	}

}
