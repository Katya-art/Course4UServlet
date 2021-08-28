package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.CourseDao;

import javax.servlet.http.HttpServletRequest;

public class DeleteCourseCommand implements Command{

    private final CourseDao courseDao;

    public DeleteCourseCommand(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        courseDao.deleteCourse(Long.parseLong(request.getParameter("id")));
        String page = request.getParameter("page");
        String sortField = request.getParameter("sortField");
        String sortDir = request.getParameter("sortDir");
        String teacher = request.getParameter("teacher");
        String theme = request.getParameter("theme");
        return String.format("redirect:/index.jsp?page=%s&sortField=%s&sortDir=%s&teacher=%s&theme=%s", page,
                sortField, sortDir, teacher, theme);
    }
}
