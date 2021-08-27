package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.CourseDao;

import javax.servlet.http.HttpServletRequest;

public class StartCourseCommand implements Command {

    private final CourseDao courseDao;

    public StartCourseCommand(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        courseDao.updateCourseCondition(Long.parseLong(request.getParameter("id")), 2);
        return "redirect:/teachers_courses.jsp";
    }
}
