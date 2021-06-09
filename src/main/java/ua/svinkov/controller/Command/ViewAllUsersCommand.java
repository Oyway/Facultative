package ua.svinkov.controller.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.enums.Role;
import ua.svinkov.service.UserService;

public class ViewAllUsersCommand implements Command {
	
	private static final Logger log = Logger.getLogger(ViewAllUsersCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String forward = "/WEB-INF/admin/users.jsp";
		
        UserService userService = new UserService();
        
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        
        if(!CommandUtility.checkUserIsLogged(request, user.getLogin())){
        	forward = "/login.jsp";
            return forward;
        }
        forward = "redirect:/users";
        String query = request.getQueryString();
        if(Objects.nonNull(query) && query.startsWith("block")) {
        	int userId = Integer.parseInt(request.getQueryString().replaceAll("block=", ""));
        	User userUpdate = userService.findById(userId);
        	userUpdate.setStatus(false);
        	userService.updateUser(userUpdate);
        	return forward;
        } else if(Objects.nonNull(query) && query.startsWith("unblock")) {
        	int userId = Integer.parseInt(request.getQueryString().replaceAll("unblock=", ""));
        	User userUpdate = userService.findById(userId);
        	userUpdate.setStatus(true);
        	userService.updateUser(userUpdate);
        	return forward;
        } else if(Objects.nonNull(query) && query.startsWith("update")) {
        	int userId = Integer.parseInt(request.getQueryString().replaceAll("update=", ""));
        	String userRole = request.getParameter("optionRoles"+userId);
        	User userUpdate = userService.findById(userId);
        	userUpdate.setRole(Role.valueOf(userRole));
        	userService.updateUser(userUpdate);
        	return forward;
        }
        forward = "/WEB-INF/admin/users.jsp";
        List<User> users = userService.findAll();
		log.trace("Found in DB: users --> " + users);	
		
		int tempPage = 1;
		int recordsPerPage = 7;
		if(Objects.nonNull(request.getQueryString()) && request.getQueryString().startsWith("page"))
			tempPage = Integer.parseInt(request.getQueryString().replaceAll("page=", ""));
		int page = tempPage;

		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			forward = "/WEB-INF/error.jsp";
			return forward;
		}

		Integer pageTotale = (int)Math.ceil(users.size()*1.0/recordsPerPage);
		log.trace("Found in DB: user --> " + user);
		
		log.trace("Found in courses: pageTotale --> " + pageTotale);
		List<User> usersCurrentPage = new ArrayList<>();
		for(int i = (page-1)*recordsPerPage; i < users.size() && i < page*recordsPerPage; i++) {
			usersCurrentPage.add(users.get(i));
		}
		log.trace("Found in courses: coursesCurrentPage --> " + usersCurrentPage);
		session.setAttribute("allUsers", usersCurrentPage);
        request.setAttribute("noOfPages", pageTotale);
        request.setAttribute("currentPage", page);

		return forward;
	}

}
