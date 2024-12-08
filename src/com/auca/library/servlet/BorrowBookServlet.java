package com.auca.library.servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.auca.library.util.DatabaseConnection;
@WebServlet("/BorrowBookServlet")
public class BorrowBookServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;
 private static final Logger LOGGER = Logger.getLogger(BorrowBookServlet.class.getName());
 
 @Override
 protected void doGet(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException {
     try {
         LOGGER.info("Handling GET request to BorrowBookServlet");

         HttpSession session = request.getSession(false);
         if (session == null || session.getAttribute("email") == null) {
             request.setAttribute("error", "You must be logged in.");
             request.getRequestDispatcher("/login.jsp").forward(request, response);
             return;
         }

         String role = (String) session.getAttribute("role");
         if (role == null || (!role.equals("teacher") && !role.equals("student"))) {
             request.setAttribute("error", "You are not allowed to borrow books.");
             request.getRequestDispatcher("/error.jsp").forward(request, response);
             return;
         }

         request.getRequestDispatcher("/borrowBook.jsp").forward(request, response);
     } catch (ServletException | IOException e) {
         LOGGER.log(Level.SEVERE, "Error processing GET request", e);
         handleError(request, response, "Unable to load borrow book page: " + e.getMessage());
     }
 }

// Handle POST Requests
 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException {
     // Log incoming request details
     logRequestDetails(request);

     // Get session and validate user is logged in
     HttpSession session = request.getSession(false);
     if (session == null || session.getAttribute("email") == null) {
         handleError(request, response, "You must be logged in to borrow a book.");
         return;
     }

     // Retrieve email from session
     String email = (String) session.getAttribute("email");
     String role = (String) session.getAttribute("role");

     // Check if the user has the correct role (teacher or student)
     if (role == null || (!role.equals("teacher") && !role.equals("student"))) {
         handleError(request, response, "You are not allowed to borrow books.");
         return;
     }

     // Validate book identifier input
     String bookIdentifier = request.getParameter("bookIdentifier");
     if (bookIdentifier == null || bookIdentifier.trim().isEmpty()) {
         handleError(request, response, "Book identifier is required.");
         return;
     }

     Connection conn = null;
     try {
         conn = DatabaseConnection.getConnection();

         // Retrieve member ID using email
         int memberId = getMemberIdByEmail(conn, email);
         if (memberId <= 0) {
             handleError(request, response, "Unable to find member information.");
             return;
         }

         // Retrieve book ID using identifier (ISBN or book ID)
         int bookId = getBookIdByIdentifier(conn, bookIdentifier);
         if (bookId <= 0) {
             handleError(request, response, "Invalid book identifier.");
             return;
         }

         // Validate member details
         MemberDetails memberDetails = validateMember(conn, memberId);
         if (memberDetails == null) {
             handleError(request, response, "Invalid member or unapproved membership.");
             return;
         }

         // Check borrowing eligibility
         BorrowingEligibility eligibility = checkBorrowingEligibility(conn, memberId, memberDetails);
         if (!eligibility.isEligible) {
             // Set session attributes for error page
             session.setAttribute("membershipType", memberDetails.membershipType);
             session.setAttribute("maxBooks", eligibility.maxBooks);
             session.setAttribute("currentBorrowedBooks", eligibility.currentBorrowedBooks);
             
             request.getRequestDispatcher("/book_limit_error.jsp").forward(request, response);
             return;
         }

         // Check book availability
         if (!isBookAvailable(conn, bookId)) {
             handleError(request, response, "The book is not available for borrowing.");
             return;
         }

         // Process book borrowing
         borrowBook(conn, memberId, bookId);

         // Redirect to success page
         request.setAttribute("successMessage", "Book borrowed successfully!");
         request.getRequestDispatcher("/borrowSuccess.jsp").forward(request, response);

     } catch (SQLException e) {
         LOGGER.log(Level.SEVERE, "Database error during book borrowing", e);
         handleError(request, response, "A database error occurred. Please try again later.");
     } finally {
         closeConnection(conn);
     }
 }
 
 private void logRequestDetails(HttpServletRequest request) {
     LOGGER.info("Request URI: " + request.getRequestURI());
     LOGGER.info("Context Path: " + request.getContextPath());
     
     // Log all parameters
     java.util.Enumeration<String> paramNames = request.getParameterNames();
     while (paramNames.hasMoreElements()) {
         String paramName = paramNames.nextElement();
         String paramValue = request.getParameter(paramName);
         LOGGER.info("Parameter: " + paramName + " = " + paramValue);
     }
 }
 



 // Inner class to hold member details
 private static class MemberDetails {
 String approvalStatus;
 String membershipType;

 MemberDetails(String approvalStatus, String membershipType) {
 this.approvalStatus = approvalStatus;
 this.membershipType = membershipType;
 }
 }
 // Inner class to hold borrowing eligibility details
 private static class BorrowingEligibility {
 boolean isEligible;
 int maxBooks;
 int currentBorrowedBooks;

 BorrowingEligibility(boolean isEligible, int maxBooks, int
currentBorrowedBooks) {
 this.isEligible = isEligible;
 this.maxBooks = maxBooks;
 this.currentBorrowedBooks = currentBorrowedBooks;
 }
 }
 // Validate member
//Validate member
private MemberDetails validateMember(Connection conn, int memberId) throws SQLException {
  String memberQuery = "SELECT approvalStatus, membership_type FROM members WHERE id = ?";
  LOGGER.info("Executing query: " + memberQuery + " with memberId: " + memberId);

  try (PreparedStatement memberStmt = conn.prepareStatement(memberQuery)) {
      memberStmt.setInt(1, memberId);

      try (ResultSet memberRs = memberStmt.executeQuery()) {
          if (memberRs.next()) {
              String approvalStatus = memberRs.getString("approvalStatus");
              String membershipType = memberRs.getString("membership_type");

              LOGGER.info("Member found - Approval Status: " + approvalStatus + ", Membership Type: " + membershipType);

              // Check if membership is approved
              if (!"APPROVED".equalsIgnoreCase(approvalStatus)) {
                  LOGGER.warning("Member not approved. Approval Status: " + approvalStatus);
                  return null;
              }

              return new MemberDetails(approvalStatus, membershipType);
          }
          LOGGER.warning("No member found with ID: " + memberId);
          return null;
      }
  } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error validating member", e);
      throw e;
  }
}
 // Check borrowing eligibility
