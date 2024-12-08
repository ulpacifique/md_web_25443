package com.auca.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auca.library.util.DatabaseConnection;

@WebServlet("/GetProvinceByPhoneServlet")
public class GetProvinceByPhoneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String phoneNumber = request.getParameter("phoneNumber");
        
        // For debugging
        System.out.println("Received phone number: " + phoneNumber);

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            out.println("Error: Phone number is required");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                out.println("Error: Database connection failed");
                return;
            }

            // Updated SQL to use the new table structure with direct location IDs
            String sql = "SELECT p.name AS province_name " +
                         "FROM users u " +
                         "JOIN provinces p ON u.province_id = p.id " +
                         "WHERE u.phone_number = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, phoneNumber);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String provinceName = rs.getString("province_name");
                        out.println("Province: " + provinceName);
                    } else {
                        out.println("Error: No user found with this phone number");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error: Database error - " + e.getMessage());
        }
    }

    // Optional: Add a method to get more detailed location information
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String phoneNumber = request.getParameter("phoneNumber");
        
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            out.println("Error: Phone number is required");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                out.println("Error: Database connection failed");
                return;
            }

            // Comprehensive location details query
            String sql = "SELECT " +
                         "p.name AS province_name, " +
                         "d.name AS district_name, " +
                         "s.name AS sector_name, " +
                         "c.name AS cell_name, " +
                         "v.name AS village_name " +
                         "FROM users u " +
                         "JOIN provinces p ON u.province_id = p.id " +
                         "JOIN districts d ON u.district_id = d.id " +
                         "JOIN sectors s ON u.sector_id = s.id " +
                         "JOIN cells c ON u.cell_id = c.id " +
                         "JOIN villages v ON u.village_id = v.id " +
                         "WHERE u.phone_number = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, phoneNumber);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Detailed location information
                        out.println("Province: " + rs.getString("province_name"));
                        out.println("District: " + rs.getString("district_name"));
                        out.println("Sector: " + rs.getString("sector_name"));
                        out.println("Cell: " + rs.getString("cell_name"));
                        out.println("Village: " + rs.getString("village_name"));
                    } else {
                        out.println("Error: No user found with this phone number");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error: Database error - " + e.getMessage());
        }
    }
}