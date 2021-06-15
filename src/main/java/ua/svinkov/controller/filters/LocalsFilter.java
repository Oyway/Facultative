package ua.svinkov.controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

public class LocalsFilter implements Filter {

	private static final Logger log = Logger.getLogger(LocalsFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		String sessionLocale = request.getParameter("locales");
		log.trace("sessionLocale --> " + sessionLocale);
		if (sessionLocale != null && !sessionLocale.isEmpty()) {
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", sessionLocale);

			session.setAttribute("defaultLocale", sessionLocale);
			log.trace("Set the session attribute: defaultLocaleName --> " + sessionLocale);

			log.info("Locale for user: defaultLocale --> " + sessionLocale);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
