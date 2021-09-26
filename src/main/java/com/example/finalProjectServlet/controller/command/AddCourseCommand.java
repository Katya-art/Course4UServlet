package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.CourseDao;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddCourseCommand implements Command {

    private final CourseDao courseDao;

    public AddCourseCommand(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String theme = request.getParameter("theme");
        String duration = request.getParameter("duration");
        String teacherId = request.getParameter("teacher");
        Locale locale = new Locale("en");
        if (request.getSession().getAttribute("lang") != null) {
            locale = new Locale((String) request.getSession().getAttribute("lang"));
        }

        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        if(name == null || name.isEmpty() || theme == null || theme.isEmpty() || duration == null ||
                duration.isEmpty() || teacherId == null || teacherId.isEmpty() ){
            request.setAttribute("error", messages.getString("required"));
            return "/add_course.jsp";
        }

        boolean hasError = false;

        if (name.length() < 6 || name.length() > 255) {
            request.setAttribute("nameError", messages.getString("sizeCourseFormName"));
            hasError = true;
        }

        if (theme.length() < 6 || theme.length() > 32) {
            request.setAttribute("themeError", messages.getString("sizeCourseFormTheme"));
            hasError = true;
        }

        if (Integer.parseInt(duration) <= 0) {
            request.setAttribute("durationError", messages.getString("wrongDuration"));
            hasError = true;
        }

        if (Integer.parseInt(teacherId) == 0) {
            request.setAttribute("teacherError", messages.getString("selectTeacher"));
            hasError = true;
        }

        if (hasError) {
            return "/add_course.jsp";
        }

        try {
            System.out.println(teacherId);
            courseDao.save(name, theme, Integer.parseInt(duration), Integer.parseInt(teacherId), 1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return "redirect:/index.jsp";
    }
}
