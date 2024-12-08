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

@WebServlet("/addSector")
public class SectorServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sectorName = request.getParameter("name");
        int districtId = Integer.parseInt(request.getParameter("districtId"));

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO sectors (name, district_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, sectorName);
                stmt.setInt(2, districtId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("addLocation.jsp");
    }
}
