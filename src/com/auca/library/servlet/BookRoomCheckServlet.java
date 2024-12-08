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
import com.auca.library.util.DatabaseConnection;

@WebServlet("/BookRoomCheckServlet")
public class BookRoomCheckServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        
        try {
            // Database connection
            Connection conn = DatabaseConnection.getConnection();
            
            // Query to count books in a specific room
            String query = "SELECT COUNT(*) AS book_count " +
                           "FROM Books b " +
                           "JOIN Shelves s ON b.shelf_id = s.shelf_id " +
                           "WHERE s.room_id = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, roomId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int bookCount = rs.getInt("book_count");
                
                // Set attributes for JSP
                request.setAttribute("roomId", roomId);
                request.setAttribute("bookCount", bookCount);
                
                // Forward to result page
                request.getRequestDispatcher("/room-book-count.jsp").forward(request, response);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}