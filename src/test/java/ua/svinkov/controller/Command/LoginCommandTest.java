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

class LoginCommandTest {
	
	private HttpServletRequest mockedRequest;
    private LoginCommand loginCommand;
  
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
        loginCommand = new LoginCommand();
        session = mock(HttpSession.class);
        context = mock(ServletContext.class);
	}
	
	@Test
    public void testExecuteNullLogin() {
		HttpSession session = mock(HttpSession.class);
		
        when(mockedRequest.getParameter("login")).thenReturn(null);
        when(mockedRequest.getParameter("password")).thenReturn("");
        when(mockedRequest.getSession()).thenReturn(session);
        String expected = Path.PAGE_LOGIN;
        
        String actual = loginCommand.execute(mockedRequest);

        assertEquals(expected, actual);
    }

	@Test
	void testExecuteGetStudentPage() {
        String login = "user";
        String password = "user";
        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add("user");
        when(mockedRequest.getParameter("login")).thenReturn(login);
        when(mockedRequest.getParameter("pass")).thenReturn(password);
        when(mockedRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(mockedRequest.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);
       
        String expected = Path.REDIRECT + Path.PAGE_STUDENT;
        String actual = loginCommand.execute(mockedRequest);

        assertEquals(expected, actual);
	}
	
	@Test
	void testExecuteGetAdminPage() {
        String login = "admin";
        String password = "admin";
        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add("user");
        when(mockedRequest.getParameter("login")).thenReturn(login);
        when(mockedRequest.getParameter("pass")).thenReturn(password);
        when(mockedRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(mockedRequest.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);
       
        String expected = Path.REDIRECT + Path.PAGE_ADMIN;
        String actual = loginCommand.execute(mockedRequest);

        assertEquals(expected, actual);
	}

	@Test
	void testExecuteGetTeacherPage() {
        String login = "teacher";
        String password = "t";
        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add("user");
        when(mockedRequest.getParameter("login")).thenReturn(login);
        when(mockedRequest.getParameter("pass")).thenReturn(password);
        when(mockedRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(mockedRequest.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);
       
        String expected = Path.REDIRECT + Path.PAGE_TEACHER;
        String actual = loginCommand.execute(mockedRequest);

        assertEquals(expected, actual);
	}
}
