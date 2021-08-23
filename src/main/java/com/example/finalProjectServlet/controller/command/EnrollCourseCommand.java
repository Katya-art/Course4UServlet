package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.CourseDao;

import javax.servlet.http.HttpServletRequest;

public class EnrollCourseCommand implements Command {

    private final CourseDao courseDao;

    public EnrollCourseCommand(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        courseDao.addStudentToCourse(Long.parseLong(request.getParameter("course_id")),
                Long.parseLong(request.getParameter("student_id")), 6L);
        return "redirect:/index.jsp";
    }
}
