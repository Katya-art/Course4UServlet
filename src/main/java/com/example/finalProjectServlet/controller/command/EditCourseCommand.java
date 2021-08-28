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
        String themeName = request.getParameter("themeName");
        String duration = request.getParameter("duration");
        String teacherId = request.getParameter("teacherId");

        String page = request.getParameter("page");
        String sortField = request.getParameter("sortField");
        String sortDir = request.getParameter("sortDir");
        String teacher = request.getParameter("teacher");
        String theme = request.getParameter("theme");

        if(name == null || name.isEmpty() || themeName == null || themeName.isEmpty() || duration == null ||
                duration.isEmpty() || teacherId == null || teacherId.isEmpty() ){
            request.setAttribute("error", "All fields are required");
            return "/add_course.jsp";
        }

        boolean hasError = false;

        if (name.length() < 6 || name.length() > 255) {
            request.setAttribute("nameError", "sizeCourseFormName");
            hasError = true;
        }

        if (themeName.length() < 6 || themeName.length() > 32) {
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
        courseDao.updateCourse(id, name, themeName, Integer.parseInt(duration), Integer.parseInt(teacherId));
        return String.format("redirect:/index.jsp?page=%s&sortField=%s&sortDir=%s&teacher=%s&theme=%s", page,
                sortField, sortDir, teacher, theme);
    }
}
