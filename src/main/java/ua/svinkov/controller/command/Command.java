package ua.svinkov.controller.command;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * Main interface for the Command pattern implementation.
 * 
 * @author R.Svinkov
 *
 */
public abstract class Command implements Serializable {

	private static final long serialVersionUID = 6230910481111755076L;

	/**
	 * Execution method for command.
	 * 
	 * @return Address to go once the command is executed.
	 */
	public abstract String execute(HttpServletRequest request);
}
