package com.example.finalProjectServlet.command;

import com.example.finalProjectServlet.controller.command.EditCourseCommand;
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

public class EditCourseCommandTest extends Mockito {

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
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Test Course");
        when(request.getParameter("themeName")).thenReturn("Test Theme");
        when(request.getParameter("duration")).thenReturn("6");
        when(request.getParameter("teacherId")).thenReturn("1");

        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sortField")).thenReturn("name");
        when(request.getParameter("sortDir")).thenReturn("asc");
        when(request.getParameter("teacher")).thenReturn("test");
        when(request.getParameter("theme")).thenReturn("test");
        when(courseDao.updateCourse(1L, "Test Course", "Test Theme", 6, 1))
                .thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new EditCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("themeName");
        verify(request, atLeast(1)).getParameter("duration");
        verify(request, atLeast(1)).getParameter("teacherId");

        verify(request, atLeast(1)).getParameter("page");
        verify(request, atLeast(1)).getParameter("sortField");
        verify(request, atLeast(1)).getParameter("sortDir");
        verify(request, atLeast(1)).getParameter("teacher");
        verify(request, atLeast(1)).getParameter("theme");
        verify(courseDao, atLeast(1)).updateCourse(1L, "Test Course", "Test Theme",
                6, 1);
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void emptyFieldsTest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("themeName")).thenReturn("");
        when(request.getParameter("duration")).thenReturn("");
        when(request.getParameter("teacherId")).thenReturn("");

        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sortField")).thenReturn("name");
        when(request.getParameter("sortDir")).thenReturn("asc");
        when(request.getParameter("teacher")).thenReturn("test");
        when(request.getParameter("theme")).thenReturn("test");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new EditCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("themeName");
        verify(request, atLeast(1)).getParameter("duration");
        verify(request, atLeast(1)).getParameter("teacherId");

        verify(request, atLeast(1)).getParameter("page");
        verify(request, atLeast(1)).getParameter("sortField");
        verify(request, atLeast(1)).getParameter("sortDir");
        verify(request, atLeast(1)).getParameter("teacher");
        verify(request, atLeast(1)).getParameter("theme");
        verify(request).setAttribute("error", "All fields are required");
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void allFieldsHaveErrorsTest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("themeName")).thenReturn("test");
        when(request.getParameter("duration")).thenReturn("-1");
        when(request.getParameter("teacherId")).thenReturn("1");

        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sortField")).thenReturn("name");
        when(request.getParameter("sortDir")).thenReturn("asc");
        when(request.getParameter("teacher")).thenReturn("test");
        when(request.getParameter("theme")).thenReturn("test");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new EditCourseCommand(courseDao).execute(request);

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("themeName");
        verify(request, atLeast(1)).getParameter("duration");
        verify(request, atLeast(1)).getParameter("teacherId");

        verify(request, atLeast(1)).getParameter("page");
        verify(request, atLeast(1)).getParameter("sortField");
        verify(request, atLeast(1)).getParameter("sortDir");
        verify(request, atLeast(1)).getParameter("teacher");
        verify(request, atLeast(1)).getParameter("theme");

        verify(request).setAttribute("nameError", "sizeCourseFormName");
        verify(request).setAttribute("themeError", "sizeCourseFormTheme");
        verify(request).setAttribute("durationError", "wrongDuration");

        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }
}
