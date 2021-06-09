package ua.svinkov.controller.Command;

import javax.servlet.http.HttpServletRequest;

import ua.svinkov.constants.Path;

public class GetProfileCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		return Path.PAGE_PROFILE;
	}

}
