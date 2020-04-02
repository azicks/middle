package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(editForm());
        writer.flush();
    }

    private String editForm() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Create user</title>" +
                "</head>");
        html.append(
                "<body>" +
                        "<form action=\"/dispatch\" method=\"post\">" +
                        "<b><p>Create new user: </p></b>" +
                        "<input type=\"text\" name=\"name\" \">" +
                        "<input type=\"text\" name=\"login\" \">" +
                        "<input type=\"text\" name=\"email\" \">" +
                        "<button name=\"action\" value=\"add\" type=\"submit\">Ok</button>" +
                        "</form>\n" +
                        "</body>\n" +
                        "</html>");
        return html.toString();
    }
}
