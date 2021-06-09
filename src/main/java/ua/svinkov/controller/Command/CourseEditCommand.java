package ua.svinkov.controller.Command;

import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.Topic;
import ua.svinkov.model.entity.User;
import ua.svinkov.service.CoursesService;
import ua.svinkov.service.UserService;

public class CourseEditCommand implements Command{
	
	private static final Logger log = Logger.getLogger(CourseEditCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String forward = "redirect:/admin";
		CoursesService serv = new CoursesService();
		if(Objects.nonNull(request.getQueryString()) && request.getQueryString().equals("edit")) {
			Course course = Course.builder()
					.courseid(Integer.parseInt(request.getParameter("courseId")))
					.course(request.getParameter("courseName"))
					.topic(Topic.builder().topicId(Integer.parseInt(request.getParameter("optionTopics"))).build())
					.teacher(User.builder().userid(Integer.parseInt(request.getParameter("optionTeacher"))).build())
					.dateStart(LocalDate.parse(request.getParameter("dateStart")))
					.dateEnd(LocalDate.parse(request.getParameter("dateEnd")))
					.description(request.getParameter("descr")).build();
			serv.updateCourse(course);
			return forward;
		}
		Integer courseId = Integer.parseInt(request.getParameter("edit"));

		String errorMessage = null;
		forward = "/WEB-INF/admin/courseedit.jsp";
		
		
		Course course = serv.findCourseById(courseId);
		log.trace("Found in DB: course --> " + course);
		
		CoursesService courseService = new CoursesService();
        UserService userService = new UserService();
        
        List<Course> courses = courseService.findAll();
        List<Topic> topics = courseService.findAllTopics();
        List<User> teachers = userService.findAllTeachers();
        
        request.setAttribute("courseId", course.getCourseid());
		request.setAttribute("courseName", course.getCourse());
		request.setAttribute("optionTopics", course.getTopic().getTopicId());
		request.setAttribute("optionTeacher", course.getTeacher().getUserid());
		request.setAttribute("dateStart", course.getDateStart());
		request.setAttribute("dateEnd", course.getDateEnd());
		request.setAttribute("descr", course.getDescription());
		//User user = User.builder().userid(0).login(login).password(pass).email(email).firstname(firstname)
		//		.surname(surname).role(null).build();
		//new UserService().createUser(user);
		//forward = "/login";
		return forward;
	}

}
