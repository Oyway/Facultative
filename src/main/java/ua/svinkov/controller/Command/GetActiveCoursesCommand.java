package ua.svinkov.controller.Command;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.UserCourses;
import ua.svinkov.model.entity.enums.Role;
import ua.svinkov.service.CoursesService;

/**
 * Get active courses of user item
 * 
 * @author R.Svinkov
 *
 */
public class GetActiveCoursesCommand extends Command {

	private static final long serialVersionUID = 3465301228439569669L;

	private static final Logger log = Logger.getLogger(LoginCommand.class);
	
	private static final String ATTRIBUTE_USER = "user";

	@Override
	public String execute(HttpServletRequest request) {
		String forward = Path.PAGE_USER_BASIS;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(ATTRIBUTE_USER);

		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			if(user.getRole().equals(Role.ADMIN)) {
				forward = Path.REDIRECT + Path.PAGE_ADMIN;
			} else if(user.getRole().equals(Role.TEACHER)) {
				forward = Path.REDIRECT + Path.PAGE_ADMIN;
			} else if(user.getRole().equals(Role.STUDENT)) {
				forward = Path.REDIRECT + Path.PAGE_USER_BASIS;
			} else if(user.getRole().equals(Role.UNKNOWN)) {
				forward = Path.REDIRECT + Path.PAGE_ERROR;
			}
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
