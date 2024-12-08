<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.auca.library.util.DatabaseConnection" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Dashboard - AUCA Library</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    
    <style>
    
    * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Poppins', sans-serif;
    color: #fff;
}

body {
    background: #1a1a1f;
}

.dashboard-container {
    display: flex;
    min-height: 100vh;
}

.sidebar {
    width: 250px;
    background: rgba(43, 39, 255, 0.1);
    border-right: 1px solid rgba(107, 39, 255, 0.3);
    padding: 20px;
}

.logo {
    font-size: 24px;
    font-weight: bold;
    padding: 20px 0;
    text-align: center;
    border-bottom: 1px solid rgba(107, 39, 255, 0.3);
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    background: linear-gradient(45deg, #fff, #5d27ff);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
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
    gap: 10px;
    color: rgba(255, 255, 255, 0.8);
}

.menu-item:hover {
    background: rgba(93, 39, 255, 0.2);
    transform: translateX(5px);
}

.menu-item i {
    color: #5d27ff;
}

.main-content {
    flex: 1;
    background: rgba(37, 37, 43, 0.95);
    padding: 20px;
}

.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
    background: rgba(43, 39, 255, 0.1);
    padding: 20px;
    border-radius: 10px;
    border: 1px solid rgba(107, 39, 255, 0.3);
}

.stats-container {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 20px;
    margin-bottom: 30px;
}

.stat-card {
    background: rgba(43, 39, 255, 0.1);
    padding: 20px;
    border-radius: 10px;
    border: 1px solid rgba(107, 39, 255, 0.3);
    transition: transform 0.3s ease;
}

.stat-card:hover {
    transform: translateY(-5px);
    border-color: #5d27ff;
}

.stat-card h3 {
    color: rgba(255, 255, 255, 0.9);
    margin-bottom: 10px;
}

.stat-card .number {
    font-size: 24px;
    font-weight: bold;
    color: #5d27ff;
}

.recent-activities {
    background: rgba(43, 39, 255, 0.1);
    padding: 20px;
    border-radius: 10px;
    border: 1px solid rgba(107, 39, 255, 0.3);
}

.activity-list {
    margin-top: 20px;
}

.activity-item {
    padding: 15px;
    border-bottom: 1px solid rgba(107, 39, 255, 0.3);
    display: flex;
    align-items: center;
}

.activity-item i {
    margin-right: 15px;
    color: #5d27ff;
}

.action-buttons {
    display: flex;
    gap: 10px;
    margin-top: 20px;
}

.action-button {
    padding: 10px 20px;
    border: 2px solid #6b27ff;
    border-radius: 40px;
    cursor: pointer;
    transition: all 0.3s;
    display: flex;
    align-items: center;
    gap: 8px;
    background: transparent;
    color: #fff;
    font-size: 14px;
    font-weight: 500;
}

.primary-button {
    background: transparent;
}

.primary-button:hover {
    background: #6b27ff;
    box-shadow: 0 0 20px rgba(107, 39, 255, 0.4);
}

.secondary-button {
    background: rgba(93, 39, 255, 0.2);
}

.secondary-button:hover {
    background: #6b27ff;
    box-shadow: 0 0 20px rgba(107, 39, 255, 0.4);
}

.logout-button {
    background: transparent;
    border: 2px solid #ff4444;
    color: #ff4444;
    padding: 10px 20px;
    border-radius: 40px;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s;
    display: flex;
    align-items: center;
}

.logout-button i {
    margin-right: 8px;
    color: #ff4444;
}

.logout-button:hover {
    background: #ff4444;
    color: #fff;
    box-shadow: 0 0 20px rgba(255, 68, 68, 0.4);
}

.logout-button:hover i {
    color: #fff;
}

/* Return Book Modal Styles */
.return-book-modal {
    display: none;
    position: fixed;
    z-index: 1000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    justify-content: center;
    align-items: center;
}

.return-book-container {
    background: rgba(37, 37, 43, 0.95);
    padding: 30px;
    border-radius: 15px;
    width: 500px;
    border: 2px solid #2b27ff;
    box-shadow: 0 0 25px rgba(57, 39, 255, 0.3);
}

