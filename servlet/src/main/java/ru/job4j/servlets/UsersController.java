package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersController extends HttpServlet {
    private final ValidateService service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", service.getUsers());
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String action = req.getParameter("action");
        if ("Edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            User u = service.getUser(id);
            req.setAttribute("user", u);
            req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
        }
        if ("Remove".equals(action)) {
            req.setAttribute("id", req.getParameter("id"));
            req.getRequestDispatcher("/WEB-INF/views/delete.jsp").forward(req, resp);
        }
        if ("Create".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
        }
        service.dispatch(req.getParameterMap());
        resp.sendRedirect("/");
    }
}
