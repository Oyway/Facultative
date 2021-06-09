package ua.svinkov.controller.Command;

import javax.servlet.http.HttpServletRequest;

public class GetProfileCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		String forward = "/WEB-INF/profile.jsp";
		
		return forward;
	}

}
