package com.auca.library.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auca.library.model.Book;
import com.auca.library.util.DatabaseConnection;

@WebServlet("/MyBooksServlet")
public class MyBooksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("email");
        List<Book> borrowedBooks = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Updated query to remove bb.due_date and use calculated due date
            String query = "SELECT " +
                           "bb.id AS borrowed_book_id, " +
                           "b.book_id, " +
                           "b.title, " +
                           "b.author, " +
                           "b.isbn, " +
                           "b.category, " +
                           "b.status, " +
                           "bb.borrow_date, " +
                           "DATE_ADD(bb.borrow_date, INTERVAL 14 DAY) AS calculated_due_date, " +
                           "m.membership_type, " +
                           "DATEDIFF(CURRENT_DATE, DATE_ADD(bb.borrow_date, INTERVAL 14 DAY)) AS days_overdue " +
                           "FROM borrowed_books bb " +
                           "JOIN books b ON bb.book_id = b.book_id " +
                           "JOIN members m ON bb.member_id = m.id " +
                           "WHERE m.email = ? AND bb.return_date IS NULL";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, userEmail);

                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        Book book = new Book();
                        
                        // Set book details
                        book.setBookId(resultSet.getInt("book_id"));
                        book.setTitle(resultSet.getString("title"));
                        book.setAuthor(resultSet.getString("author"));
                        book.setIsbn(resultSet.getString("isbn"));
                        book.setCategory(resultSet.getString("category"));
                        book.setStatus(resultSet.getString("status"));
                        
                        // Set borrow and due dates
                        book.setBorrowDate(resultSet.getDate("borrow_date"));
                        book.setDueDate(resultSet.getDate("calculated_due_date"));
                        
                        // Set borrowed book ID
                        book.setBorrowedBookId(resultSet.getInt("borrowed_book_id"));
                        
                        // Calculate fine
                        int daysOverdue = resultSet.getInt("days_overdue");
                        String membershipType = resultSet.getString("membership_type");
                        double fineAmount = calculateFine(daysOverdue, membershipType);
                        book.setFineAmount(fineAmount);

                        borrowedBooks.add(book);
                    }
                }
            }

            System.out.println("DEBUG: Total Borrowed Books Found: " + borrowedBooks.size());

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error retrieving borrowed books: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        // Set the borrowed books list as a request attribute
        request.setAttribute("borrowedBooks", borrowedBooks);
        
        // Forward to JSP
        request.getRequestDispatcher("my_books.jsp").forward(request, response);
    }
    // Fine calculation method with membership type consideration
    private double calculateFine(int daysOverdue, String membershipType) {
        if (daysOverdue <= 0) {
            return 0.0; // No fine if not overdue
        }

        double fineRate;
        switch (membershipType.toLowerCase()) {
            case "gold":
                fineRate = 50.0; // 50 Rwf per day
                break;
            case "silver":
                fineRate = 30.0; // 30 Rwf per day
                break;
            case "striver":
                fineRate = 10.0; // 10 Rwf per day
                break;
            default:
                fineRate = 50.0; // Default to highest rate
        }

        return Math.max(0, daysOverdue * fineRate);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}