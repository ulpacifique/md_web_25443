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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@WebServlet("/GetSectorsServlet")
public class GetSectorsServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int districtId = Integer.parseInt(request.getParameter("districtId"));
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM sectors WHERE district_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, districtId);
                ResultSet rs = stmt.executeQuery();
                
                JsonArray sectors = new JsonArray();
                while (rs.next()) {
                    JsonObject sector = new JsonObject();
                    sector.addProperty("id", rs.getInt("id"));
                    sector.addProperty("name", rs.getString("name"));
                    sectors.add(sector);
                }
                
                response.setContentType("application/json");
                response.getWriter().write(sectors.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
