package ua.svinkov.controller.Command;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.model.dao.DaoFactory;
import ua.svinkov.model.dao.impl.JDBCDaoFactory;
import ua.svinkov.model.dao.impl.JDBCUserCoursesDao;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.UserCourses;
import ua.svinkov.service.CoursesService;

public class GetActiveCoursesCommand implements Command {

	private static final Logger log = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String forward = "/WEB-INF/user/userbasis.jsp";

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			forward = "/WEB-INF/error.jsp";
			return forward;
		}

		List<UserCourses> courses = new CoursesService().findUserCourses(user);

		log.trace("Found in DB: user --> " + user);

		List<UserCourses> coursesNotStarted = courses.stream()
				.filter(t -> LocalDate.now().isBefore(t.getCourse().getDateStart())).collect(Collectors.toList());
		log.trace("Found in DB: coursesNotStarted --> " + coursesNotStarted);
		
		List<UserCourses> coursesActive = courses.stream()
				.filter(t -> LocalDate.now().isAfter(t.getCourse().getDateStart())
						&& LocalDate.now().isBefore(t.getCourse().getDateEnd()))
				.collect(Collectors.toList());
		log.trace("Found in DB: coursesActive --> " + coursesActive);
		
		List<UserCourses> coursesEnded = courses.stream()
				.filter(t -> LocalDate.now().isAfter(t.getCourse().getDateEnd())).collect(Collectors.toList());
		log.trace("Found in DB: coursesEnded --> " + coursesEnded);

		session.setAttribute("userCoursesNotStarted", coursesNotStarted);
		session.setAttribute("userCoursesActive", coursesActive);
		session.setAttribute("userCoursesEnded", coursesEnded);
		return forward;
	}

}
