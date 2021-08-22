package com.example.finalProjectServlet.controller;

import com.example.finalProjectServlet.controller.command.*;
import com.example.finalProjectServlet.model.dao.CourseDao;
import com.example.finalProjectServlet.model.dao.UserDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@WebServlet
public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("index", new MainCommand());
        commands.put("register", new RegisterCommand(new UserDao()));
        commands.put("logout", new LogOutCommand());
        commands.put("login", new LoginCommand());
        commands.put("students_list", new GetStudentsListCommand(new UserDao()));
        commands.put("change_student_status", new ChangeStudentsStatusCommand(new UserDao()));
        commands.put("add_course", new AddCourseCommand(new CourseDao()));
        commands.put("delete_course", new DeleteCourseCommand(new CourseDao()));
        commands.put("edit_course", new EditCourseCommand(new CourseDao()));
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
        //response.getWriter().print("Hello from servlet");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/course4u/" , "");
        Command command = commands.getOrDefault(path, (r)->"/index.jsp");
        System.out.println("processRequest method");
        String page = command.execute(request);
        //request.getRequestDispatcher(page).forward(request,response);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/course4u"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
