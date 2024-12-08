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
import javax.servlet.http.HttpSession;

import com.auca.library.util.DatabaseConnection;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("signin1.html");
            return;
        }

        String userEmail = (String) session.getAttribute("email");
        
        int totalBorrowedBooks = 0;
        int booksDueToday = 0;
        double fineAmount = 0.0;

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Total Borrowed Books
            String borrowedBooksQuery = "SELECT COUNT(*) AS total_books FROM borrowed_books bb " +
                "JOIN members m ON bb.member_id = m.id " +
                "WHERE m.email = ? AND bb.return_date IS NULL";
            
            try (PreparedStatement pstmt = conn.prepareStatement(borrowedBooksQuery)) {
                pstmt.setString(1, userEmail);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        totalBorrowedBooks = rs.getInt("total_books");
                    }
                }
            }

            // Books Due Today
            String booksDueTodayQuery = "SELECT COUNT(*) AS due_books FROM borrowed_books bb " +
                "JOIN members m ON bb.member_id = m.id " +
                "WHERE m.email = ? AND bb.due_date <= CURRENT_DATE AND bb.return_date IS NULL";
            
            try (PreparedStatement pstmt = conn.prepareStatement(booksDueTodayQuery)) {
                pstmt.setString(1, userEmail);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        booksDueToday = rs.getInt("due_books");
                    }
                }
            }

            // Fine Amount
            String fineQuery = "SELECT COALESCE(SUM(fine_amount), 0) AS total_fine FROM borrowed_books bb " +
                "JOIN members m ON bb.member_id = m.id " +
                "WHERE m.email = ? AND bb.return_date IS NULL";
            
            try (PreparedStatement pstmt = conn.prepareStatement(fineQuery)) {
                pstmt.setString(1, userEmail);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        fineAmount = rs.getDouble("total_fine");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Log the error
        }

        // Set attributes for JSP
        request.setAttribute("totalBorrowedBooks", totalBorrowedBooks);
        request.setAttribute("booksDueToday", booksDueToday);
        request.setAttribute("fineAmount", fineAmount);

        // Forward to the dashboard
        request.getRequestDispatcher("student_dashboard.jsp").forward(request, response);
    }
}