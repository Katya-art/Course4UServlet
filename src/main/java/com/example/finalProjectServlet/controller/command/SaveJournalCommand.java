package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.CourseDao;

import javax.servlet.http.HttpServletRequest;

public class SaveJournalCommand implements Command{

    private final CourseDao courseDao;

    public SaveJournalCommand(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long courseId = Long.parseLong(request.getParameter("id"));
        String[] students = request.getParameterValues("students");
        String[] marks = request.getParameterValues("marks");
        courseDao.saveStudentsMarks(courseId, students, marks);
        return "redirect:/teachers_courses.jsp";
    }
}
