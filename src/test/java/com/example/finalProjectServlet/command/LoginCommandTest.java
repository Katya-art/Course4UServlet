package com.example.finalProjectServlet.command;

import com.example.finalProjectServlet.controller.command.LoginCommand;
import com.example.finalProjectServlet.model.dao.UserDao;
import com.example.finalProjectServlet.model.entity.Role;
import com.example.finalProjectServlet.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class LoginCommandTest extends Mockito {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    ServletContext servletContext;

    @Mock
    UserDao userDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void completedTest() throws Exception {
        Set<String> loggedUsers = new HashSet<>();
        when(request.getParameter("username")).thenReturn("TestUser");
        when(request.getParameter("password")).thenReturn("testUser");
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getServletContext()).thenReturn(servletContext);
        when(request.getSession().getServletContext().getAttribute("loggedUsers")).thenReturn(loggedUsers);

        User user = new User();

        user.setId(1L);
        user.setFullName("Test User");
        user.setUsername("TestUser");
        user.setEmail("test.user@gmail.com");
        user.setPassword("testUser");
        user.setRoleId(1);
        user.setStatusId(1);

        when(userDao.findByUsername("TestUser")).thenReturn(user);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new LoginCommand(userDao).execute(request);

        verify(session).setAttribute("user", user);
        verify(session).setAttribute("userRole", Role.getRole(user));

        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("password");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void emptyFieldsTest() throws Exception {
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new LoginCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("password");
        verify(request).setAttribute("error", "Login/password cannot be empty");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void nullUserTest() throws Exception {
        when(request.getParameter("username")).thenReturn("TestUser");
        when(request.getParameter("password")).thenReturn("testUser");
        when(userDao.findByUsername("TestUser")).thenReturn(null);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new LoginCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("password");
        verify(request).setAttribute("error", "Cannot find user with such login/password");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void userInUseTest() throws Exception {
        User user = new User();

        user.setId(1L);
        user.setFullName("Test User");
        user.setUsername("TestUser");
        user.setEmail("test.user@gmail.com");
        user.setPassword("testUser");
        user.setRoleId(1);
        user.setStatusId(1);

        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add(user.getUsername());
        when(request.getParameter("username")).thenReturn("TestUser");
        when(request.getParameter("password")).thenReturn("testUser");
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getServletContext()).thenReturn(servletContext);
        when(request.getSession().getServletContext().getAttribute("loggedUsers")).thenReturn(loggedUsers);

        when(userDao.findByUsername("TestUser")).thenReturn(user);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new LoginCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("password");
        verify(request).setAttribute("errorMessage", "This user already in use");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }
}
