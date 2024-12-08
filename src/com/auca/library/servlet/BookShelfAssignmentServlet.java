package com.auca.library.servlet;

import com.auca.library.dao.BookDAO;
import com.auca.library.dao.BookShelfAssignmentDAO;
import com.auca.library.dao.ShelfDAO;
import com.auca.library.model.Book;
import com.auca.library.model.Shelf;
import com.auca.library.model.ValidationResult;
import com.auca.library.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/BookShelfAssignmentServlet")
public class BookShelfAssignmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(BookShelfAssignmentServlet.class.getName());

    private BookShelfAssignmentDAO bookShelfAssignmentDAO;
    private BookDAO bookDAO;
    private ShelfDAO shelfDAO;

    @Override
    public void init() throws ServletException {
        try {
            // Ensure DAOs are initialized with database connection
            Connection connection = DatabaseConnection.getConnection();
            shelfDAO = new ShelfDAO(connection);
            bookDAO = new BookDAO(connection);
            bookShelfAssignmentDAO = new BookShelfAssignmentDAO(connection);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database initialization error", e);
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Validate that DAOs are initialized
            if (bookDAO == null || shelfDAO == null) {
                logger.log(Level.SEVERE, "DAO not initialized for GET request");
                throw new ServletException("Database access objects not initialized");
            }

            // Retrieve available books and shelves
            List<Book> books = bookDAO.getAllAvailableBooks();
            List<Shelf> shelves = shelfDAO.getAllShelves();

            // Log sizes for debugging
            logger.info("Available Books found: " + books.size());
            logger.info("Shelves found: " + shelves.size());

            // Set attributes for JSP
            request.setAttribute("books", books);
            request.setAttribute("shelves", shelves);
            
            // Forward to the assignment page
            request.getRequestDispatcher("/assignBookToShelf.jsp").forward(request, response);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error during GET request", e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Validate that DAOs are initialized
            if (bookShelfAssignmentDAO == null || bookDAO == null || shelfDAO == null) {
                logger.log(Level.SEVERE, "DAO not initialized for POST request");
                throw new ServletException("Database access objects not initialized");
            }

            // Parse book and shelf IDs
            String bookIdParam = request.getParameter("bookId");
            String shelfIdParam = request.getParameter("shelfId");

            // Validate input parameters
            if (bookIdParam == null || shelfIdParam == null) {
                throw new IllegalArgumentException("Book ID or Shelf ID is missing");
            }

            int bookId = Integer.parseInt(bookIdParam);
            int shelfId = Integer.parseInt(shelfIdParam);

            // Log input for debugging
            logger.info("Received Book ID: " + bookId + ", Shelf ID: " + shelfId);

            // Validate book and shelf assignment
            ValidationResult validationResult = validateBookShelfAssignment(bookId, shelfId);

            if (validationResult.isValid()) {
                // Perform book assignment with support for reassignment
                boolean assignmentResult = bookShelfAssignmentDAO.assignBookToShelf(bookId, shelfId);

                if (assignmentResult) {
                    request.setAttribute("successMessage", "Book successfully assigned/reassigned to shelf.");
                } else {
                    request.setAttribute("errorMessage", "Failed to assign book to shelf.");
                }
            } else {
                // Set validation error message
                request.setAttribute("errorMessage", validationResult.getErrorMessage());
            }

            // Reload books and shelves for the page
            List<Book> books = bookDAO.getAllAvailableBooks();
            List<Shelf> shelves = shelfDAO.getAllShelves();
            request.setAttribute("books", books);
            request.setAttribute("shelves", shelves);

            request.getRequestDispatcher("/assignBookToShelf.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Invalid book or shelf ID format", e);
            request.setAttribute("errorMessage", "Invalid book or shelf ID format.");
            request.getRequestDispatcher("/assignBookToShelf.jsp").forward(request, response);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error during book assignment", e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    // Corresponding DAO method would look like:
    // In BookShelfAssignmentDAO

    // Comprehensive Validation Method
    private ValidationResult validateBookShelfAssignment(int bookId, int shelfId) {
        try {
            // Detailed logging for debugging
            logger.info("Starting book shelf assignment validation");
            logger.info("Book ID: " + bookId);
            logger.info("Shelf ID: " + shelfId);

            // Null checks with explicit logging
            if (bookShelfAssignmentDAO == null || bookDAO == null || shelfDAO == null) {
                logger.severe("DAO not initialized");
                return new ValidationResult(false, "Database access objects not initialized");
            }

            // Validate book exists
            Book book = bookDAO.getBookById(bookId);
            if (book == null) {
                logger.warning("Book not found with ID: " + bookId);
                return new ValidationResult(false, "Book does not exist.");
            }

            // Validate shelf exists
            Shelf shelf = shelfDAO.getShelfById(shelfId);
            if (shelf == null) {
                logger.warning("Shelf not found with ID: " + shelfId);
                return new ValidationResult(false, "Shelf does not exist.");
            }

            // Check shelf capacity
            int currentBookCount = bookShelfAssignmentDAO.getBookCountOnShelf(shelfId);
            if (currentBookCount >= shelf.getCapacity()) {
                logger.warning("Shelf capacity exceeded for shelf ID: " + shelfId);
                return new ValidationResult(false, "Shelf capacity has been reached.");
            }

            // Allow reassignment by removing the previous check for existing assignment
            // Instead of blocking reassignment, we'll allow moving the book to a new shelf

            // All validations passed
            logger.info("Book shelf assignment validation successful");
            return new ValidationResult(true, "");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during book shelf assignment validation", e);
            return new ValidationResult(false, "Database error during validation: " + e.getMessage());
        }
    }
    // Separate method to handle SQL exception
    private boolean assignBookToShelf(int bookId, int shelfId) throws SQLException {
        return bookShelfAssignmentDAO.assignBookToShelf(bookId, shelfId);
    }

    // Cleanup resources
    @Override
    public void destroy() {
        // Close database connections if needed
        DatabaseConnection.closeConnection(null);
    }
}