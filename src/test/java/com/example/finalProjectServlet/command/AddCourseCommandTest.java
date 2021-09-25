package com.example.finalProjectServlet.command;

import com.example.finalProjectServlet.controller.command.AddCourseCommand;
import com.example.finalProjectServlet.model.dao.CourseDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

public class AddCourseCommandTest extends Mockito {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    CourseDao courseDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void completedTest() throws Exception {
        when(request.getParameter("name")).thenReturn("TestCourse");
        when(request.getParameter("theme")).thenReturn("TestTheme");
        when(request.getParameter("duration")).thenReturn("7");
        when(request.getParameter("teacher")).thenReturn("1");
        when(courseDao.save("TestCourse", "TestTheme", 7, 1, 1)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new AddCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("theme");
        verify(request, atLeast(1)).getParameter("duration");
        verify(request, atLeast(1)).getParameter("teacher");
        verify(courseDao).save("TestCourse", "TestTheme", 7,
                1, 1);
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void emptyFieldsTest() throws Exception {
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("theme")).thenReturn("");
        when(request.getParameter("duration")).thenReturn("");
        when(request.getParameter("teacher")).thenReturn("");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new AddCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("theme");
        verify(request, atLeast(1)).getParameter("duration");
        verify(request, atLeast(1)).getParameter("teacher");
        verify(request).setAttribute("error", "All fields are required");

        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void allFieldsHaveErrorsTest() throws Exception {
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("theme")).thenReturn("test");
        when(request.getParameter("duration")).thenReturn("-1");
        when(request.getParameter("teacher")).thenReturn("teacher");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new AddCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("theme");
        verify(request, atLeast(1)).getParameter("duration");
        verify(request, atLeast(1)).getParameter("teacher");
        verify(request).setAttribute("nameError", "sizeCourseFormName");
        verify(request).setAttribute("themeError", "sizeCourseFormTheme");
        verify(request).setAttribute("durationError", "wrongDuration");

        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }
}
