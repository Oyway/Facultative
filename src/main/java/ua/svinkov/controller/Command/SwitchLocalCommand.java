package ua.svinkov.controller.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

public class SwitchLocalCommand implements Command {
	
	private static final Logger log = Logger.getLogger(SwitchLocalCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String forward = request.getRequestURI();
		String sessionLocale = request.getParameter("locales");
		log.trace("sessionLocale --> " + sessionLocale);
		log.trace("forward --> " + request.getContextPath());
		if (sessionLocale != null && !sessionLocale.isEmpty()) {
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", sessionLocale);
			
			session.setAttribute("defaultLocale", sessionLocale);
			log.trace("Set the session attribute: defaultLocaleName --> " + sessionLocale);
			
			log.info("Locale for user: defaultLocale --> " + sessionLocale);
			log.info("Locale for user: defaultLocale --> " + sessionLocale);
		}
		return forward;
	}

}
