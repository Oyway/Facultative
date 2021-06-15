package ua.svinkov.controller.listener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.svinkov.model.entity.User;

class SessionListenerTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSessionDestroyed() {
		HttpSessionEvent event = mock(HttpSessionEvent.class);
        HttpSession session = mock(HttpSession.class);
        ServletContext context = mock(ServletContext.class);
        User user = mock(User.class);
        String login = "user";
        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add(login);
        when(event.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);
        when(user.getLogin()).thenReturn(login);
        when( event.getSession().getAttribute("user")).thenReturn(user);
        SessionListener listener = new SessionListener();

        listener.sessionDestroyed(event);
        loggedUsers.remove(login);

        verify(session, times(1)).setAttribute("loggedUsers", loggedUsers);
	}

}
