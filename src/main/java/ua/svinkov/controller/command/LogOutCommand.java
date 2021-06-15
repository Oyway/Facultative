package ua.svinkov.controller.command;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * LogOut command
 * 
 * @author R.Svinkov
 *
 */
public class LogOutCommand extends Command {

	private static final long serialVersionUID = -2382724900723146485L;

	@Override
	public String execute(HttpServletRequest request) {
		CommandUtility.setUserRole(request, Role.UNKNOWN, "Guest");
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		return Path.PAGE_LOGIN;
	}
}
