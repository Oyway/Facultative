package ua.svinkov.controller.Command;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.constants.Path;
import ua.svinkov.model.dao.DaoFactory;
import ua.svinkov.model.dao.impl.JDBCDaoFactory;
import ua.svinkov.model.dao.impl.JDBCUserCoursesDao;
import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.UserCourses;

/**
 * Registration on courses for student command
 * 
 * @author R.Svinkov
 *
 */
public class RegOnCoursesCommand extends Command {

	private static final long serialVersionUID = 916710903233248554L;
	private static final Logger log = Logger.getLogger(RegOnCoursesCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		StringBuilder regMessage = new StringBuilder();
		String forward = Path.REDIRECT + Path.PAGE_ALL_COURSES;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String[] id = request.getParameterValues("option");

		if (Objects.isNull(id))
			return forward;

		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			forward = Path.PAGE_LOGIN;
			return forward;
		}
		DaoFactory factory = JDBCDaoFactory.getInstance();
		JDBCUserCoursesDao dao = (JDBCUserCoursesDao) factory.createUserCoursesDao();
		for (int i = 0; i < id.length; i++) {
			UserCourses courses = new UserCourses();
			courses.setUser(user);
			courses.setCourse(Course.builder().courseid(Integer.parseInt(id[i])).build());
			if (!dao.findUserCourse(user.getUserid(), Integer.parseInt(id[i]))) {
				dao.create(courses);
				log.trace("Insert in DB: users_courses --> " + courses);
				regMessage.append("Complete reg to course ").append(Integer.parseInt(id[i])).append(" ");
			} else {
				regMessage.append("Already registred to course ").append(Integer.parseInt(id[i])).append(" ");
			}
		}

		session.setAttribute("regMessage", regMessage);
		return forward;
	}
}
