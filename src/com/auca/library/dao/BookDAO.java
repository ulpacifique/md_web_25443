package com.auca.library.dao;

import com.auca.library.model.Book;
import com.auca.library.model.BorrowedBook;
import com.auca.library.model.Member;
import com.auca.library.model.Shelf;
import com.auca.library.servlet.BookShelfAssignmentServlet;
import com.auca.library.util.DatabaseConnection;
import com.auca.library.util.HibernateUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



import org.hibernate.Session;
import org.hibernate.Transaction;

public class BookDAO {
	private Connection connection;
	 private static final Logger logger = Logger.getLogger(BookShelfAssignmentServlet.class.getName());
	 public BookDAO(Connection connection) {
	        this.connection = connection;
	 }
    // Borrow a book
    public boolean borrowBook(int bookId, int memberId, Date dueDate) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Retrieve the book by ID
            Book book = session.get(Book.class, bookId);
            if (book != null && "AVAILABLE".equals(book.getStatus())) {
                book.setStatus("BORROWED");
                book.setBorrowedBy(memberId);
                book.setDueDate(dueDate);
                session.update(book);
                transaction.commit();
                return true;
            }
            return false; // Book not available
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false; // An error occurred
        }
    }
    
  

    public boolean borrowBook(int bookId, int memberId, java.util.Date dueDate) {
        Transaction transaction = null;
        Session session = null;
        
        try {
            // Open Hibernate session
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Retrieve the book by ID
            Book book = session.get(Book.class, bookId);
            
            // Check if book exists and is available
            if (book == null) {
                return false; // Book not found
            }
            
            // Check book availability
            if (book.getAvailableCopies() <= 0 || !"AVAILABLE".equals(book.getStatus())) {
                return false; // Book not available
            }
            
            // Update book details
            book.setStatus("BORROWED");
            book.setBorrowedBy(memberId);
            book.setDueDate(new java.sql.Date(dueDate.getTime()));
            
            // Decrease available copies
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            
            // Save updated book
            session.update(book);
            
            // Commit transaction
            transaction.commit();
            
            return true;
        } catch (Exception e) {
            // Rollback transaction if something goes wrong
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            // Close the session
            if (session != null) {
                session.close();
            }
        }
    }
	
    public long countBorrowedBooks(int memberId) {
        Transaction transaction = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            String hql = "SELECT COUNT(*) FROM BorrowedBook bb WHERE bb.memb erId = :memberId AND bb.status = 'BORROWED'";
            Long count = (Long) session.createQuery(hql)
                .setParameter("memberId", memberId)
                .uniqueResult();
            
            transaction.commit();
            return count != null ? count : 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
	private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublicationDate(rs.getDate("publication_date"));
        book.setIsbn(rs.getString("isbn"));
        book.setStatus(rs.getString("status"));
        book.setCreatedAt(rs.getTimestamp("created_at"));
        book.setUpdatedAt(rs.getTimestamp("updated_at"));
        book.setShelfId(rs.getInt("shelf_id"));
        book.setTotalCopies(rs.getInt("total_copies"));
        book.setAvailableCopies(rs.getInt("available_copies"));
        book.setCategory(rs.getString("category"));
        book.setDescription(rs.getString("description"));
        return book;
    }
	
	 public void updateBook(Book book) throws SQLException {
	        String sql = "UPDATE books SET title = ?, author = ?, publication_date = ?, isbn = ?, status = ?, updated_at = ?, total_copies = ?, available_copies = ?, category = ?, description = ? WHERE book_id = ?";
	        
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            pstmt.setString(1, book.getTitle());
	            pstmt.setString(2, book.getAuthor());
	            pstmt.setDate(3, new java.sql.Date(book.getPublicationDate().getTime()));
	            pstmt.setString(4, book.getIsbn());
	            pstmt.setString(5, book.getStatus());
	            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
	            pstmt.setInt(7, book.getTotalCopies());
	            pstmt.setInt(8, book.getAvailableCopies());
	            pstmt.setString(9, book.getCategory());
	            pstmt.setString(10, book.getDescription());
	            pstmt.setInt(11, book.getBookId());
	            pstmt.executeUpdate();
	        }
	    }
	 
	 public List<Book> searchBooks(String searchTerm) throws SQLException {
	        List<Book> books = new ArrayList<>();
	        String sql = "SELECT * FROM books WHERE " +
	                     "title LIKE ? OR author LIKE ? OR isbn LIKE ?";
	        
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            String likePattern = "%" + searchTerm + "%";
	            pstmt.setString(1, likePattern);
	            pstmt.setString(2, likePattern);
	            pstmt.setString(3, likePattern);
	            
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                    Book book = mapResultSetToBook(rs);
	                    books.add(book);
	                }
	            }
	        } return books;
	    }
	 
	   public List<Book> filterBooks(String category, String status) throws SQLException {
	        List<Book> books = new ArrayList<>();
	        String sql = "SELECT * FROM books WHERE category = ? AND status = ?";
	        
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            pstmt.setString(1, category);
	            pstmt.setString(2, status);
	            
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                    Book book = mapResultSetToBook(rs);
	                    books.add(book);
	                }
	            }
	        }
	        return books;
	    }
	 
	 public void addBook(Book book) throws SQLException {
	        String sql = "INSERT INTO books (title, author, publication_date, isbn, status, created_at, updated_at, total_copies, available_copies, category, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            pstmt.setString(1, book.getTitle());
	            pstmt.setString(2, book.getAuthor());
	            pstmt.setDate(3, new java.sql.Date(book.getPublicationDate().getTime()));
	            pstmt.setString(4, book.getIsbn());
	            pstmt.setString(5, book.getStatus());
	            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
	            pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
	            pstmt.setInt(8, book.getTotalCopies());
	            pstmt.setInt(9, book.getAvailableCopies());
	            pstmt.setString(10, book.getCategory());
	            pstmt.setString(11, book.getDescription());
	            pstmt.executeUpdate();
	        }
	    }
	 
	 public double returnBook(int borrowedBookId, int memberId) {
		    Transaction transaction = null;
		    Session session = null;
		    double fineAmount = 0.0;
		    
		    try {
		        // Open Hibernate session
		        session = HibernateUtil.getSessionFactory().openSession();
		        transaction = session.beginTransaction();

		        // Retrieve the borrowed book
		        BorrowedBook borrowedBook = session.get(BorrowedBook.class, borrowedBookId);
		        
		        if (borrowedBook == null || !"BORROWED".equals(borrowedBook.getStatus())) {
		            return -1; // Book not found or already returned
		        }

		        // Retrieve member to get membership type
		        Member member = session.get(Member.class, memberId);
		        if (member == null) {
		            return -1; // Member not found
		        }

		        // Calculate fine
		        Date currentDate = new Date(System.currentTimeMillis());
		        long overdueDays = calculateOverdueDays(borrowedBook.getBorrowDate(), currentDate);
		        fineAmount = calculateFine(overdueDays, member.getMembershipType());

		        // Update borrowed book status
		        borrowedBook.setStatus("RETURNED");
		        borrowedBook.setReturnDate(currentDate);
		        borrowedBook.setFineAmount(fineAmount);
		        session.update(borrowedBook);

		        // Retrieve the book and update its availability
		        Book book = session.get(Book.class, borrowedBook.getBookId());
		        if (book != null) {
		            book.setStatus("AVAILABLE");
		            book.setAvailableCopies(book.getAvailableCopies() + 1);
		            session.update(book);
		        }

		        // Update member's total fine
		        member.setTotalFine(member.getTotalFine() + fineAmount);
		        session.update(member);

		        // Commit transaction
		        transaction.commit();
		        
		        return fineAmount;
		    } catch (Exception e) {
		        // Rollback transaction if something goes wrong
		        if (transaction != null) {
		            transaction.rollback();
		        }
		        e.printStackTrace();
		        return -1;
		    } finally {
		        // Close the session
		        if (session != null) {
		            session.close();
		        }
		    }
		}

		private long calculateOverdueDays(java.util.Date borrowDate, Date currentDate) {
		// TODO Auto-generated method stub
		return 0;
	}
		// Helper method to calculate overdue days
		private long calculateOverdueDays(Date borrowDate, Date returnDate) {
		    long borrowTime = borrowDate.getTime();
		    long returnTime = returnDate.getTime();
		    long gracePeriod = 14 * 24 * 60 * 60 * 1000L; // 14 days in milliseconds
		    
		    long overdueDays = (returnTime - borrowTime - gracePeriod) / (24 * 60 * 60 * 1000L);
		    return Math.max(0, overdueDays);
		}

		// Helper method to calculate fine based on membership type
		private double calculateFine(long overdueDays, String membershipType) {
		    if (overdueDays <= 0) {
		        return 0.0;
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

		    return overdueDays * fineRate;
		}

		 public List<Book> getAllBooks() throws SQLException {
		        List<Book> books = new ArrayList<>();
		        String sql = "SELECT * FROM books WHERE book_id NOT IN (SELECT book_id FROM book_shelf_assignments)";
		        
		        try (PreparedStatement pstmt = connection.prepareStatement(sql);
		             ResultSet rs = pstmt.executeQuery()) {
		            
		            while (rs.next()) {
		                Book book = new Book();
		                book.setBookId(rs.getInt("book_id"));
		                book.setTitle(rs.getString("title"));
		                book.setAuthor(rs.getString("author"));
		                // Set other book properties
		                books.add(book);
		            }
		        }
		        return books;
		    }

	
		 public Book getBookById(int bookId) throws SQLException {
			    String query = "SELECT book_id, title, author, shelf_id FROM books WHERE book_id = ?";
			    
			    try (PreparedStatement stmt = connection.prepareStatement(query)) {
			        // Detailed logging
			        logger.info("Executing Book Lookup Query");
			        logger.info("Query: " + query);
			        logger.info("Book ID Parameter: " + bookId);

			        stmt.setInt(1, bookId);
			        
			        try (ResultSet rs = stmt.executeQuery()) {
			            if (rs.next()) {
			                Book book = new Book();
			                book.setBookId(rs.getInt("book_id"));
			                book.setTitle(rs.getString("title"));
			                book.setAuthor(rs.getString("author"));
			                book.setShelfId(rs.getInt("shelf_id"));

			                // Logging successful book retrieval
			                logger.info("Book Found: " + book.getTitle());
			                
			                return book;
			            } else {
			                // Log when no book is found
			                logger.warning("No book found with ID: " + bookId);
			                return null;
			            }
			        }
			    } catch (SQLException e) {
			        // Comprehensive error logging
			        logger.log(Level.SEVERE, "Database error in getBookById", e);
			        logger.severe("Full Error Details: " + e.getMessage());
			        logger.severe("SQL State: " + e.getSQLState());
			        logger.severe("Error Code: " + e.getErrorCode());
			        
			        throw e;
			    }
			}
		
	
	// In BookDAO
		 public List<Book> getAllAvailableBooks() throws SQLException {
			    List<Book> availableBooks = new ArrayList<>();
			    String query = "SELECT book_id, title, author, shelf_id FROM books";
			    
			    try (Statement stmt = connection.createStatement();
			         ResultSet rs = stmt.executeQuery(query)) {
			        
			        while (rs.next()) {
			            Book book = new Book();
			            book.setBookId(rs.getInt("book_id"));
			            book.setTitle(rs.getString("title"));
			            book.setAuthor(rs.getString("author"));
			            book.setShelfId(rs.getInt("shelf_id"));
			            
			            availableBooks.add(book);
			        }
			        
			        return availableBooks;
			    }
		 }
	// In ShelfDAO
	public List<Shelf> getAllShelvesWithCapacity() throws SQLException {
	    List<Shelf> shelves = new ArrayList<>();
	    String sql = "SELECT s.shelf_id, s.shelf_name, s.capacity, " +
	                 "(SELECT COUNT(*) FROM book_shelf_assignments WHERE shelf_id = s.shelf_id) AS currentBookCount " +
	                 "FROM shelves s";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        while (rs.next()) {
	            Shelf shelf = new Shelf();
	            shelf.setShelfId(rs.getInt("shelf_id"));
	            shelf.setShelfName(rs.getString("shelf_name"));
	            shelf.setCapacity(rs.getInt("capacity"));
	            shelf.setCurrentBookCount(rs.getInt("currentBookCount"));
	            shelves.add(shelf);
	        }
	    }
	    return shelves;
	}
	
	
	
	
	
	}
