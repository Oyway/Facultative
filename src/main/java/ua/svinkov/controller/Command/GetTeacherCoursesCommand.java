package ua.svinkov.controller.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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

public class GetTeacherCoursesCommand implements Command {

	private static final Logger log = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		int tempPage = 1;
		if (Objects.nonNull(request.getParameter("page"))) {
			tempPage = Integer.parseInt(request.getParameter("page")) ;
		}
		int page = tempPage;
		String forward = "/WEB-INF/teacher/teacherbasis.jsp";

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			forward = "/WEB-INF/error.jsp";
			return forward;
		}
		DaoFactory factory = JDBCDaoFactory.getInstance();
		JDBCUserCoursesDao dao = (JDBCUserCoursesDao) factory.createUserCoursesDao();
		List<UserCourses> courses = dao.findAllByTeacherId(user.getUserid());
		List<Integer> pageTotaleTemp = new ArrayList<>();
		Set<Integer> pageTotaleSetTemp = new HashSet<>();
		if (Objects.nonNull(request.getQueryString()) && request.getQueryString().equals("update")) {
			String sortType = request.getParameter("optionSort");
			if(sortType.equals("StudCount Decs")) {
				session.setAttribute("sort", sortType);
				pageTotaleTemp = new CoursesService().findCoursesSortedByCountSt();
			} else {
				session.setAttribute("sort", sortType);
				pageTotaleTemp = new CoursesService().findCoursesSortedByCountSt();
				Collections.reverse(pageTotaleTemp);
			}
		} else if(Objects.nonNull(session.getAttribute("sort"))){
			if(session.getAttribute("sort").equals("StudCount Decs")) {
				pageTotaleTemp = new CoursesService().findCoursesSortedByCountSt();
			} else {
				pageTotaleTemp = new CoursesService().findCoursesSortedByCountSt();
				Collections.reverse(pageTotaleTemp);
			}
		}else {
			pageTotaleSetTemp = courses.stream().map(t -> t.getCourse().getCourseid()).collect(Collectors.toSet());
		}
		if(pageTotaleTemp.isEmpty())
			pageTotaleTemp = new ArrayList<>(pageTotaleSetTemp);
		List<Integer> pageTotal = new ArrayList<>(pageTotaleTemp);
		log.trace("Found in DB: user --> " + user);

		log.trace("Found in DB: courses --> " + courses);
		log.trace("Found in courses: pageTotale --> " + pageTotal);
		List<UserCourses> coursesCurrentPage = courses.stream()
				.filter(t -> t.getCourse().getCourseid() == new ArrayList<>(pageTotal).get(page - 1))
				.collect(Collectors.toList());
		log.trace("Found in courses: coursesCurrentPage --> " + coursesCurrentPage);
		session.setAttribute("courseName", coursesCurrentPage.get(0).getCourse().getCourse());
		session.setAttribute("students", coursesCurrentPage);
		request.setAttribute("noOfPages", pageTotal.size());
		request.setAttribute("currentPage", page);
		dao.close();
		return forward;
	}

}
