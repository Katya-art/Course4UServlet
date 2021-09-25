package com.example.finalProjectServlet.command;

import com.example.finalProjectServlet.controller.command.RegisterCommand;
import com.example.finalProjectServlet.model.dao.UserDao;
import com.example.finalProjectServlet.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

public class RegisterCommandTest extends Mockito {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    UserDao userDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void completedTest() throws Exception {
        when(request.getParameter("fullName")).thenReturn("Test User");
        when(request.getParameter("username")).thenReturn("TestUser");
        when(request.getParameter("email")).thenReturn("test.user@gmail.com");
        when(request.getParameter("password")).thenReturn("testUser");
        when(request.getParameter("confirmPassword")).thenReturn("testUser");

        when(request.getSession()).thenReturn(session);

        when(request.getParameter("role")).thenReturn("teacher");

        when(userDao.findByUsername("TestUser")).thenReturn(null);
        when(userDao.findByEmail("test.user@gmail.com")).thenReturn(null);

        when(userDao.save("Test User", "TestUser", "test.user@gmail.com", "testUser",
                1, 1)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new RegisterCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("fullName");
        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("email");
        verify(request, atLeast(1)).getParameter("password");
        verify(request, atLeast(1)).getParameter("confirmPassword");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void fieldsEmptyTest() throws Exception {
        when(request.getParameter("fullName")).thenReturn("");
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("email")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("confirmPassword")).thenReturn("");

        when(request.getSession()).thenReturn(session);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new RegisterCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("fullName");
        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("email");
        verify(request, atLeast(1)).getParameter("password");
        verify(request, atLeast(1)).getParameter("confirmPassword");
        verify(request).setAttribute("error", "All fields are required");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void allFieldsHaveErrorsTest() throws Exception {
        when(request.getParameter("fullName")).thenReturn("Test");
        when(request.getParameter("username")).thenReturn("Test");
        when(request.getParameter("email")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getParameter("confirmPassword")).thenReturn("test");

        when(request.getSession()).thenReturn(session);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new RegisterCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("fullName");
        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("email");
        verify(request, atLeast(1)).getParameter("password");
        verify(request, atLeast(1)).getParameter("confirmPassword");

        verify(request).setAttribute("fullNameError", "Full name must be between 6 and 32 characters");
        verify(request).setAttribute("usernameError", "Username must be between 6 and 32 characters");
        verify(request).setAttribute("emailError", "Wrong email address");
        verify(request).setAttribute("passwordError", "Password must be between 6 and 32 characters");

        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void differentPasswordTest() throws Exception {
        when(request.getParameter("fullName")).thenReturn("Test User");
        when(request.getParameter("username")).thenReturn("TestUser");
        when(request.getParameter("email")).thenReturn("test.user@gmail.com");
        when(request.getParameter("password")).thenReturn("testUser1");
        when(request.getParameter("confirmPassword")).thenReturn("testUser2");

        when(request.getSession()).thenReturn(session);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new RegisterCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("fullName");
        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("email");
        verify(request, atLeast(1)).getParameter("password");
        verify(request, atLeast(1)).getParameter("confirmPassword");
        verify(request).setAttribute("passwordError", "Passwords don`t mach");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void usernameExistsTest() throws Exception {
        when(request.getParameter("fullName")).thenReturn("Test User");
        when(request.getParameter("username")).thenReturn("TestUser");
        when(request.getParameter("email")).thenReturn("test.user@gmail.com");
        when(request.getParameter("password")).thenReturn("testUser");
        when(request.getParameter("confirmPassword")).thenReturn("testUser");

        when(request.getSession()).thenReturn(session);

        when(request.getParameter("role")).thenReturn("teacher");

        when(userDao.findByUsername("TestUser")).thenReturn(new User("Test User", "TestUser",
                "test.user@gmail.com", "testUser", 1, 1));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new RegisterCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("fullName");
        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("email");
        verify(request, atLeast(1)).getParameter("password");
        verify(request, atLeast(1)).getParameter("confirmPassword");
        verify(request).setAttribute("usernameError", "Account with such username already exists");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void emailExistsTest() throws Exception {
        when(request.getParameter("fullName")).thenReturn("Test User");
        when(request.getParameter("username")).thenReturn("TestUser");
        when(request.getParameter("email")).thenReturn("test.user@gmail.com");
        when(request.getParameter("password")).thenReturn("testUser");
        when(request.getParameter("confirmPassword")).thenReturn("testUser");

        when(request.getSession()).thenReturn(session);

        when(request.getParameter("role")).thenReturn("teacher");

        when(userDao.findByEmail("test.user@gmail.com")).thenReturn(new User("Test User", "TestUser",
                "test.user@gmail.com", "testUser", 1, 1));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new RegisterCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("fullName");
        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("email");
        verify(request, atLeast(1)).getParameter("password");
        verify(request, atLeast(1)).getParameter("confirmPassword");
        verify(request).setAttribute("emailError", "Account with such email already exists");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }
}
