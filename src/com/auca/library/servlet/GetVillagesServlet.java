package com.auca.library.servlet;

import com.auca.library.dao.LocationDAO;
import com.auca.library.model.Location;
import com.auca.library.util.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/GetVillagesServlet")
public class GetVillagesServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cellId = Integer.parseInt(request.getParameter("cellId"));
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM villages WHERE cell_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, cellId);
                ResultSet rs = stmt.executeQuery();
                
                JsonArray villages = new JsonArray();
                while (rs.next()) {
                    JsonObject village = new JsonObject();
                    village.addProperty("id", rs.getInt("id"));
                    village.addProperty("name", rs.getString("name"));
                    villages.add(village);
                }
                
                response.setContentType("application/json");
                response.getWriter().write(villages.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
