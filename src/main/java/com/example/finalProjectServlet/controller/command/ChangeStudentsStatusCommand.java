package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.UserDao;

import javax.servlet.http.HttpServletRequest;

public class ChangeStudentsStatusCommand implements Command {

    private final UserDao userDao;

    public ChangeStudentsStatusCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int statusId = 1;
        if (request.getParameter("action").equals("block")) {
            statusId = 2;
        }
        userDao.updateUserStatus(Long.valueOf(request.getParameter("id")), statusId);
        return "redirect:/students_list";
    }
}
