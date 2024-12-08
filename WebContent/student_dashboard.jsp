<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.auca.library.util.DatabaseConnection" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard - AUCA Library</title>
    
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        .dashboard-container {
            display: flex;
            min-height: 100vh;
        }

        .sidebar {
            width: 250px;
            background: #2c3e50;
            color: white;
            padding: 20px;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            padding: 20px 0;
            text-align: center;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }

        .menu-items {
            margin-top: 20px;
        }

        .menu-item {
            padding: 15px;
            cursor: pointer;
            transition: all 0.3s;
            border-radius: 5px;
            margin-bottom: 5px;
            display: flex;
            align-items: center;
        }

        .menu-item i {
            margin-right: 10px;
        }

        .menu-item:hover {
            background: #34495e;
        }

        .main-content {
            flex: 1;
            background: #f5f6fa;
            padding: 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .stat-card h3 {
            color: #2c3e50;
            margin-bottom: 10px;
        }

        .stat-card .number {
            font-size: 24px;
            font-weight: bold;
            color: #3498db;
        }

        .recent-activities {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .activity-list {
            margin-top: 20px;
        }

        .activity-item {
            padding: 15px;
            border-bottom: 1px solid #eee;
            display: flex;
            align-items: center;
        }

        .activity-item:last-child {
            border-bottom: none;
        }

        .activity-item i {
            margin-right: 15px;
            color: #3498db;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }

        .action-button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .primary-button {
            background: #3498db;
            color: white;
        }

        .secondary-button {
            background: #2ecc71;
            color: white;
        }

        .logout-button {
            background: #e74c3c;
            color: white;
        }

        .action-button:hover {
            opacity: 0.9;
        }

        .action-button i {
            margin-right: 5px;
        }
    </style>
    
    <script>
        function checkMembershipStatus() {
            // Retrieve the email from a session variable
            const email = "<%= session.getAttribute("email") %>";

            const xhr = new XMLHttpRequest();
            xhr.open("GET", "CheckMembershipStatusServlet?email=" + encodeURIComponent(email), true);
            xhr.onload = function() {
                if (xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    alert(response.message);
                } else {
                    alert("Error checking membership status. Please try again.");
                }
            };
            xhr.send();
        }
    </script>
</head>
<body>
    <%
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            response.sendRedirect("signin1.html");
            return;
        }
    
        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");

        // Initialize variables for dashboard statistics
        int totalBorrowedBooks = 0;
        int booksDueToday = 0;
        double fineAmount = 0.0;

        // Database connection and query
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Query for Total Borrowed Books
            String borrowedBooksQuery = "SELECT COUNT(*) AS total_books FROM borrowed_books bb " +
                "JOIN members m ON bb.member_id = m.id " +
                "WHERE m.email = ? AND bb.return_date IS NULL";
            
            try (PreparedStatement pstmt = conn.prepareStatement(borrowedBooksQuery)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        totalBorrowedBooks = rs.getInt("total_books");
                    }
                }
            }

            // Query for Books Due Today
            String booksDueTodayQuery = "SELECT COUNT(*) AS due_books FROM borrowed_books bb " +
                "JOIN members m ON bb.member_id = m.id " +
                "WHERE m.email = ? AND bb.due_date <= CURRENT_DATE AND bb.return_date IS NULL";
            
            try (PreparedStatement pstmt = conn.prepareStatement(booksDueTodayQuery)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        booksDueToday = rs.getInt("due_books");
                    }
                }
            }

            // Query for Fine Amount
            String fineQuery = "SELECT COALESCE(SUM(fine_amount), 0) AS total_fine FROM borrowed_books bb " +
                "JOIN members m ON bb.member_id = m.id " +
                "WHERE m.email = ? AND bb.return_date IS NULL";
            
            try (PreparedStatement pstmt = conn.prepareStatement(fineQuery)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        fineAmount = rs.getDouble("total_fine");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    %>

    <div class="dashboard-container">
        <div class="sidebar">
            <div class="logo">LIBRARY OF AUCA</div>
            <div class="menu-items">
                <div class="menu-item">
                    <i class="fas fa-home"></i> Dashboard
                </div>
                <div class="menu-item">
                    <button class="action-button primary-button" onclick="window.location.href='my_books.jsp'">My Books</button>
                </div>
                <div class="menu-item">
                    <i class="fas fa-user"></i> Profile
                </div>
                <div class="menu-item">
                    <i class="fas fa-cog"></i> Settings
                </div>
            </div>
        </div>

        <div class="main-content">
            <div class="header">
                <h1>Student Dashboard</h1>
                <h1>Welcome, <%= username %></h1>
                <p>Email: <%= email %></p>
                <div class="action-buttons">
                    <button class="action-button primary-button" onclick="window.location.href='borrowBook.jsp'">Borrow Book</button>
                    <button class="action-button secondary-button">View Profile</button>
                </div>
            </div>

            <div class="stats-container">
                <div class="stat-card">
                    <h3>Total Books Borrowed</h3>
                    <div class="number"><%= totalBorrowedBooks %></div>
                </div>
                <div class="stat-card">
                    <h3>Books Due Today</h3>
                    <div class="number"><%= booksDueToday %></div>
                </div>
                <div class="stat-card">
                    <h3>Fine Amount</h3>
                    <div class="number">Rwf <%= String.format("%.2f", fineAmount) %></div>
                </div>
            </div>

            <div class="recent-activities">
                <h2>Recent Activities</h2>
                <div class="activity-list">
                    <%
                        // Fetch recent activities
                        try (Connection conn = DatabaseConnection.getConnection()) {
                            String activitiesQuery = "SELECT b.title, bb.borrow_date, bb.return_date " +
                                "FROM borrowed_books bb " +
                                "JOIN books b ON bb.book_id = b.book_id " +
                                "JOIN members m ON bb.member_id = m.id " +
                                "WHERE m.email = ? " +
                                "ORDER BY bb.borrow_date DESC LIMIT 2";
                            
                            try (PreparedStatement pstmt = conn.prepareStatement(activitiesQuery)) {
                                pstmt.setString(1, email);
                                try (ResultSet rs = pstmt.executeQuery()) {
                                    while (rs.next()) {
                                        String bookTitle = rs.getString("title");
                                        Date borrowDate = rs.getDate("borrow_date");
                                        Date returnDate = rs.getDate("return_date");
                    %>
                        <div class="activity-item">
                            <i class="fas fa-book"></i>
                            <span>
                                <% if (returnDate == null) { %>
                                    Borrowed book "<%= bookTitle %>" on <%= borrowDate %>
                                <% } else { %>
                                    Returned book "<%= bookTitle %>" on <%= returnDate %>
                                <% } %>
                            </span>
                        </div>
                    <%
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    %>
                </div>
                
                <div class="action-buttons">
                    <button onclick="window.location.href='signin1.html'" class="logout-button">
                        <i class="fas fa-sign-out-alt"></i> Logout
                    </button>
                </div>
            </div>

            <!-- Button to Check Membership Approval Status -->
            <div class="action-buttons">
                <button class="action-button primary-button" onclick="checkMembershipStatus()">Check Membership Status</button>
                <button class="action-button secondary-button"  onclick="window.location.href='registration.jsp'">register for Membership</button>
            </div>
        </div>
    </div>
</body>
</html>