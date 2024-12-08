package com.auca.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookBorrowingService {
    private Connection connection;

    public BookBorrowingService(Connection connection) {
        this.connection = connection;
    }

    // Inner class to represent Late Fee Result
    public static class LateFeeResult {
        private int daysOverdue;
        private double totalFee;

        public LateFeeResult(int daysOverdue, double totalFee) {
            this.daysOverdue = daysOverdue;
            this.totalFee = totalFee;
        }

        public int getDaysOverdue() {
            return daysOverdue;
        }

        public double getTotalFee() {
            return totalFee;
        }
    }

    public LateFeeResult calculateAndApplyLateFees(int borrowedBooksId) throws SQLException {
        // Query to get borrow details with member's membership type
        String query = "SELECT " +
            "bb.borrow_date, " +
            "m.membership_type, " +
            "m.id AS member_id " +
            "FROM borrowed_books bb " +
            "JOIN members m ON bb.member_id = m.id " +
            "WHERE bb.id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, borrowedBooksId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
                    String membershipType = rs.getString("membership_type");
                    int memberId = rs.getInt("member_id");

                    // Calculate days overdue (assuming 14-day borrow period)
                    LocalDate today = LocalDate.now();
                    long daysOverdue = ChronoUnit.DAYS.between(borrowDate.plusDays(14), today);
                    
                    // Ensure negative days are treated as 0
                    daysOverdue = Math.max(0, daysOverdue);

                    // Calculate daily rate based on membership type
                    double dailyRate = getDailyRateForMembership(membershipType);
                    double totalFee = daysOverdue * dailyRate;

                    // Insert late fee record and update member's outstanding fees
                    if (totalFee > 0) {
                        insertLateFeeRecord(borrowedBooksId, memberId, daysOverdue, totalFee);
                        updateMemberOutstandingFees(memberId, totalFee);
                    }

                    return new LateFeeResult((int)daysOverdue, totalFee);
                }

                // If no record found, return zero fees
                return new LateFeeResult(0, 0.0);
            }
        }
    }

    private double getDailyRateForMembership(String membershipType) {
        switch (membershipType) {
            case "Gold":
                return 50.0;
            case "Silver":
                return 30.0;
            case "Striver":
                return 10.0;
            default:
                return 0.0;
        }
    }

    private void insertLateFeeRecord(int borrowedBooksId, int memberId, long daysOverdue, double totalFee) throws SQLException {
        String insertQuery = "INSERT INTO late_fees " +
            "(member_id, id, days_overdue, fee_amount, fee_date) " +
            "VALUES (?, ?, ?, ?, CURRENT_DATE)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, borrowedBooksId);
            pstmt.setLong(3, daysOverdue);
            pstmt.setDouble(4, totalFee);
            pstmt.executeUpdate();
        }
    }

    private void updateMemberOutstandingFees(int memberId, double additionalFees) throws SQLException {
        String updateQuery = "UPDATE members SET outstanding_fees = outstanding_fees + ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, additionalFees);
            pstmt.setInt(2, memberId);
            pstmt.executeUpdate();
        }
    }
}