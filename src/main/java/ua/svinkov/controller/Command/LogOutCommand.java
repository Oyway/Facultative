package ua.svinkov.controller.Command;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.setUserRole(request, Role.UNKNOWN, "Guest");
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		return Path.PAGE_LOGIN;
    }
}
