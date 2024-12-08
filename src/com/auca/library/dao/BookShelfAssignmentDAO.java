package com.auca.library.dao;

import com.auca.library.model.Book;
import com.auca.library.model.Shelf;
import com.auca.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookShelfAssignmentDAO {
    private static final Logger logger = Logger.getLogger(BookShelfAssignmentDAO.class.getName());
    private Connection connection;
    

    // SQL Queries
    private static final String ASSIGN_BOOK_TO_SHELF_SQL = 
        "INSERT INTO book_shelf_assignments (book_id, shelf_id, assigned_date) VALUES (?, ?, NOW())";

    private static final String REMOVE_BOOK_FROM_SHELF_SQL = 
        "DELETE FROM book_shelf_assignments WHERE book_id = ? AND shelf_id = ?";

    private static final String GET_BOOKS_ON_SHELF_SQL = 
        "SELECT b.* FROM books b " +
        "JOIN book_shelf_assignments bsa ON b.book_id = bsa.book_id " +
        "WHERE bsa.shelf_id = ?";

    private static final String GET_SHELF_FOR_BOOK_SQL = 
        "SELECT shelf_id FROM book_shelf_assignments WHERE book_id = ?";

    private static final String CHECK_BOOK_ASSIGNMENT_SQL = 
        "SELECT COUNT(*) FROM book_shelf_assignments WHERE book_id = ?";

    private static final String COUNT_BOOKS_ON_SHELF_SQL = 
        "SELECT COUNT(*) FROM book_shelf_assignments WHERE shelf_id = ?";

    public BookShelfAssignmentDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Assign a book to a shelf with comprehensive validation
     * @param bookId ID of the book to assign
     * @param shelfId ID of the shelf to assign to
     * @return boolean indicating success of assignment
     * @throws SQLException if database error occurs
     */
    public boolean assignBookToShelf(int bookId, int shelfId) throws SQLException {
        String query = "UPDATE books SET shelf_id = ?, last_assigned_date = CURRENT_TIMESTAMP WHERE book_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, shelfId);
            stmt.setInt(2, bookId);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Optionally update shelf book counts
                updateShelfBookCounts(bookId, shelfId);
                return true;
            }
            
            return false;
        }
    }

   
    	private void updateShelfBookCounts(int bookId, int shelfId) {
		// TODO Auto-generated method stub
		
	}

		private void updateShelfBookCount(int shelfId) throws SQLException {
    	    String updateCountQuery = "UPDATE shelves SET current_book_count = " +
    	                               "(SELECT COUNT(*) FROM books WHERE shelf_id = ?) " +
    	                               "WHERE shelf_id = ?";
    	    
    	    try (PreparedStatement stmt = connection.prepareStatement(updateCountQuery)) {
    	        stmt.setInt(1, shelfId);
    	        stmt.setInt(2, shelfId);
    	        
    	        stmt.executeUpdate();
    	    } catch (SQLException e) {
    	        logger.log(Level.SEVERE, "Error updating shelf book count", e);
    	        throw e;
    	    }
		
	}

	private boolean updateShelfDetails(Shelf shelf) {
		// TODO Auto-generated method stub
		return false;
	}

	private Shelf getShelfById(int shelfId) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isShelfAvailable(int shelfId) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
     * Validate if book and shelf exist
     * @param bookId Book ID to validate
     * @param shelfId Shelf ID to validate
     * @return boolean indicating validity
     * @throws SQLException if database error occurs
     */
    private boolean validateBookAndShelf(int bookId, int shelfId) throws SQLException {
        // Validate book exists
        String bookCheckSql = "SELECT COUNT(*) FROM books WHERE book_id = ?";
        String shelfCheckSql = "SELECT COUNT(*) FROM shelves WHERE shelf_id = ?";

        try (PreparedStatement bookStmt = connection.prepareStatement(bookCheckSql);
             PreparedStatement shelfStmt = connection.prepareStatement(shelfCheckSql)) {
            
            // Check book
            bookStmt.setInt(1, bookId);
            try (ResultSet rs = bookStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    logger.warning("Book does not exist");
                    return false;
                }
            }

            // Check shelf
            shelfStmt.setInt(1, shelfId);
            try (ResultSet rs = shelfStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    logger.warning("Shelf does not exist");
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * Check if a book is already assigned to a shelf
     * @param bookId Book ID to check
     * @return boolean indicating if book is assigned
     * @throws SQLException if database error occurs
     */
    public boolean isBookAlreadyAssigned(int bookId) throws SQLException {
        String query = "SELECT shelf_id FROM books WHERE book_id = ? AND shelf_id IS NOT NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if the book is already assigned to a shelf
            }
        }
    }
    /**
     * Get the number of books on a specific shelf
     * @param shelfId Shelf ID to check
     * @return number of books on the shelf
     * @throws SQLException if database error occurs
     */
    public int getBookCountOnShelf(int shelfId) throws SQLException {
        String query = "SELECT current_book_count FROM shelves WHERE shelf_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, shelfId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("current_book_count");
                }
                return 0;
            }
        }
    }

    /**
     * Remove a book from its current shelf
     * @param bookId ID of the book to remove
     * @return boolean indicating successful removal
     * @throws SQLException if database error occurs
     */
    public boolean removeBookFromShelf(int bookId) throws SQLException {
        int currentShelfId = getShelfForBook(bookId);
        
        if (currentShelfId == -1) {
            logger.warning("Book is not assigned to any shelf");
            return false;
        }

        try (PreparedStatement pstmt = connection.prepareStatement(REMOVE_BOOK_FROM_SHELF_SQL)) {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, currentShelfId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                logger.info(String.format("Book %d removed from Shelf %d", bookId, currentShelfId));
                return true;
            }
        }
        
        return false;
    }
    /**
     * Get all books on a specific shelf
     * @param shelfId ID of the shelf
     * @return List of books on the shelf
     */
    public List<Book> getBooksOnShelf(int shelfId) {
        List<Book> books = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_BOOKS_ON_SHELF_SQL)) {
            
            pstmt.setInt(1, shelfId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    // Set other book properties as needed
                    
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return books;
    }

    /**
     * Get the shelf a book is currently assigned to
     * @param bookId ID of the book
     * @return Shelf ID or -1 if not assigned
     */
    
    public boolean reassignBookToShelf(int bookId, int newShelfId) throws SQLException {
        // Remove from current shelf
        if (removeBookFromShelf(bookId)) {
            // Assign to new shelf
            return assignBookToShelf(bookId, newShelfId);
        }
        return false; 
    }
    
    
    public int getShelfForBook(int bookId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_SHELF_FOR_BOOK_SQL)) {
            
            pstmt.setInt(1, bookId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("shelf_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    
    
    
    
}