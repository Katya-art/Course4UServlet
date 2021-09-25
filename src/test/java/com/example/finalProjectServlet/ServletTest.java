package com.example.finalProjectServlet;

import com.example.finalProjectServlet.controller.Servlet;
import com.example.finalProjectServlet.controller.command.MainCommand;
import com.example.finalProjectServlet.model.dao.CourseDao;
import com.example.finalProjectServlet.model.dao.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

public class ServletTest extends Mockito {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doGetTest() throws Exception {
        when(request.getRequestURI()).thenReturn("redirect:/index.jsp");
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new Servlet().doGet(request, response);

        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }

    @Test
    public void doPostTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/course4u/");
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new Servlet().doPost(request, response);

        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().isEmpty());
    }
}
