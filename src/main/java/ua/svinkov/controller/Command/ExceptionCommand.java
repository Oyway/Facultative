package ua.svinkov.controller.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author R.Svinkov
 *
 */
public class ExceptionCommand extends Command {
 
	private static final long serialVersionUID = -7394389007013339707L;

	@Override
    public String execute(HttpServletRequest request) {
        throw new RuntimeException("Generated exception");
    }
}
