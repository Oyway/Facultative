package ua.svinkov.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.User;
import ua.svinkov.service.CoursesService;

/**
 * Set marks items
 * 
 * @author R.Svinkov
 *
 */
public class SetMarkCommand extends Command {
	
	private static final long serialVersionUID = 7013312063945940667L;
	private static final Logger log = Logger.getLogger(SetMarkCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String forward = Path.REDIRECT + Path.PAGE_TEACHER + "?page=" + request.getParameter("page");
		
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
		log.info("Mark for studentid " + studentid + " curseid " + curseid + " update");
		return forward;
	}

}