private BorrowingEligibility checkBorrowingEligibility(Connection conn, int memberId, MemberDetails memberDetails) throws SQLException {
    // Determine max books based on membership type
    int maxBooks = determineMaxBooks(memberDetails.membershipType);

    // Add a space between FROM and table name
    String borrowedQuery = "SELECT COUNT(*) as borrowed_count FROM borrowed_books WHERE member_id = ? AND return_date IS NULL";

    try (PreparedStatement borrowedStmt = conn.prepareStatement(borrowedQuery)) {
        borrowedStmt.setInt(1, memberId);

        try (ResultSet borrowedRs = borrowedStmt.executeQuery()) {
            if (borrowedRs.next()) {
                int borrowedCount = borrowedRs.getInt("borrowed_count");
                boolean isEligible = borrowedCount < maxBooks;

                return new BorrowingEligibility(isEligible, maxBooks, borrowedCount);
            }

            // If no results, assume 0 borrowed books
            return new BorrowingEligibility(true, maxBooks, 0);
        }
    }
}

private int getMemberIdByEmail(Connection conn, String email) throws SQLException {
    String sql = "SELECT id FROM members WHERE email = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, email);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
    }
    return -1;
}

private int getBookIdByIdentifier(Connection conn, String bookIdentifier) throws SQLException {
    // Try to find by ISBN first
    String isbnSql = "SELECT book_id FROM books WHERE isbn = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(isbnSql)) {
        pstmt.setString(1, bookIdentifier);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("book_id");
            }
        }
    }

    // If not found by ISBN, try by ID
    try {
        int bookId = Integer.parseInt(bookIdentifier);
        String idSql = "SELECT book_id FROM books WHERE book_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(idSql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("book_id");
                }
            }
        }
    } catch (NumberFormatException e) {
        // Not a valid integer
        return -1;
    }

    return -1;
}

 // Determine max books based on membership type
 private int determineMaxBooks(String membershipType) {
 switch (membershipType.toLowerCase()) {
 case "gold":
 return 5;
 case "silver":
 return 3;
 case "striver":
 return 2;
 default:
 return 0;
 }
 }
 // Check book availability
 private boolean isBookAvailable(Connection conn, int bookId) throws SQLException {
	    // Add a space before WHERE clause
	    String bookQuery = "SELECT available_copies FROM books WHERE book_id = ?";

	    try (PreparedStatement bookStmt = conn.prepareStatement(bookQuery)) {
	        bookStmt.setInt(1, bookId);

	        try (ResultSet bookRs = bookStmt.executeQuery()) {
	            return bookRs.next() && bookRs.getInt("available_copies") > 0;
	        }
	    }
	}
 // Borrow book
 private void borrowBook(Connection conn, int memberId, int bookId) throws
SQLException {
 conn.setAutoCommit(false);

 try {
 // Insert borrow record
 String borrowQuery = "INSERT INTO borrowed_books (member_id, book_id,borrow_date) VALUES (?, ?, CURRENT_DATE)";
 try (PreparedStatement borrowStmt =
conn.prepareStatement(borrowQuery)) {
 borrowStmt.setInt(1, memberId);
 borrowStmt.setInt(2, bookId);
 borrowStmt.executeUpdate();
 }
 // Update book availability
 String updateBookQuery = "UPDATE books SET available_copies =available_copies - 1 WHERE book_id = ?";
 try (PreparedStatement updateBookStmt =
conn.prepareStatement(updateBookQuery)) {
 updateBookStmt.setInt(1, bookId);
 updateBookStmt.executeUpdate();
 }
 conn.commit();
 } catch (SQLException e) {
 conn.rollback();
 throw e;
 } finally {
 conn.setAutoCommit(true);
 }
 }
 // Handle errors and forward to error page
 private void handleError(HttpServletRequest request, HttpServletResponse
response, String errorMessage) throws ServletException, IOException {
 request.setAttribute("errorMessage", errorMessage);
 request.getRequestDispatcher("/error.jsp").forward(request, response);
 }
 // Close database connection
 private void closeConnection(Connection conn) {
 if (conn != null) {
 try {
 conn.close();
 } catch (SQLException e) {
 e.printStackTrace();
 }
 }
 }

}
