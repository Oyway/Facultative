package ua.svinkov.controller.Command;

import java.util.ArrayList;
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

public class GetAdminAllCoursesCommand implements Command{
	
	private static final Logger log = Logger.getLogger(GetAdminAllCoursesCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String forward = Path.PAGE_ADMIN_BASIS;
		
		CoursesService courseService = new CoursesService();
        UserService userService = new UserService();
        
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        
        if(!CommandUtility.checkUserIsLogged(request, user.getLogin())){
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

        if(Objects.nonNull(request.getParameter("delete"))) {
        	int courseId = Integer.parseInt(request.getParameter("delete"));
        	courseService.deleteCourse(courseId);
        }
        
        List<Course> courses = courseService.findAll();
        List<Topic> topics = courseService.findAllTopics();
        List<User> teachers = userService.findAllTeachers();
		log.trace("Found in DB: courses --> " + courses);	
		
		int tempPage = 1;
		int recordsPerPage = 5;
		if(Objects.nonNull(request.getParameter("page")))
			tempPage = Integer.parseInt(request.getParameter("page"));
		int page = tempPage;
		
		Integer pageTotale = (int)Math.ceil(courses.size()*1.0/recordsPerPage);
		log.trace("Found in DB: user --> " + user);
		
		log.trace("Found in DB: courses --> " + courses);
		log.trace("Found in courses: pageTotale --> " + pageTotale);
		List<Course> coursesCurrentPage = new ArrayList<>();
		for(int i = (page-1)*recordsPerPage; i < courses.size() && i < page*recordsPerPage; i++) {
			coursesCurrentPage.add(courses.get(i));
		}
		log.trace("Found in courses: coursesCurrentPage --> " + coursesCurrentPage);
		session.setAttribute("allCourses", coursesCurrentPage);
		session.setAttribute("allTopics", topics);
		session.setAttribute("allTeachers", teachers);
        request.setAttribute("noOfPages", pageTotale);
        request.setAttribute("currentPage", page);

		return forward;
	}

}
