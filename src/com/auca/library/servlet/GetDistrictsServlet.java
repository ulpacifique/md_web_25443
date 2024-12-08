package com.auca.library.servlet;
import com.auca.library.model.Location;
import com.auca.library.util.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auca.library.dao.LocationDAO;
@WebServlet("/GetDistrictsServlet")
public class GetDistrictsServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int provinceId = Integer.parseInt(request.getParameter("provinceId"));
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM districts WHERE province_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, provinceId);
                ResultSet rs = stmt.executeQuery();
                
                // Convert result set to JSON
                JsonArray districts = new JsonArray();
                while (rs.next()) {
                    JsonObject district = new JsonObject();
                    district.addProperty("id", rs.getInt("id"));
                    district.addProperty("name", rs.getString("name"));
                    districts.add(district);
                }
                
                response.setContentType("application/json");
                response.getWriter().write(districts.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
