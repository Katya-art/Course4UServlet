package com.example.finalProjectServlet.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.finalProjectServlet.model.entity.User;
import org.apache.log4j.Logger;

public class LogOutCommand implements Command {

    private static final Logger log = Logger.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command starts");

        HttpSession session = request.getSession(false);
        if (session != null) {
            CommandUtility.deleteFromLogged(request, ((User) session.getAttribute("user")).getUsername());
            session.invalidate();
        }

        log.debug("Command finished");
        return "/login.jsp";
    }

}
