package ua.svinkov.controller.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
 * Get teachers courses
 * 
 * @author R.Svinkov
 *
 */
public class GetTeacherCoursesCommand extends Command {

	private static final long serialVersionUID = 2510064395175234551L;

	private static final Logger log = Logger.getLogger(LoginCommand.class);

	public static final String PARAM_PAGE = "page";

	@Override
	public String execute(HttpServletRequest request) {
		String forward = Path.PAGE_TEACHER_BASIS;
		int tempPage = 1;
		if (Objects.nonNull(request.getParameter(PARAM_PAGE))) {
			tempPage = Integer.parseInt(request.getParameter(PARAM_PAGE));
		}
		int page = tempPage;

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

		List<UserCourses> courses = new CoursesService().findAllByTeacherId(user.getUserid());
		List<Long> pageTotaleTemp = new ArrayList<>();
		Set<Long> pageTotaleSetTemp = new HashSet<>();

		if (Objects.nonNull(request.getParameter("update"))) {
			String sortType = request.getParameter("optionSort");
			if (sortType.equals("StudCount Decs")) {
				session.setAttribute("sort", sortType);
				pageTotaleTemp = new CoursesService().findCoursesSortedByCountSt(user.getUserid());
			} else {
				session.setAttribute("sort", sortType);
				pageTotaleTemp = new CoursesService().findCoursesSortedByCountSt(user.getUserid());
				Collections.reverse(pageTotaleTemp);
			}
		} else if (Objects.nonNull(session.getAttribute("sort"))) {
			if (session.getAttribute("sort").equals("StudCount Decs")) {
				pageTotaleTemp = new CoursesService().findCoursesSortedByCountSt(user.getUserid());
			} else {
				pageTotaleTemp = new CoursesService().findCoursesSortedByCountSt(user.getUserid());
				Collections.reverse(pageTotaleTemp);
			}
		} else {
			pageTotaleSetTemp = courses.stream().map(t -> t.getCourse().getCourseid()).collect(Collectors.toSet());
		}

		if (pageTotaleTemp.isEmpty())
			pageTotaleTemp = new ArrayList<>(pageTotaleSetTemp);

		List<Long> pageTotal = new ArrayList<>(pageTotaleTemp);

		log.trace("Found in DB: courses --> " + courses);
		log.trace("Found in courses: pageTotale --> " + pageTotal);
		List<UserCourses> coursesCurrentPage = courses.stream()
				.filter(t -> t.getCourse().getCourseid() == new ArrayList<>(pageTotal).get(page - 1))
				.collect(Collectors.toList());
		log.trace("Found in courses: coursesCurrentPage --> " + coursesCurrentPage);
		if (!coursesCurrentPage.isEmpty())
			session.setAttribute("courseName", coursesCurrentPage.get(0).getCourse().getCourse());

		session.setAttribute("students", coursesCurrentPage);
		request.setAttribute("noOfPages", pageTotal.size());
		request.setAttribute("currentPage", page);
		return forward;
	}

}
