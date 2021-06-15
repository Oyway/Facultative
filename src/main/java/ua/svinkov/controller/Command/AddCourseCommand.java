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
 * Add courses command for admin
 * 
 * @author R.Svinkov
 *
 */
public class AddCourseCommand extends Command {

	private static final long serialVersionUID = -5707789410493061581L;

	private static final Logger log = Logger.getLogger(AddCourseCommand.class);

	private static final String PARAM_OPTION_TOPICS = "optionTopics";
	private static final String PARAM_COURSE = "course";
	private static final String EMPTY_LINE = "";
	private static final String PARAM_OPTION_TEACHER = "optionTeacher";
	private static final String PARAM_DATE_START = "dateStart";
	private static final String PARAM_DATE_END = "dateEnd";
	private static final String PARAM_DESCRIPTION = "description";

	@Override
	public String execute(HttpServletRequest request) {
		String errorMessage = null;
		if (!validation(request)) {
			errorMessage = "Fields cannot be empty";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return Path.PAGE_ADMIN;
		}

		String course = request.getParameter(PARAM_COURSE);
		Integer topicId = Integer.parseInt(request.getParameter(PARAM_OPTION_TOPICS));
		Integer teacher = Integer.parseInt(request.getParameter(PARAM_OPTION_TEACHER));
		LocalDate dateStart = LocalDate.parse(request.getParameter(PARAM_DATE_START));
		LocalDate dateEnd = LocalDate.parse(request.getParameter(PARAM_DATE_END));
		String description = request.getParameter(PARAM_DESCRIPTION);

		if (dateEnd.isBefore(dateStart)) {
			errorMessage = "End date cant be less then start date!";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return Path.PAGE_ADMIN;
		}

		Course c = Course.builder().course(course).topic(Topic.builder().topicId(topicId).build())
				.teacher(User.builder().userid(teacher).build()).dateStart(dateStart).dateEnd(dateEnd)
				.description(description).build();

		new CoursesService().createCourse(c);

		return Path.REDIRECT + Path.PAGE_ADMIN;
	}

	/**
	 * Validation of parameters for course
	 * 
	 * @return result of validation
	 */
	private boolean validation(HttpServletRequest request) {
		boolean valid = true;
		if (Objects.isNull(request.getParameter(PARAM_COURSE))
				|| request.getParameter(PARAM_COURSE).equals(EMPTY_LINE)) {
			valid = false;
		} else if (Objects.isNull(request.getParameter(PARAM_OPTION_TOPICS))
				|| request.getParameter(PARAM_OPTION_TOPICS).equals(EMPTY_LINE)) {
			valid = false;
		} else if (Objects.isNull(request.getParameter(PARAM_OPTION_TEACHER))
				|| request.getParameter(PARAM_OPTION_TEACHER).equals(EMPTY_LINE)) {
			valid = false;
		} else if (Objects.isNull(request.getParameter(PARAM_DATE_START))
				|| request.getParameter(PARAM_DATE_START).equals(EMPTY_LINE)) {
			valid = false;
		} else if (Objects.isNull(request.getParameter(PARAM_DATE_END))
				|| request.getParameter(PARAM_DATE_END).equals(EMPTY_LINE)) {
			valid = false;
		}
		return valid;
	}

}
