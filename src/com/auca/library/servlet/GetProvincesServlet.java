package com.auca.library.servlet;
import com.auca.library.model.Location;
import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auca.library.dao.LocationDAO;
import com.google.gson.Gson;

@WebServlet("/getProvinces")
public class GetProvincesServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Location> provinces = LocationDAO.getProvinces();
        String jsonResponse = new Gson().toJson(provinces);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