.return-book-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid rgba(107, 39, 255, 0.3);
    padding-bottom: 15px;
    margin-bottom: 20px;
}

.book-list {
    max-height: 300px;
    overflow-y: auto;
}

.book-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    border-bottom: 1px solid rgba(107, 39, 255, 0.3);
}

.fine-details {
    background: rgba(43, 39, 255, 0.1);
    padding: 15px;
    border-radius: 5px;
    margin-top: 20px;
    border: 1px solid rgba(107, 39, 255, 0.3);
}
    </style>
    
    
    
    
    
    <script>
        function checkMembershipStatus() {
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
                try (ResultSet rs = pstmt.executeQuery()) { if (rs.next()) {
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
            <div class="logo">
                <i class="fas fa-book-reader"></i>
                AUCA Library
            </div>
            <div class="menu-items">
                <div class="menu-item">
                    <i class="fas fa-home"></i> Dashboard
                </div>
                <div class="menu-item">
                    <i class="fas fa-book"></i>
                    <button class="action-button primary-button" onclick="window.location.href='MyBooksServlet'">My Books</button>
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
                <h1>Teacher Dashboard</h1>
                <h1>Welcome, <%= username %></h1>
                <p>Email: <%= email %></p>
                <div class="action-buttons">
                    <button class="action-button primary-button" onclick="window.location.href='borrowBook.jsp'">
                        <i class="fas fa-book"></i> Borrow Book
                    </button>
                    <button class="action-button secondary-button"  onclick="window.location.href='registration.jsp'">
                        <i class="fas fa-user"></i> join membership
                    </button>
                </div>
            </div>

            <div class="stats-container">
                <div class="stat-card">
                    <h3><i class="fas fa-book"></i> Total Books Borrowed</h3>
                    <div class="number"><%= totalBorrowedBooks %></div>
                </div>
                <div class="stat-card">
                    <h3><i class="fas fa-clock"></i> Books Due Today</h3>
                    <div class="number"><%= booksDueToday %></div>
                </div>
                <div class="stat-card">
                    <h3><i class="fas fa-money-bill"></i> Fine Amount</h3>
                    <div class="number">Rwf <%= String.format("%.2f", fineAmount) %></div>
                </div>
            </div>
            
            <div id="return-book-modal" class="return-book-modal">
    <div class="return-book-container">
        <div class="return-book-header">
            <h2>Return Books</h2>
            <button onclick="closeReturnBookModal()" style="background:none; border:none; font-size:24px;">&times;</button>
        </div>
        
        <div class="book-list" id="borrowed-books-list">
            <!-- Borrowed books will be dynamically populated here -->
        </div>

        <div class="fine-details">
           <p><strong>Borrow Date:</strong> 
    <fmt:formatDate value="${book.borrowDate}" pattern="yyyy-MM-dd" />
</p>
<p><strong>Due Date:</strong> 
    <fmt:formatDate value="${book.dueDate}" pattern="yyyy-MM-dd" />
</p>
<p><strong>Fine:</strong> 
    <fmt:formatNumber value="${book.fineAmount}" type="currency" currencyCode="RWF" />
</p>
        </div>

        <div class="return-actions">
            <button class="action-button secondary-button" onclick="closeReturnBookModal()">Cancel</button>
        </div>
    </div>
</div>
            

            <div class="recent-activities">
                <h2><i class="fas fa-history"></i> Recent Activities</h2>
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
            </div>

            <div class="action-buttons">
                <button class="action-button primary-button" onclick="checkMembershipStatus()">
                    <i class="fas fa-check-circle"></i> Check Membership Status
                </button>
                <button class="action-button secondary-button">
                    <i class="fas fa-user"></i> View Profile
                </button>
                <button class="action-button primary-button" onclick="openReturnBookModal()">
        <i class="fas fa-undo"></i> Return Book
    </button>
                
                <button onclick="window.location.href='signin1.html'" class="logout-button">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </button>
            </div>
        </div>
    </div>
</body>
</html>