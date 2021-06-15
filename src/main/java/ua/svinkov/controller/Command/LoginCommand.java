package ua.svinkov.controller.Command;

import org.apache.log4j.Logger;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.enums.Role;
import ua.svinkov.service.UserService;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

	private static final Logger log = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String errorMessage = null;
		String forward = Path.PAGE_LOGIN;

		String login = request.getParameter("login");
		String pass = request.getParameter("pass");

		HttpSession session = request.getSession();
		User currentUser = (User) request.getSession().getAttribute("user");

		if (login == null || login.equals("") || pass == null || pass.equals("")) {
			errorMessage = "Login/password cannot be empty";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		}

		if (Objects.nonNull(currentUser) && !CommandUtility.checkUserIsLogged(request, currentUser.getLogin())) {
			if (currentUser.getRole().equals(Role.ADMIN)) {
				forward = Path.REDIRECT + Path.PAGE_ADMIN;
			} else if (currentUser.getRole().equals(Role.TEACHER)) {
				forward = Path.REDIRECT + Path.PAGE_ADMIN;
			} else if (currentUser.getRole().equals(Role.STUDENT)) {
				forward = Path.REDIRECT + Path.PAGE_USER_BASIS;
			} else if (currentUser.getRole().equals(Role.UNKNOWN)) {
				forward = Path.REDIRECT + Path.PAGE_LOGIN;
			}
			return forward;
		}

		User user = new UserService().findByLogin(login);

		log.trace("Found in DB: user --> " + user);

		if (user == null || !pass.equals(user.getPassword())) {
			errorMessage = "Cannot find user with such login/password";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		} else if (!user.isStatus()) {
			errorMessage = "Your account is blocked!";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		} else {
			CommandUtility.checkUserIsLogged(request, user.getLogin());
			if (user.getRole().equals(Role.ADMIN)) {
				if (user.getRole() == Role.ADMIN)
					CommandUtility.setUserRole(request, Role.ADMIN, login);
				forward = Path.REDIRECT + Path.PAGE_ADMIN;
			}
			if (user.getRole().equals(Role.STUDENT)) {
				CommandUtility.setUserRole(request, Role.STUDENT, login);
				forward = Path.REDIRECT + Path.PAGE_STUDENT;
			}
			if (user.getRole().equals(Role.TEACHER)) {
				CommandUtility.setUserRole(request, Role.TEACHER, login);
				forward = Path.REDIRECT + Path.PAGE_TEACHER;
			}
			session.setAttribute("user", user);
			log.trace("Set the session attribute: user --> " + user);
			session.setAttribute("userRole", user.getRole());
			log.trace("Set the session attribute: userRole --> " + user.getRole());
			log.info("User " + user + " logged as " + user.getRole().toString().toLowerCase());
		}
		return forward;
	}

}
