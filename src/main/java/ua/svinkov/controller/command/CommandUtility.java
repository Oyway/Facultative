package ua.svinkov.controller.command;

import ua.svinkov.model.entity.enums.Role;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

/**
 * Creating and regulation of session
 * 
 * @author R.Svinkov
 *
 */
class CommandUtility {
	/**
	 * Set user role in session
	 * 
	 * @param role User role
	 * @param name User login
	 */
	static void setUserRole(HttpServletRequest request, Role role, String name) {
		HttpSession session = request.getSession();
		ServletContext context = request.getServletContext();
		context.setAttribute("userName", name);
		session.setAttribute("role", role);
	}

	/**
	 * 
	 * @param userName User login
	 * @return if user logged or not
	 */
	static boolean checkUserIsLogged(HttpServletRequest request, String userName) {
		HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
				.getAttribute("loggedUsers");

		if (loggedUsers.stream().anyMatch(userName::equals)) {
			return true;
		}
		loggedUsers.add(userName);
		request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
		return false;
	}
}
