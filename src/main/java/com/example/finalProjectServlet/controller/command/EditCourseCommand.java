package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.CourseDao;

import javax.servlet.http.HttpServletRequest;

public class EditCourseCommand implements Command{

    private final CourseDao courseDao;

    public EditCourseCommand(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String theme = request.getParameter("theme");
        String duration = request.getParameter("duration");
        String teacherId = request.getParameter("teacher");

        if(name == null || name.isEmpty() || theme == null || theme.isEmpty() || duration == null ||
                duration.isEmpty() || teacherId == null || teacherId.isEmpty() ){
            request.setAttribute("error", "All fields are required");
            return "/add_course.jsp";
        }

        boolean hasError = false;

        if (name.length() < 6 || name.length() > 255) {
            request.setAttribute("nameError", "sizeCourseFormName");
            hasError = true;
        }

        if (theme.length() < 6 || theme.length() > 32) {
            request.setAttribute("themeError", "sizeCourseFormTheme");
            hasError = true;
        }

        if (Integer.parseInt(duration) <= 0) {
            request.setAttribute("durationError", "wrongDuration");
            hasError = true;
        }

        if (hasError) {
            return "/add_course.jsp";
        }
        courseDao.updateCourse(id, name, theme, Integer.parseInt(duration), Integer.parseInt(teacherId));
        return "redirect:/index.jsp";
    }
}
