package com.auca.library.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auca.library.util.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/SignInServlet")
public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identifier = request.getParameter("username"); //
        String password = request.getParameter("password");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Select user information including role and email
            String sql = "SELECT id, username, password, role, email FROM users WHERE username = ? OR email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, identifier);
            stmt.setString(2, identifier);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                
                // Check the password using BCrypt
                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Password match, set user info in session
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", rs.getLong("id"));
                    session.setAttribute("username", rs.getString("username"));
                    session.setAttribute("role", rs.getString("role"));
                    session.setAttribute("email", rs.getString("email")); // Add email to session

                    // Log session information for debugging
                    System.out.println("Session created: ID=" + session.getId() + 
                                       ", User=" + rs.getString("username") + 
                                       ", Email=" + rs.getString("email"));

                    // Redirect based on role
                    String role = rs.getString("role");
                    switch(role) {
                        case "librarian":
                            response.sendRedirect("librarian_dashboard.jsp");
                            break;
                        case "Manager":
                        	 response.sendRedirect("manager.jsp");
                        	 break;
                        case "HOD":
                            response.sendRedirect("hod_dashboard.jsp");
                            break;
                        case "Dean":
                            response.sendRedirect("dean_dashboard.jsp");
                            break;
                        case "Registrar":
                            response.sendRedirect("registrar_dashboard.jsp");
                            break;
                        case "student":
                            response.sendRedirect("student_dashboard.jsp");
                            break;
                        case "teacher":
                            response.sendRedirect("teacher_dashboard.jsp");
                            break;
                        default:
                            response.sendRedirect("signin1.html");
                    }
                } else {
                    // Password mismatch
                    request.setAttribute("error", "Invalid username/email or password");
                    request.getRequestDispatcher("signin1.html").forward(request, response);
                }
            } else {
                // No user found
                request.setAttribute("error", "Invalid username/email or password");
                request.getRequestDispatcher("signin1.html").forward(request, response);
            }
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("signin1.html").forward(request, response);
        }
    }
}