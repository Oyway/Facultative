package ua.svinkov.controller.Command;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.svinkov.model.entity.User;
import ua.svinkov.service.UserService;

public class RegistrationCommand implements Command {
	private static final Logger log = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
		String firstname = request.getParameter("firstname");
		String surname = request.getParameter("surname");

		String errorMessage = null;
		String forward = "/login.jsp";

		if (Objects.isNull(login) || login.equals("") || Objects.isNull(pass) || pass.equals("")
				|| Objects.isNull(email) || email.equals("") || Objects.isNull(firstname) || firstname.equals("")
				|| Objects.isNull(surname) || surname.equals("")) {
			errorMessage = "Fields cannot be empty";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return "/reg.jsp";
		}
		
		User userCheck = new UserService().findByLogin(login);
        if(Objects.nonNull(userCheck.getLogin())) {
        	errorMessage = "Login already taken";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return "/reg.jsp";
        }
		log.trace("Found in DB: user --> " + userCheck);
		System.out.println(login + " " + pass);
		User user = User.builder().userid(0).login(login).password(pass).email(email).firstname(firstname)
				.surname(surname).role(null).build();
		new UserService().createUser(user);
		forward = "/login";
		return forward;
	}

}
