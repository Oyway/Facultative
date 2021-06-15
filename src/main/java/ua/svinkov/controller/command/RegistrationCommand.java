package ua.svinkov.controller.command;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.User;
import ua.svinkov.service.UserService;

/**
 * Registration command
 * 
 * @author R.Svinkov
 *
 */
public class RegistrationCommand extends Command {

	private static final long serialVersionUID = 4439998128044370742L;

	private static final Logger log = Logger.getLogger(RegistrationCommand.class);

	private static final String EMPTY_LINE = "";
	private static final String REGEX_EMAIL = "\\w+@\\w+\\.\\w+";

	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstname");
		String surname = request.getParameter("surname");

		String errorMessage = null;

		if (validation(login, pass, email, firstName, surname)) {
			errorMessage = "Fields cannot be empty";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return Path.PAGE_REGISTRATION;
		}

		if (!email.matches(REGEX_EMAIL)) {
			errorMessage = "Email must be correct(Example@gmail.com)";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return Path.PAGE_REGISTRATION;
		}

		User userCheck = new UserService().findByLogin(login);
		if (Objects.nonNull(userCheck)) {
			errorMessage = "Login already taken";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return Path.PAGE_REGISTRATION;
		}
		log.trace("Found in DB: user --> " + userCheck);
		System.out.println(login + " " + pass);
		User user = User.builder().userid(0).login(login).password(pass).email(email).firstName(firstName)
				.surname(surname).role(null).build();
		new UserService().createUser(user);
		return Path.PAGE_LOGIN;
	}

	/**
	 * Validation of input data
	 * 
	 * @return result of checking params
	 */
	private boolean validation(String login, String pass, String email, String firstName, String surname) {
		boolean valid = false;
		if (Objects.isNull(login) || login.equals(EMPTY_LINE)) {
			valid = true;
		} else if (Objects.isNull(pass) || pass.equals(EMPTY_LINE)) {
			valid = true;
		} else if (Objects.isNull(email) || email.equals(EMPTY_LINE)) {
			valid = true;
		} else if (Objects.isNull(firstName) || firstName.equals(EMPTY_LINE)) {
			valid = true;
		} else if (Objects.isNull(surname) || surname.equals(EMPTY_LINE)) {
			valid = true;
		}
		return valid;
	}

}
