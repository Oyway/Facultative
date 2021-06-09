package ua.svinkov.controller.Command;

import java.time.LocalDate;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.Topic;
import ua.svinkov.model.entity.User;
import ua.svinkov.service.CoursesService;

public class AddCourseCommand implements Command {

	private static final Logger log = Logger.getLogger(AddCourseCommand.class);
	
	private static final String OPTION_TOPICS = "optionTopics";
	private static final String COURSE = "course";
	private static final String EMPTY_LINE = "";
	private static final String OPTION_TEACHER = "optiontTeachers";
	private static final String DATE_START = "datestart";
	private static final String DATE_END = "dateend";
	private static final String DESCRIPTION = "description";

	@Override
	public String execute(HttpServletRequest request) {
		String forward = "redirect:/admin";
		String errorMessage = null;
		if(!validation(request)) {
			errorMessage = "Fields cannot be empty";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return "/admin";
		}

		String course = request.getParameter(COURSE);
		Integer topicId = Integer.parseInt(request.getParameter(OPTION_TOPICS));
		Integer teacher = Integer.parseInt(request.getParameter(OPTION_TEACHER));
		LocalDate dateStart = LocalDate.parse(request.getParameter(DATE_START));
		LocalDate dateEnd = LocalDate.parse(request.getParameter(DATE_END));
		String description = request.getParameter(DESCRIPTION);

		if(dateEnd.isBefore(dateStart)) {
			errorMessage = "End date cant be less then start date!";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return "/admin";
		}

		Course c = Course.builder().course(course).topic(Topic.builder().topicId(topicId).build())
				.teacher(User.builder().userid(teacher).build()).dateStart(dateStart).dateEnd(dateEnd)
				.description(description).build();

		new CoursesService().createCourse(c);
		forward = "redirect:/admin";

		return forward;
	}
	
	private boolean validation(HttpServletRequest request) {
		boolean valid = true;
		if(Objects.isNull(request.getParameter(COURSE)) || request.getParameter(COURSE).equals(EMPTY_LINE)) {
			valid = false;
		} else if(Objects.isNull(request.getParameter(OPTION_TOPICS)) || request.getParameter(OPTION_TOPICS).equals(EMPTY_LINE)) {
			valid = false;
		} else if(Objects.isNull(request.getParameter(OPTION_TEACHER)) || request.getParameter(OPTION_TEACHER).equals(EMPTY_LINE)) {
			valid = false;
		} else if(Objects.isNull(request.getParameter(DATE_START)) || request.getParameter(DATE_START).equals(EMPTY_LINE)) {
			valid = false;
		} else if(Objects.isNull(request.getParameter(DATE_END)) || request.getParameter(DATE_END).equals(EMPTY_LINE)) {
			valid = false;
		} 
		return valid;
	}

}
