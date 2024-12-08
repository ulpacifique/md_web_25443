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
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("Received POST request to CreateAccountServlet");
        System.out.println("Content Type: " + request.getContentType());
        System.out.println("Request Parameters:");
        for (String paramName : request.getParameterMap().keySet()) {
            String[] paramValues = request.getParameterValues(paramName);
            for (String paramValue : paramValues) {
                System.out.println(paramName + " = " + paramValue);
            }
        }
        
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Get and validate parameters
        String username = validateParameter(request, "username", out);
        String email = validateParameter(request, "email", out);
        String password = validateParameter(request, "password", out);
        String phoneNumber = validateParameter(request, "phoneNumber", out);
        
        // Location IDs from dropdowns
        String provinceId = validateParameter(request, "province", out);
        String districtId = validateParameter(request, "district", out);
        String sectorId = validateParameter(request, "sector", out);
        String cellId = validateParameter(request, "cell", out);
        String villageId = validateParameter(request, "village", out);
        
        String role = validateParameter(request, "role", out);

        // Debug logging
        System.out.println("Received parameters:");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Password: [PROTECTED]");
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Province ID: " + provinceId);
        System.out.println("District ID: " + districtId);
        System.out.println("Sector ID: " + sectorId);
        System.out.println("Cell ID: " + cellId);
        System.out.println("Village ID: " + villageId);
        System.out.println("Role: " + role);

        // Validate all required fields
        if (username == null || email == null || password == null || phoneNumber == null ||
            provinceId == null || districtId == null || sectorId == null || 
            cellId == null || villageId == null) {
            out.println("Error: All fields are required");
            return;
        }
        if (role == null || role.trim().isEmpty()) {
            role = "student"; // Default role
        }

        Connection conn = null;
        PreparedStatement userStmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                out.println("Error: Database connection failed");
                return;
            }

            // Start transaction
            conn.setAutoCommit(false);

            // Check if username already exists
            String checkUserSql = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSql);
            checkUserStmt.setString(1, username);
            ResultSet rs = checkUserStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                out.println("Error: Username already exists");
                return;
            }

            // Check if email already exists
            String checkEmailSql = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailSql);
            checkEmailStmt.setString(1, email);
            rs = checkEmailStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                out.println("Error: Email already exists");
                return;
            }

            // Verify that the location IDs are valid and exist in their respective tables
            if (!validateLocationIds(conn, provinceId, districtId, sectorId, cellId, villageId)) {
                out.println("Error: Invalid location selection");
                return;
            }

            // Hash password
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Insert user with location IDs
            String userSql = "INSERT INTO users (" +
                "username, email, password, phone_number, " +
                "province_id, district_id, sector_id, cell_id, village_id, " +
                "role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            userStmt = conn.prepareStatement(userSql);
            userStmt.setString(1, username);
            userStmt.setString(2, email);
            userStmt.setString(3, hashedPassword);
            userStmt.setString(4, phoneNumber);
            userStmt.setInt(5, Integer.parseInt(provinceId));
            userStmt.setInt(6, Integer.parseInt(districtId));
            userStmt.setInt(7, Integer.parseInt(sectorId));
            userStmt.setInt(8, Integer.parseInt(cellId));
            userStmt.setInt(9, Integer.parseInt(villageId));
            userStmt.setString(10, role);

            int userRows = userStmt.executeUpdate();
            if (userRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Commit transaction
            conn.commit();
            out.println("Success: Account created successfully");

        } catch (SQLException | NumberFormatException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            out.println("Error: Database error - " + e.getMessage());
        } finally {
            try {
                if (userStmt != null) userStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String validateParameter(HttpServletRequest request, String paramName, PrintWriter out) {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            out.println("Error: " + paramName + " is required");
            return null;
        }
        return value.trim();
    }

    private boolean validateLocationIds(Connection conn, String provinceId, String districtId, 
                                        String sectorId, String cellId, String villageId) throws SQLException {
        // Validate province
        String provinceSql = "SELECT COUNT(*) FROM provinces WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(provinceSql)) {
            stmt.setInt(1, Integer.parseInt(provinceId));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) return false;
        }

        // Validate district
        String districtSql = "SELECT COUNT(*) FROM districts WHERE id = ? AND province_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(districtSql)) {
            stmt.setInt(1, Integer.parseInt(districtId));
            stmt.setInt(2, Integer.parseInt(provinceId));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) return false;
        }

        // Validate sector
        String sectorSql = "SELECT COUNT(*) FROM sectors WHERE id = ? AND district_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sectorSql)) {
            stmt.setInt(1, Integer.parseInt(sectorId));
            stmt.setInt(2, Integer.parseInt(districtId));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) return false;
        }

        // Validate cell
        String cellSql = "SELECT COUNT(*) FROM cells WHERE id = ? AND sector_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(cellSql)) {
            stmt.setInt(1, Integer.parseInt(cellId));
            stmt.setInt(2, Integer.parseInt(sectorId));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) return false;
        }

        // Validate village
        String villageSql = "SELECT COUNT(*) FROM villages WHERE id = ? AND cell_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(villageSql)) {
            stmt.setInt(1, Integer.parseInt(villageId));
            stmt.setInt(2, Integer.parseInt(cellId));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
}