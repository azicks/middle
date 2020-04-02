package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserUpdateServlet extends HttpServlet {
    private final ValidateService service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType ("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        resp.setCharacterEncoding ("UTF-8");
        String action = req.getParameter("action");
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            if ("Remove".equals(action)) {
                writer.append(removeForm(Integer.parseInt(id)));
            }
            if ("Edit".equals(action)) {
                writer.append(editForm(Integer.parseInt(id)));
            }
        }
        writer.flush();
    }

    private String removeForm(int id) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>Sure to remove?</title>\n" +
                "</head>\n");
        html.append(
                "<body>\n" +
                "<form method=\"post\" action=\"/dispatch\">\n" +
                "<b><p>Are you sure to delete user with id = " + id + "</p></b>\n" +
                "<input type=\"hidden\" name=\"id\" value=\"" + id + "\">" +
                "<button name=\"action\" value=\"delete\" type=\"submit\">Yes</button>" +
                "<input type=\"submit\" value=\"No\" name=\"action\">\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>");
        return html.toString();
    }

    private String editForm(int id) {
        StringBuilder html = new StringBuilder();
        User u = service.getUser(id);
        html.append("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Edit user</title>" +
                "</head>");
        html.append(
                "<body>" +
                "<form action=\"/dispatch\" method=\"post\">" +
                "<b><p>Edit user wit id = " + id + "</p></b>" +
                "<input type=\"text\" name=\"name\" value=\"" + u.getName() + "\">" +
                "<input type=\"text\" name=\"login\" value=\"" + u.getLogin() + "\">" +
                "<input type=\"text\" name=\"email\" value=\"" + u.getEmail() + "\">" +
                "<input type=\"hidden\" name=\"id\" value=\"" + id + "\">" +
                "<button name=\"action\" value=\"update\" type=\"submit\">Ok</button>" +
                "</form>\n" +
                "</body>\n" +
                "</html>");
        return html.toString();
    }
}
