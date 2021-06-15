package ua.svinkov.controller.command;

import javax.servlet.http.HttpServletRequest;

import ua.svinkov.constants.Path;

/**
 * Get profile of user
 * 
 * @author R.Svinkov
 *
 */
public class GetProfileCommand extends Command {

	private static final long serialVersionUID = 1627794896382193302L;

	@Override
	public String execute(HttpServletRequest request) {
		return Path.PAGE_PROFILE;
	}

}
