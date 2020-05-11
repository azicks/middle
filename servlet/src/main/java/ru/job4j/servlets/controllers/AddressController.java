package ru.job4j.servlets.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.job4j.servlets.AddressStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddressController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        JSONObject object = new JSONObject();
        JSONArray cities = new JSONArray();
        cities.addAll(AddressStorage.getInstance().getAllCities());
        object.put("cities", cities);
        resp.getWriter().write(object.toJSONString());
    }
}
