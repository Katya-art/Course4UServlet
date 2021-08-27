package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.CourseDao;

import javax.servlet.http.HttpServletRequest;

public class FinishCourseCommand implements Command {

    private final CourseDao courseDao;

    public FinishCourseCommand(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        courseDao.updateCourseCondition(Long.parseLong(request.getParameter("id")), 3);
        return "redirect:/teachers_courses.jsp";
    }
}
