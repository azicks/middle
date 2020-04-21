package ru.job4j.servlets.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.servlets.User;
import ru.job4j.servlets.UserStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignInController extends HttpServlet {
    private final UserStorage userStorage = UserStorage.getInstance();
    private static final Logger log = LogManager.getLogger(SignInController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User u = userStorage.findByLogin(login);
        if (u != null && u.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(-1);
            session.setAttribute("login", login);
            session.setAttribute("role", u.getRole());
            session.setAttribute("name", u.getName());
            log.trace(String.format("User authentificated  %s %s %s \n %s", login, u.getName(), u.getRole(), session.toString()));
            resp.sendRedirect("/");
        } else {
            log.trace(String.format("Invalid credentials %s %s", login, password));
            req.setAttribute("error", "Invalid credentials");
            doGet(req, resp);
        }
    }
}
