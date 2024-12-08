package com.auca.library.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auca.library.dao.LocationDAO;
import com.auca.library.model.Location;
import com.auca.library.util.DatabaseConnection;
import com.google.gson.Gson;

@WebServlet("/addDistrict")
public class DistrictServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String districtName = request.getParameter("name");
        int provinceId = Integer.parseInt(request.getParameter("provinceId"));

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO districts (name, province_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, districtName);
                stmt.setInt(2, provinceId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("addLocation.jsp");
    }
}
