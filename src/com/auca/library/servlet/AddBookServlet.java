package com.auca.library.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auca.library.util.DatabaseConnection;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publicationDate = request.getParameter("publication_date");
        String isbn = request.getParameter("isbn");
        String shelfId = request.getParameter("shelf_id");
        int totalCopies = Integer.parseInt(request.getParameter("total_copies"));
        int availableCopies = Integer.parseInt(request.getParameter("available_copies"));
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO books (title, author, publication_date, isbn, shelf_id, total_copies, available_copies, category, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, publicationDate);
            statement.setString(4, isbn);
            statement.setString(5, shelfId);
            statement.setInt(6, totalCopies);
            statement.setInt(7, availableCopies);
            statement.setString(8, category);
            statement.setString(9, description);
            statement.executeUpdate();
            
            response.sendRedirect("bookAddedsuccess.html");  
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");  
        }
    }
}
