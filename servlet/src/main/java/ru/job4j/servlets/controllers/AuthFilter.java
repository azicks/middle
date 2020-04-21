package ru.job4j.servlets.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter extends HttpFilter {
    private static final Logger log = LogManager.getLogger(AuthFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        log.trace(String.format("%s\n%s\n%s\n%s\n", session.toString(), req.getRequestURI(), session.getAttribute("login"), session.getAttribute("role")));
        if (!req.getRequestURI().contains("/signin")) {
            if (session.getAttribute("login") == null) {
                res.sendRedirect("/signin");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
