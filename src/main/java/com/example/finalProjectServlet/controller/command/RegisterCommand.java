package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.UserDao;
import com.example.finalProjectServlet.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command {

    private final UserDao userDao;

    public RegisterCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if(fullName == null || fullName.isEmpty() || username == null || username.isEmpty() || email == null ||
                email.isEmpty() || password == null || password.isEmpty() ){
            request.setAttribute("error", "All fields are required");
            return "/register.jsp";
        }

        boolean hasError = false;

        if (fullName.length() < 6 || fullName.length() > 32) {
            request.setAttribute("fullNameError", "SizeUserFormUsername");
            hasError = true;
        }

        if (username.length() < 6 || username.length() > 32) {
            request.setAttribute("usernameError", "SizeUserFormUsername");
            hasError = true;
        }

        if (!email.trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            request.setAttribute("emailError", "WrongEmail");
            hasError = true;
        }

        if (password.length() < 6 || password.length() > 32) {
            request.setAttribute("passwordError", "sizeUserFormPassword");
            hasError = true;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("passwordError", "differentUserFormPassword");
        }

        if (hasError || userDao.findByUsername(username) != null) {
            return "/register.jsp";
        }

        try {
            userDao.save(fullName, username, email, password, 1, 1);
            User user = new User(fullName, username, email, password, 1, 1);
            request.getSession().setAttribute("user", user);
            CommandUtility.checkUserIsLogged(request, username);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return "/index.jsp";
    }
}
