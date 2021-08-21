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
        return "redirect:/index.jsp";
    }
}
