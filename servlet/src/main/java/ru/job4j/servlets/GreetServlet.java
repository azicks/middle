package ru.job4j.servlets;

import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GreetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String name = req.getParameter("name");
        PrintWriter writer = resp.getWriter();
        if (name == null || name.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write("Empty input");
            return;
        }
        JSONObject answer = new JSONObject();
        answer.put("answer", String.format("Nice to meet you, %s", name));
        writer.write(answer.toJSONString());
    }
}
