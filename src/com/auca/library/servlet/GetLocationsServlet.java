package com.auca.library.servlet;

import com.auca.library.dao.LocationDAO;
import com.auca.library.model.Location;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetLocationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String level = req.getParameter("level");

        LocationDAO locationDAO = new LocationDAO();
        List<Location> locations = locationDAO.getLocationsByLevel(level); // Updated method call

        Gson gson = new Gson();
        String json = gson.toJson(locations);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}