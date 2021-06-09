package ua.svinkov.controller.Command;

import org.apache.log4j.Logger;

import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.enums.Role;
import ua.svinkov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command{
	
	private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        
        String errorMessage = null;
        String forward = "/login.jsp";

        HttpSession session = request.getSession();
        
        if( login == null || login.equals("") || pass == null || pass.equals("")  ){
        	errorMessage = "Login/password cannot be empty";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
            return forward;
        }
        
        /*if(CommandUtility.checkUserIsLogged(request, login)){
        	forward = "/WEB-INF/error.jsp";
            return forward;
        }
*/
        User user = new UserService().findByLogin(login);
        
		log.trace("Found in DB: user --> " + user);
			
		if (user == null || !pass.equals(user.getPassword())) {
			errorMessage = "Cannot find user with such login/password";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			forward = "/login.jsp";
            return forward;
		} else if(!user.isStatus()){
			errorMessage = "Your account is blocked!";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			forward = "/login.jsp";
            return forward;
		} else {	
			if(CommandUtility.checkUserIsLogged(request, login)){
	        	forward = "/WEB-INF/error.jsp";
	            return forward;
	        }
			if (user.getRole().equals(Role.ADMIN)) {
				if(user.getRole() == Role.ADMIN)
				 CommandUtility.setUserRole(request, Role.ADMIN, login);
				forward = "redirect:/admin";
			}
		
			if (user.getRole().equals(Role.STUDENT)) {
				CommandUtility.setUserRole(request, Role.STUDENT, login);
				forward = "redirect:/student";
			}
			

			if (user.getRole().equals(Role.TEACHER)) {
				CommandUtility.setUserRole(request, Role.TEACHER, login);
				forward = "redirect:/teacher";
			}
			
			session.setAttribute("user", user);
			log.trace("Set the session attribute: user --> " + user);
				
			session.setAttribute("userRole", user.getRole());				
			log.trace("Set the session attribute: userRole --> " + user.getRole());
				
			log.info("User " + user + " logged as " + user.getRole().toString().toLowerCase());
			
		}
		return forward;
    }

}
