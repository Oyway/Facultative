package ua.svinkov.controller.Command;

import java.time.LocalDate;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.Topic;
import ua.svinkov.model.entity.User;
import ua.svinkov.service.CoursesService;

/**
 * Edit course item
 * 
 * @author R.Svinkov
 *
 */
public class CourseEditCommand extends Command {

	private static final long serialVersionUID = 1524296463662484015L;

	private static final Logger log = Logger.getLogger(CourseEditCommand.class);

	private static final String PARAM_OPTION_TOPICS = "optionTopics";
	private static final String PARAM_OPTION_TEACHER = "optionTeacher";
	private static final String PARAM_DATE_START = "dateStart";
	private static final String PARAM_DATE_END = "dateEnd";
	private static final String PARAM_DESCRIPTION = "description";

	private static final String PARAM_COURSE_ID = "courseId";
	private static final String PARAM_COURSE_NAME = "courseName";
	private static final String PARAM_EDIT = "edit";

	@Override
	public String execute(HttpServletRequest request) {
		CoursesService serv = new CoursesService();
		if (Objects.nonNull(request.getQueryString()) && request.getQueryString().equals(PARAM_EDIT)) {
			Course course = Course.builder().courseid(Long.parseLong(request.getParameter(PARAM_COURSE_ID)))
					.course(request.getParameter(PARAM_COURSE_NAME))
					.topic(Topic.builder().topicId(Long.parseLong(request.getParameter(PARAM_OPTION_TOPICS))).build())
					.teacher(
							User.builder().userid(Long.parseLong(request.getParameter(PARAM_OPTION_TEACHER))).build())
					.dateStart(LocalDate.parse(request.getParameter(PARAM_DATE_START)))
					.dateEnd(LocalDate.parse(request.getParameter(PARAM_DATE_END)))
					.description(request.getParameter(PARAM_DESCRIPTION)).build();
			serv.updateCourse(course);
			return Path.REDIRECT + Path.PAGE_ADMIN;
		}
		Long courseId = Long.parseLong(request.getParameter(PARAM_EDIT));

		Course course = serv.findCourseById(courseId);
		log.trace("Found in DB: course --> " + course);

		request.setAttribute(PARAM_COURSE_ID, course.getCourseid());
		request.setAttribute(PARAM_COURSE_NAME, course.getCourse());
		request.setAttribute(PARAM_OPTION_TOPICS, course.getTopic().getTopicId());
		request.setAttribute(PARAM_OPTION_TEACHER, course.getTeacher().getUserid());
		request.setAttribute(PARAM_DATE_START, course.getDateStart());
		request.setAttribute(PARAM_DATE_END, course.getDateEnd());
		request.setAttribute(PARAM_DESCRIPTION, course.getDescription());
		return Path.PAGE_COURSE_EDIT;
	}

}
