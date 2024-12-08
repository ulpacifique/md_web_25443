package com.auca.library.servlet;

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
import com.auca.library.model.Location;
import com.auca.library.util.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
@WebServlet("/GetCellsServlet")
public class GetCellsServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sectorId = Integer.parseInt(request.getParameter("sectorId"));
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM cells WHERE sector_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, sectorId);
                ResultSet rs = stmt.executeQuery();
                
                JsonArray cells = new JsonArray();
                while (rs.next()) {
                    JsonObject cell = new JsonObject();
                    cell.addProperty("id", rs.getInt("id"));
                    cell.addProperty("name", rs.getString("name"));
                    cells.add(cell);
                }
                
                response.setContentType("application/json");
                response.getWriter().write(cells.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
