package com.example.finalProjectServlet.controller.command;

import javax.servlet.http.HttpServletRequest;

public class MainCommand implements Command {

    public MainCommand() {}

    @Override
    public String execute(HttpServletRequest request) {
        return "index.jsp";
    }
}
