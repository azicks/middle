package ru.job4j.servlets.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.servlets.Role;
import ru.job4j.servlets.User;
import ru.job4j.servlets.UserStorage;
import ru.job4j.servlets.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UsersController extends HttpServlet {
    private final ValidateService service = ValidateService.getInstance();
    private final UserStorage userStorage = UserStorage.getInstance();
    private static final Logger log = LogManager.getLogger(UsersController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        if (isRequestForImage(req)) {
            sendImage(req, resp);
        } else {
            String path = getServletContext().getRealPath("/bin/images/");
            req.setAttribute("users", userStorage.findAll());
            req.setAttribute("path", path);
            req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String action = req.getParameter("action");
        if ("gotoEditView".equals(action)) {
            User currentUser = userStorage.findByLogin((String) req.getSession().getAttribute("login"));
            int id = Integer.parseInt(req.getParameter("id"));
            User editedUser = userStorage.findById(id);
            if (editedUser.isRoot() && !currentUser.isRoot()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            req.setAttribute("user", editedUser);
            req.setAttribute("roles", userStorage.getUserRoles());
            req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
        }

        if ("delete".equals(action)) {
            String path = getServletContext().getRealPath("/bin/images/");
            service.deleteUser(req.getParameter("id"), path);
        }

        if ("update".equals(action)) {
            HttpSession session = req.getSession();
            User current = userStorage.findByLogin((String) session.getAttribute("login"));
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            String role = req.getParameter("role");
            String country = req.getParameter("country");
            String city = req.getParameter("city");
            //if try to modify another user and not admin
            if (!Role.ADMIN.equals(session.getAttribute("role")) && current.getId() != Integer.parseInt(id)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            } else {
                //if self-editing
                if (current.getId() == Integer.parseInt(id)) {
                    if (role == null) {
                        role = current.getRole().value();
                    }
                    session.setAttribute("role", Role.getRole(role));
                }
                if (!service.updateUser(id, name, login, email, role, country, city)) {
                    resp.sendError(HttpServletResponse.SC_CONFLICT, "Empty field");
                    return;
                }
            }
        }
        if ("logout".equals(action)) {
            req.getSession().invalidate();
        }
        resp.sendRedirect("/");
    }

    private HttpServletResponse sendImage(HttpServletRequest req, HttpServletResponse resp) {
        String image = req.getParameter("getImage");
        String id = req.getParameter("id");
        resp.setContentType("image/*");
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Content-Disposition", String.format("attachment; filename=%s", image));
        String path = getServletContext().getRealPath("/bin/images/");
        File file = new File(path + id + "_" + image);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                resp.getOutputStream().write(in.readAllBytes());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return resp;
    }

    private boolean isRequestForImage(HttpServletRequest req) {
        String image = req.getParameter("getImage");
        if (image != null && !image.isEmpty()) {
            return true;
        }
        return false;
    }
}
