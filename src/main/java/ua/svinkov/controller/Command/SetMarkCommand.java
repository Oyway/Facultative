package ua.svinkov.controller.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.model.entity.User;
import ua.svinkov.service.CoursesService;

public class SetMarkCommand implements Command {
	
	private static final Logger log = Logger.getLogger(SetMarkCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String forward = "redirect:/teacher?page=" + request.getParameter("page");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			forward = "/login";
			return forward;
		}
		
		int studentid = Integer.parseInt(request.getParameter("studentid"));
		int curseid = Integer.parseInt(request.getParameter("courseid"));
		int mark = Integer.parseInt(request.getParameter("mark"));

		
		new CoursesService().updateMark(studentid, curseid, mark);
		
		return forward;
	}

}
