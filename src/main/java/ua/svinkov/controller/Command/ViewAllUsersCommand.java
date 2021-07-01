package ua.svinkov.controller.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.svinkov.constants.Path;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.enums.Role;
import ua.svinkov.service.UserService;

/**
 * View all users for admin
 * 
 * @author R.Svinkov
 *
 */
public class ViewAllUsersCommand extends Command {

	private static final long serialVersionUID = -3825761703485439240L;

	private static final Logger log = Logger.getLogger(ViewAllUsersCommand.class);

	private static final String BLOCK = "block";
	private static final String UNBLOCK = "unblock";
	private static final String PAGE = "page";
	private static final String UPDATE = "update";

	@Override
	public String execute(HttpServletRequest request) {
		String forward = Path.PAGE_ADMIN_USERS;

		UserService userService = new UserService();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			return Path.PAGE_LOGIN;
		}
		forward = Path.REDIRECT + Path.PAGE_USERS;
		String query = request.getQueryString();
		if (Objects.nonNull(query) && query.startsWith(BLOCK)) {
			Long userId = Long.parseLong(request.getParameter(BLOCK));
			User userUpdate = userService.findById(userId);
			userUpdate.setStatus(false);
			userService.updateUser(userUpdate);
			CommandUtility.removeUserFromLogged(request, userUpdate.getLogin());
			return forward;
		} else if (Objects.nonNull(query) && query.startsWith(UNBLOCK)) {
			Long userId = Long.parseLong(request.getParameter(UNBLOCK));
			User userUpdate = userService.findById(userId);
			userUpdate.setStatus(true);
			userService.updateUser(userUpdate);
			return forward;
		} else if (Objects.nonNull(query) && query.startsWith(UPDATE)) {
			Long userId = Long.parseLong(request.getParameter(UPDATE));
			String userRole = request.getParameter("optionRoles" + userId);
			User userUpdate = userService.findById(userId);
			userUpdate.setRole(Role.valueOf(userRole));
			userService.updateUser(userUpdate);
			CommandUtility.removeUserFromLogged(request, userUpdate.getLogin());
			return forward;
		}
		forward = Path.PAGE_ADMIN_USERS;
		List<User> users = userService.findAll();
		log.trace("Found in DB: users --> " + users);

		int tempPage = 1;
		int recordsPerPage = 7;
		if (Objects.nonNull(request.getParameter(PAGE)))
			tempPage = Integer.parseInt(request.getParameter(PAGE));
		int page = tempPage;

		if (!CommandUtility.checkUserIsLogged(request, user.getLogin())) {
			return Path.PAGE_LOGIN;
		}

		Integer pageTotale = (int) Math.ceil(users.size() * 1.0 / recordsPerPage);
		log.trace("Found in DB: user --> " + user);

		log.trace("Found in courses: pageTotale --> " + pageTotale);
		List<User> usersCurrentPage = new ArrayList<>();
		for (int i = (page - 1) * recordsPerPage; i < users.size() && i < page * recordsPerPage; i++) {
			usersCurrentPage.add(users.get(i));
		}
		log.trace("Found in courses: coursesCurrentPage --> " + usersCurrentPage);
		session.setAttribute("allUsers", usersCurrentPage);
		request.setAttribute("noOfPages", pageTotale);
		request.setAttribute("currentPage", page);

		return forward;
	}
}
