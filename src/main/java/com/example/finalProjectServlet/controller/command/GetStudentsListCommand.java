package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.UserDao;

import javax.servlet.http.HttpServletRequest;

public class GetStudentsListCommand implements Command {

    private final UserDao userDao;

    public GetStudentsListCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("studentsList", userDao.findAllByRole(1));
        return "/students_list.jsp";
    }
}
