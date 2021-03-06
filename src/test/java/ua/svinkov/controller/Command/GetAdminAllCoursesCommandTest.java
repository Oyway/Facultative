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
import ua.svinkov.controller.Command.GetAdminAllCoursesCommand;
import ua.svinkov.model.dao.impl.ConnectionPoolHolder;
import ua.svinkov.model.entity.User;

class GetAdminAllCoursesCommandTest {

	private HttpServletRequest mockedRequest;
    private GetAdminAllCoursesCommand getAdminAllCoursesCommand;
  
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
		getAdminAllCoursesCommand = new GetAdminAllCoursesCommand();
        session = mock(HttpSession.class);
        context = mock(ServletContext.class);
	}

	@Test
	void testExecute() {
		String login = "admin";
        User user = mock(User.class);
        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add("admin");
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getLogin()).thenReturn(login);
        when(mockedRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(mockedRequest.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);
       
        String expected = Path.PAGE_ADMIN_BASIS;
        String actual = getAdminAllCoursesCommand.execute(mockedRequest);

        assertEquals(expected, actual);
	}

}
