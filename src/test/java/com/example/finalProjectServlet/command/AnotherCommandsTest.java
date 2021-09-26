package com.example.finalProjectServlet.command;

import com.example.finalProjectServlet.controller.command.*;
import com.example.finalProjectServlet.model.dao.CourseDao;
import com.example.finalProjectServlet.model.dao.UserDao;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class AnotherCommandsTest extends Mockito {

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

    @Mock
    CourseDao courseDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void changeStudentsStatusCommandTest() throws Exception {
        when(request.getParameter("action")).thenReturn("block");
        when(request.getParameter("id")).thenReturn("2");
        when(userDao.updateUserStatus(2L, 2)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new ChangeStudentsStatusCommand(userDao).execute(request);

        verify(request, atLeast(1)).getParameter("action");
        verify(request, atLeast(1)).getParameter("id");
        verify(userDao, atLeast(1)).updateUserStatus(2L, 2);
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void deleteCourseCommandTest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sortField")).thenReturn("name");
        when(request.getParameter("sortDir")).thenReturn("asc");
        when(request.getParameter("teacher")).thenReturn("test");
        when(request.getParameter("theme")).thenReturn("test");
        when(courseDao.deleteCourse(1L)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new DeleteCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("page");
        verify(request, atLeast(1)).getParameter("sortField");
        verify(request, atLeast(1)).getParameter("sortDir");
        verify(request, atLeast(1)).getParameter("teacher");
        verify(request, atLeast(1)).getParameter("theme");
        verify(courseDao, atLeast(1)).deleteCourse(1L);
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void enrollCourseCommandTest() throws Exception {
        when(request.getParameter("course_id")).thenReturn("1");
        when(request.getParameter("student_id")).thenReturn("2");
        when(courseDao.addStudentToCourse(1L, 2L, 6L)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new EnrollCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("course_id");
        verify(request, atLeast(1)).getParameter("student_id");
        verify(courseDao, atLeast(1)).addStudentToCourse(1L, 2L, 6L);
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void finishCourseCommandTest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(courseDao.updateCourseCondition(1L, 3)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new FinishCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("id");
        verify(courseDao, atLeast(1)).updateCourseCondition(1L, 3);
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void getStudentsListCommandTest() throws Exception {
        User user1 = new User("Test User1", "TestUser1", "test.user1@gmail.com",
                "testUser1", 1, 1);
        user1.setId(1L);

        User user2 = new User("Test User2", "TestUser2", "test.user2@gmail.com",
                "testUser2", 1, 1);;
        user2.setId(2L);

        List<User> students = new ArrayList<>();
        students.add(user1);
        students.add(user2);

        when(userDao.findAllByRole(1)).thenReturn(students);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new GetStudentsListCommand(userDao).execute(request);

        verify(userDao, atLeast(1)).findAllByRole(1);
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void logOutCommandTest() throws Exception {
        User user = new User();

        user.setUsername("TestUser");
        user.setPassword("testUser");
        user.setRoleId(1);

        Set<String> loggedUsers = new HashSet<>();

        loggedUsers.add(user.getUsername());

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getServletContext()).thenReturn(servletContext);
        when(request.getSession().getServletContext().getAttribute("loggedUsers")).thenReturn(loggedUsers);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new LogOutCommand().execute(request);

        verify(session, atLeast(1)).getAttribute("user");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void mainCommandTest() throws Exception {

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new MainCommand().execute(request);

        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void saveJournalCommandTest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameterValues("students")).thenReturn(new String[]{"student1"});
        when(request.getParameterValues("marks")).thenReturn(new String[]{"1"});

        when(courseDao.saveStudentsMarks(1L, new String[]{"student1"}, new String[]{"1"})).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new SaveJournalCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameterValues("students");
        verify(request, atLeast(1)).getParameterValues("marks");
        verify(courseDao, atLeast(1)).saveStudentsMarks(1L, new String[]{"student1"},
                new String[]{"1"});
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void startCourseCommandTest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(courseDao.updateCourseCondition(1L, 2)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new StartCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("id");
        verify(courseDao, atLeast(1)).updateCourseCondition(1L, 2);
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }
}
