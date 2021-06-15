package ua.svinkov.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.svinkov.controller.command.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

/**
 * Main servlet controller.
 * 
 * @author R.Svinkov
 *
 */
public class Servlet extends HttpServlet {

	private static final long serialVersionUID = 3984106607436184738L;

	private Map<String, Command> commands = new HashMap<>();

	@Override
	public void init(ServletConfig servletConfig) {

		servletConfig.getServletContext().setAttribute("loggedUsers", new HashSet<String>());

		commands.put("logout", new LogOutCommand());
		commands.put("login", new LoginCommand());
		commands.put("exception", new ExceptionCommand());
		commands.put("reg", new RegistrationCommand());
		commands.put("student", new GetActiveCoursesCommand());
		commands.put("allcourses", new GetAllCoursesCommand());
		commands.put("reg_course", new RegOnCoursesCommand());
		commands.put("teacher", new GetTeacherCoursesCommand());
		commands.put("setMark", new SetMarkCommand());
		commands.put("profile", new GetProfileCommand());
		commands.put("admin", new GetAdminAllCoursesCommand());
		commands.put("addCourse", new AddCourseCommand());
		commands.put("courseedit", new CourseEditCommand());
		commands.put("users", new ViewAllUsersCommand());
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		processRequest(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		processRequest(request, response);
	}

	/**
	 * Main method of this controller.
	 * 
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		path = path.replaceAll(".*/Facultative/", "");
		String commandName = request.getParameter("command");
		Command command = null;
		if (!Objects.isNull(commandName)) {
			command = commands.getOrDefault(commandName, new LoginCommand());
		} else {
			command = commands.getOrDefault(path, new LoginCommand());
		}
		System.out.println(command.getClass().getName());
		String page = command.execute(request);
		if (page.contains("redirect:")) {
			response.sendRedirect(page.replace("redirect:", "/Facultative"));
		} else {
			request.getRequestDispatcher(page).forward(request, response);
		}
	}
}
