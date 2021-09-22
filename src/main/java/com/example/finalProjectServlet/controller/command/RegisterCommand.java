package com.example.finalProjectServlet.controller.command;

import com.example.finalProjectServlet.model.dao.UserDao;
import com.example.finalProjectServlet.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

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

        Locale locale = new Locale("en");
        if (request.getSession().getAttribute("lang") != null) {
            locale = new Locale((String) request.getSession().getAttribute("lang"));
        }

        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        if(fullName == null || fullName.isEmpty() || username == null || username.isEmpty() || email == null ||
                email.isEmpty() || password == null || password.isEmpty() ){
            request.setAttribute("error", messages.getString("required"));
            return "/register.jsp";
        }

        boolean hasError = false;

        if (fullName.length() < 6 || fullName.length() > 32) {
            request.setAttribute("fullNameError", messages.getString("sizeUserFormFullName"));
            hasError = true;
        }

        if (username.length() < 6 || username.length() > 32) {
            request.setAttribute("usernameError", messages.getString("sizeUserFormUsername"));
            hasError = true;
        }

        if (!email.trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            request.setAttribute("emailError", messages.getString("wrongEmail"));
            hasError = true;
        }

        if (password.length() < 6 || password.length() > 32) {
            request.setAttribute("passwordError", messages.getString("sizeUserFormPassword"));
            hasError = true;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("passwordError", messages.getString("differentUserFormPassword"));
            hasError = true;
        }

        if (hasError) {
            return String.format("/register.jsp?role=%s", request.getParameter("role"));
        }

        if (userDao.findByUsername(username) != null) {
            request.setAttribute("usernameError", messages.getString("usernameExists"));
            return String.format("/register.jsp?role=%s", request.getParameter("role"));
        }

        if (userDao.findByEmail(email) != null) {
            request.setAttribute("emailError", messages.getString("emailExists"));
            return String.format("/register.jsp?role=%s", request.getParameter("role"));
        }

        try {
            int roleId = 1;
            if (request.getParameter("role").equals("teacher")) {
                roleId = 3;
            }
            userDao.save(fullName, username, email, password, roleId, 1);
            User user = userDao.findByUsername(username);
            if (roleId == 1) {
                request.getSession().setAttribute("user", user);
                CommandUtility.checkUserIsLogged(request, username);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return "redirect:/index.jsp";
    }
}
