package ru.job4j.servlets.controllers;

import ru.job4j.servlets.Role;
import ru.job4j.servlets.User;
import ru.job4j.servlets.UserStorage;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter extends HttpFilter {
    private final UserStorage userStorage = UserStorage.getInstance();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Role role = (Role) session.getAttribute("role");
        if (Role.ADMIN != role) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(req, res);
    }
}
