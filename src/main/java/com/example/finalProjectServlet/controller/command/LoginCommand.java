package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.UserDao;
import com.example.finalProjectServlet.model.entity.Role;
import com.example.finalProjectServlet.model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        log.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain login and password from the request
        String username = request.getParameter("username");
        log.trace("Request parameter: loging --> " + username);

        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String forward = "/WEB-INF/error.jsp";

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        User user = new UserDao().findByUsername(username);
        log.trace("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        if (CommandUtility.checkUserIsLogged(request, username)) {
            errorMessage = "This user already in use";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        Role userRole = Role.getRole(user);
        log.trace("userRole --> " + userRole);

        forward = "index.jsp";

        session.setAttribute("user", user);
        log.trace("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", userRole);
        log.trace("Set the session attribute: userRole --> " + userRole);

        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());


        log.debug("Command finished");
        return forward;
    }

}
