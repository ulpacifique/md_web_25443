package com.auca.library.test;

import com.auca.library.model.BookBorrowingService;
import com.auca.library.model.BookBorrowingService.LateFeeResult;
import com.auca.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LateFeeCalculationTest {
    private Connection conn;
    private BookBorrowingService borrowingService;

    public LateFeeCalculationTest() {
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection!");
                return;
            }
            borrowingService = new BookBorrowingService(conn);
            System.out.println("Database connection established successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing test: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void runAllTests() {
        try {
            System.out.println("Starting Late Fee Calculation Tests...");

            // Test each membership type
            testLateFeeForMembership("Gold", 50.0);
            testLateFeeForMembership("Silver", 30.0);
            testLateFeeForMembership("Striver", 10.0);

            // Test no late fee scenario
            testNoLateFee();

            System.out.println("All Late Fee Calculation Tests Completed Successfully!");
        } catch (Exception e) {
            System.err.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void testLateFeeForMembership(String membershipType, double dailyRate) throws SQLException {
        System.out.println("\nTesting Late Fee for " + membershipType + " Membership");

        int memberId = -1;
        int bookId = -1;
        int borrowedBooksId = -1;

        try {
            // Create test member
            memberId = createTestMember(membershipType);
            System.out.println("Created test member with ID: " + memberId);

            // Create test book
            bookId = createTestBook();
            System.out.println("Created test book with ID: " + bookId);

            // Create overdue borrow record
            borrowedBooksId = createOverdueBorrowRecord(memberId, bookId);
            System.out.println("Created overdue borrow record with ID: " + borrowedBooksId);

            // Calculate and apply late fees
            LateFeeResult result = borrowingService.calculateAndApplyLateFees(borrowedBooksId);

            // Validate late fee calculation
            int expectedDaysOverdue = 15; // Assuming 15 days overdue
            double expectedTotalFee = expectedDaysOverdue * dailyRate;

            System.out.println("Expected Days Overdue: " + expectedDaysOverdue);
            System.out.println("Actual Days Overdue: " + result.getDaysOverdue());
            System.out.println("Expected Total Fee: " + expectedTotalFee);
            System.out.println("Actual Total Fee: " + result.getTotalFee());

            // Assertions
            if (result.getDaysOverdue() != expectedDaysOverdue) { System.err.println("Days overdue mismatch!");
            }
            if (result.getTotalFee() != expectedTotalFee) {
                System.err.println("Total fee mismatch!");
            }
        } finally {
            // Clean up test data
            cleanUpTestData(memberId, bookId, borrowedBooksId);
        }
    }

    private void testNoLateFee() throws SQLException {
        System.out.println("\nTesting No Late Fee Scenario");

        int memberId = -1;
        int bookId = -1;
        int borrowedBooksId = -1;

        try {
            // Create test member
            memberId = createTestMember("Gold");
            System.out.println("Created test member with ID: " + memberId);

            // Create test book
            bookId = createTestBook();
            System.out.println("Created test book with ID: " + bookId);

            // Create borrow record with no overdue
            borrowedBooksId = createBorrowRecord(memberId, bookId);
            System.out.println("Created borrow record with ID: " + borrowedBooksId);

            // Calculate and apply late fees
            LateFeeResult result = borrowingService.calculateAndApplyLateFees(borrowedBooksId);

            // Validate no late fee
            System.out.println("Expected Days Overdue: 0");
            System.out.println("Actual Days Overdue: " + result.getDaysOverdue());
            System.out.println("Expected Total Fee: 0.0");
            System.out.println("Actual Total Fee: " + result.getTotalFee());

            // Assertions
            if (result.getDaysOverdue() != 0 || result.getTotalFee() != 0.0) {
                System.err.println("Late fee calculation failed for no overdue scenario!");
            }
        } finally {
            // Clean up test data
            cleanUpTestData(memberId, bookId, borrowedBooksId);
        }
    }

    private int createTestMember(String membershipType) throws SQLException {
        String insertMemberQuery = "INSERT INTO members (first_name, last_name, email, password, date_joined, membership_type, isApproved, role, outstanding_fees) VALUES (?, ?, ?, ?, CURRENT_DATE, ?, true, 'Student', 0)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertMemberQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "Test");
            pstmt.setString(2, "Member");
            pstmt.setString(3, "test@example.com");
            pstmt.setString(4, "password123");
            pstmt.setString(5, membershipType);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to create test member");
    }

    private int createTestBook() throws SQLException {
        String insertBookQuery = "INSERT INTO books (title, author, publication_date, isbn, status, created_at, updated_at, shelf_id, total_copies, available_copies, category, description) VALUES (?, ?, CURRENT_DATE, ?, 'Available', CURRENT_DATE, CURRENT_DATE, NULL, 5, 5, 'Fiction', 'Test Book Description')";
        try (PreparedStatement pstmt = conn.prepareStatement(insertBookQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "Test Book");
            pstmt.setString(2, "Test Author");
            pstmt.setString(3, "1234567890");
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to create test book");
    }

    private int createBorrowRecord(int memberId, int bookId) throws SQLException {
        String insertBorrowQuery = "INSERT INTO borrowed_books (member_id, book_id, borrow_date, return_date) VALUES (?, ?, CURRENT_DATE, NULL)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertBorrowQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to create borrow record");
    }

    private int createOverdueBorrowRecord(int memberId, int bookId) throws SQLException {
        String insertBorrowQuery = "INSERT INTO borrowed_books (member_id, book_id, borrow_date, return_date) VALUES (?, ?, DATE_SUB(CURRENT_DATE, INTERVAL 20 DAY), NULL)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertBorrowQuery, PreparedStatement .RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to create overdue borrow record");
    }

    private void cleanUpTestData(int memberId, int bookId, int borrowedBooksId) {
        try {
            if (borrowedBooksId != -1) {
                String deleteBorrowQuery = "DELETE FROM borrowed_books WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteBorrowQuery)) {
                    pstmt.setInt(1, borrowedBooksId);
                    pstmt.executeUpdate();
                }
            }
            if (bookId != -1) {
                String deleteBookQuery = "DELETE FROM books WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteBookQuery)) {
                    pstmt.setInt(1, bookId);
                    pstmt.executeUpdate();
                }
            }
            if (memberId != -1) {
                String deleteMemberQuery = "DELETE FROM members WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteMemberQuery)) {
                    pstmt.setInt(1, memberId);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error cleaning up test data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        LateFeeCalculationTest test = new LateFeeCalculationTest();
        test.runAllTests();
    }
}