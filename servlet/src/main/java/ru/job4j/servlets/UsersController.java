package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UsersController extends HttpServlet {
    private final ValidateService service = ValidateService.getInstance();
    private static final Logger log = LogManager.getLogger(UsersController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String image = req.getParameter("getImage");
        if (image != null && !image.isEmpty()) {
            String id = req.getParameter("id");
            resp.setContentType("image/*");
            resp.setCharacterEncoding("utf-8");
            resp.setHeader("Content-Disposition",
                    String.format("attachment; filename=%s", image));
            String path = getServletContext().getRealPath("/bin/images/");
            File file = new File(path + id + "_" + image);
            try (FileInputStream in = new FileInputStream(file)) {
                resp.getOutputStream().write(in.readAllBytes());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            req.setAttribute("users", service.getUsers());
            req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String action = req.getParameter("action");
        if ("gotoEditView".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            User u = service.getUser(id);
            req.setAttribute("user", u);
            req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
        }
        if ("gotoRemoveView".equals(action)) {
            req.setAttribute("id", req.getParameter("id"));
            req.getRequestDispatcher("/WEB-INF/views/delete.jsp").forward(req, resp);
        }

        if ("delete".equals(action)) {
            String path = getServletContext().getRealPath("/bin/images/");
            service.deleteUser(req.getParameter("id"), path);
        }

        if ("update".equals(action)) {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            service.updateUser(id, name, login, email);
        }
        resp.sendRedirect("/");
    }
}
