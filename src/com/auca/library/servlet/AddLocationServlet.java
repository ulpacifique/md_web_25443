package com.auca.library.servlet;

import com.auca.library.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/AddLocationServlet")
public class AddLocationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String sector = request.getParameter("sector");
        String cell = request.getParameter("cell");
        String village = request.getParameter("village");

        try {
            // Insert into the location table
            addLocation(province, district, sector, cell, village);
            response.sendRedirect("locationSuccess.jsp");  // Redirect to success page
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("locationError.jsp");  // Redirect to error page in case of failure
        }
    }

    private void addLocation(String province, String district, String sector, String cell, String village) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Update the query to match the column names in the locations table
            String query = "INSERT INTO locations (province, district, sector, cell, village) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, province);
                stmt.setString(2, district);
                stmt.setString(3, sector);
                stmt.setString(4, cell);
                stmt.setString(5, village);
                stmt.executeUpdate();
            }
        }
    }
}
