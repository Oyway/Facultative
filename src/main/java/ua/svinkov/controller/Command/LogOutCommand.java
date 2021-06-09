package ua.svinkov.controller.Command;

import ua.svinkov.model.entity.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)
        CommandUtility.setUserRole(request, Role.UNKNOWN, "Guest");
        
        //log.debug("Command starts");
		
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		
		//log.debug("Command finished");
		return "/login.jsp";
    }
}
