package com.auca.library.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auca.library.util.DatabaseConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/CheckMembershipStatusServlet")
public class CheckMembershipStatusServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email"); // Get the email from the request parameter
        String message;

        try {
            // Assuming you have a method to get a connection to your database
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT approvalStatus, membership_type FROM members WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String status = resultSet.getString("approvalStatus");
                String membershipType = resultSet.getString("membership_type");

                if ("approved".equalsIgnoreCase(status)) {
                    message = "You are approved to borrow books. Membership Type: " + membershipType;
                } else {
                    message = "Your membership status is: " + status + ". You cannot borrow books.";
                }
            } else {
                message = "Email not found.";
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while checking membership status.";
        }

        out.write("{\"message\": \"" + message + "\"}");
        out.flush();
    }
}
