package ua.svinkov.controller.Command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.svinkov.constants.Path;
import ua.svinkov.model.dao.impl.ConnectionPoolHolder;

class RegistrationCommandTest {
	
	private HttpServletRequest mockedRequest;
    private RegistrationCommand regCommand;
  
    private HttpSession session;
    private ServletContext context;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/facultative_test?useSSL=false");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
		ConnectionPoolHolder.setDataSource(ds);
	}

	@BeforeEach
	void setUp() throws Exception {
		mockedRequest = mock(HttpServletRequest.class);
		regCommand = new RegistrationCommand();
        session = mock(HttpSession.class);
        context = mock(ServletContext.class);
	}

	@Test
	void testEmptyLogin() {
		String login = "";
        String password = "test";
        String email = "test@gmail.com";
		String firstName = "firstname";
		String surname = "";
        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add("user");
        when(mockedRequest.getParameter("login")).thenReturn(login);
        when(mockedRequest.getParameter("pass")).thenReturn(password);
        when(mockedRequest.getParameter("email")).thenReturn(email);
        when(mockedRequest.getParameter("firstname")).thenReturn(firstName);
        when(mockedRequest.getParameter("surname")).thenReturn(surname);
        when(mockedRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(mockedRequest.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);
       
        String expected = Path.PAGE_REGISTRATION;
        String actual = regCommand.execute(mockedRequest);

        assertEquals(expected, actual);
	}
	
	@Test
	void testWrongEmail() {
		String login = "test";
        String password = "test";
        String email = "testgmail.com";
		String firstName = "firstname";
		String surname = "surname";
        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add("user");
        when(mockedRequest.getParameter("login")).thenReturn(login);
        when(mockedRequest.getParameter("pass")).thenReturn(password);
        when(mockedRequest.getParameter("email")).thenReturn(email);
        when(mockedRequest.getParameter("firstname")).thenReturn(firstName);
        when(mockedRequest.getParameter("surname")).thenReturn(surname);
        when(mockedRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(mockedRequest.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);
       
        String expected = Path.PAGE_REGISTRATION;
        String actual = regCommand.execute(mockedRequest);

        assertEquals(expected, actual);
	}
	
	@Test
	void testLoginTaken() {
		String login = "user";
        String password = "test";
        String email = "test@gmail.com";
		String firstName = "firstname";
		String surname = "surname";
        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add("user");
        when(mockedRequest.getParameter("login")).thenReturn(login);
        when(mockedRequest.getParameter("pass")).thenReturn(password);
        when(mockedRequest.getParameter("email")).thenReturn(email);
        when(mockedRequest.getParameter("firstname")).thenReturn(firstName);
        when(mockedRequest.getParameter("surname")).thenReturn(surname);
        when(mockedRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(mockedRequest.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);
       
        String expected = Path.PAGE_REGISTRATION;
        String actual = regCommand.execute(mockedRequest);

        assertEquals(expected, actual);
	}
	

}
