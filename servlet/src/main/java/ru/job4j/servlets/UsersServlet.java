package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UsersServlet extends HttpServlet {
    private final ValidateService service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder body = new StringBuilder();
        StringBuilder rows = new StringBuilder();
        for (User u : service.getUsers()) {
            rows.append("<tr>")
                    .append("<td>" + u.getId() + "</td>")
                    .append("<td>" + "User: " + u.getName() + "</td>")
                    .append("<td>" + "Login: " + u.getLogin() + "</td>")
                    .append("<td>" + "Email: " + u.getEmail() + "</td>")
                    .append("<td>" + "Created: " + u.getCreateDate() + "</td>")
                    .append("<td>")
                    .append("        <form action=\"/edit\" method=\"post\">" +
                            "            <input type=\"hidden\" name=\"id\" value=\"" + u.getId() + "\"/>" +
                            "            <input type=\"submit\" value=\"Edit\" name=\"action\">" +
                            "            <input type=\"submit\" value=\"Remove\" name=\"action\">" +
                            "        </form>")

                    .append("</td>")
                    .append("</tr>");
        }

        if (service.getUsers().size() > 0) {
            body.append("<table border=\"1\" cellpadding=\"5\">")
                    .append(rows)
                    .append("</table>");
        }
        body.append("<br><form action=\"/create\" method=\"post\">\n" +
                "<input type=\"submit\" value=\"Add new user\">\n" +
                "</form>");
        writer.append("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Users list</title>" +
                "</head>" +
                "<body>" +
                body.toString() +
                "</body>" +
                "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
