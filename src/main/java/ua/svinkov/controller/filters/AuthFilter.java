package ua.svinkov.controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.model.entity.enums.Role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Authorization filter
 * 
 * @author R.Svinkov
 *
 */
public class AuthFilter implements Filter {

	private static final Logger log = Logger.getLogger(AuthFilter.class);

	private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private static List<String> commons = new ArrayList<String>();
	private static List<String> outOfControl = new ArrayList<String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("Filter initialization starts");

		// roles
		accessMap.put(Role.ADMIN, asList(filterConfig.getInitParameter("admin")));
		accessMap.put(Role.TEACHER, asList(filterConfig.getInitParameter("teacher")));
		accessMap.put(Role.STUDENT, asList(filterConfig.getInitParameter("student")));
		log.trace("Access map --> " + accessMap);

		// commons
		commons = asList(filterConfig.getInitParameter("common"));
		log.trace("Common commands --> " + commons);

		// out of control
		outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
		log.trace("Out of control commands --> " + outOfControl);

		log.debug("Filter initialization finished");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		log.debug("Filter starts");

		if (accessAllowed(request)) {
			log.debug("Filter finished");
			filterChain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessasge);
			log.trace("Set the request attribute: errorMessage --> " + errorMessasge);

			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String commandName = httpRequest.getRequestURI();
		commandName = commandName.replaceAll(".*/Facultative/", "");
		if (commandName == null || commandName.isEmpty())
			return false;

		if (outOfControl.contains(commandName))
			return true;

		HttpSession session = httpRequest.getSession(false);
		if (session == null)
			return false;

		Role userRole = (Role) session.getAttribute("userRole");
		if (userRole == null)
			return false;

		return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
	}

	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens())
			list.add(st.nextToken());
		return list;
	}

	@Override
	public void destroy() {

	}
}
