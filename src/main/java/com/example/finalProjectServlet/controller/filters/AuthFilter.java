package com.example.finalProjectServlet.controller.filters;

import com.example.finalProjectServlet.controller.command.LoginCommand;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    private static final Logger log = Logger.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        ServletContext context = request.getServletContext();
        log.trace(session);
        log.trace(session.getAttribute("role"));
        log.trace(context.getAttribute("loggedUsers"));
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
