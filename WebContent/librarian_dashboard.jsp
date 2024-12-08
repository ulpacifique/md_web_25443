<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="com.auca.library.util.DatabaseConnection" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Librarian Dashboard - AUCA Library</title>
    
    <!-- Modern Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    
    <!-- Icon Libraries -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    
    <style>
        :root {
            /* Color Palette */
            --primary-color: #3498db;
            --secondary-color: #2ecc71;
            --background-color: #f4f6f9;
            --text-color-dark: #2c3e50;
            --text-color-light: #7f8c8d;
            --white: #ffffff;
            
            /* Transitions */
            --transition-speed: 0.3s;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background-color: var(--background-color);
            line-height: 1.6;
            color: var(--text-color-dark);
        }

        .dashboard-container {
            display: grid;
            grid-template-columns: 280px 1fr;
            min-height: 100vh;
        }

        /* Sidebar Styling */
        .sidebar {
            background: linear-gradient(135deg, #2c3e50 0%, #3498db 100%);
            color: white;
            padding: 20px;
            box-shadow: 5px 0 15px rgba(0,0,0,0.1);
        }

        .logo {
            text-align: center;
            padding: 20px 0;
            font-size: 24px;
            font-weight: 700;
            border-bottom: 2px solid rgba(255,255,255,0.1);
            margin-bottom: 20px;
        }

        .menu-items {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .menu-item {
            display: flex;
            align-items: center;
            padding: 12px 15px;
            border-radius: 8px;
            transition: all var(--transition-speed);
            cursor: pointer;
        }

        .menu-item:hover {
            background: rgba(255,255,255,0.1);
            transform: translateX(5px);
        }

        .menu-item i {
            margin-right: 15px;
            font-size: 18px;
        }

        /* Main Content */
        .main-content {
            padding: 30px;
            background-color: var(--background-color);
            overflow-y: auto;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }

        .stat-card {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.05);
            transition: all var(--transition-speed);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        }

        .stat-card .number {
            font-size: 28px;
            font-weight: 700;
            color: var(--primary-color);
        }

        .quick-actions {
            background: white;
            border-radius: 12px;
            padding: 20px;
            margin-top: 30px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.05);
        }

        .action-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }

        .action-card {
            background: var(--background-color);
            border-radius: 10px;
            padding: 20px;
            text-align: center;
            transition: all var(--transition-speed);
            cursor: pointer;
        }

        .action-card:hover {
            background: white;
            transform: scale(1.05);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }

        .action-card i {
            font-size: 40px;
            color: var(--primary-color);
            margin-bottom: 15px;
        }

        /* Responsive Design */
        @media (max-width: 1024px) {
            .dashboard-container {
                grid-template-columns: 1fr;
            }
        }
        
        .action-buttons {
    display: flex;
    gap: 15px;
}

.action-buttons button {
    background: var(--primary-color);
    color: white;
    border: none;
    padding: 12px 20px;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
    transition: background 0.3s, transform 0.2s;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
}

.action-buttons button:hover {
    background: #2980b9;
    transform: translateY(-2px);
}

.action-buttons button:active {
    transform: translateY(0);
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.action-buttons i {
    margin-right: 8px; /* Space between icon and text */
}

 .action-buttons {
        display: flex;
        gap: 15px;
    }

    .logout-button {
        background-color: var(--primary-color);
        color: white;
        border: none;
        padding: 12px 20px;
        border-radius: 8px;
        font-size: 16px;
        cursor: pointer;
        transition: background 0.3s, transform 0.2s;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        display: flex;
        align-items: center;
    }

    .logout-button i {
        margin-right: 8px; /* Space between icon and text */
    }

    .logout-button:hover {
        background-color: #2980b9;
        transform: translateY(-2px);
    }

    .logout-button:active {
        transform: translateY(0);
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    }
    
      .action-buttons {
        display: flex;
        gap: 15px;
    }

    .logout-button {
        background-color: var(--primary-color);
        color: white;
        border: none;
        padding: 12px 20px;
        border-radius: 8px;
        font-size: 16px;
        cursor: pointer;
        transition: background 0.3s, transform 0.2s;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        display: flex;
        align-items: center;
    }

    .logout-button i {
        margin-right: 8px; /* Space between icon and text */
    }

    .logout-button:hover {
        background-color: #2980b9;
        transform: translateY(-2px);
    }

    .logout-button:active {
        transform: translateY(0);
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    }
    
    </style>
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="logo">AUCA Library</div>
            
           <div class="menu-items">
                <%
                    // Define menu items as a 2D array for better control
                    String[][] menuItems = {
                        {"Dashboard", "home", "dashboard.jsp"},
                        {"Manage Books", "book", "ViewBooksServlet"},
                        {"Manage Members", "users", "manageMembers.jsp"},
                        {"Issue/Return", "exchange-alt", "issueReturn.jsp"},
                        {"Manage Rooms", "building", "RoomManagementServlet"},
                        {"Manage Shelves", "archive", "ShelfManagementServlet"},
                        {"Settings", "cog", "settings.jsp"},
                        {"Province Lookup", "map-marker-alt", "retrieveProvince.jsp"},
                        {"Pending Memberships", "user-clock", "pendingMemberships"}
                    };

                    // Generate menu items dynamically
                    for (String[] item : menuItems) {
                        String displayName = item[0];
                        String iconName = item[1];
                        String linkTarget = item[2];
                %>
                    <div class="menu-item" onclick="window.location.href='<%= linkTarget %>'">
                        <i class="fas fa-<%= iconName %>"></i> <%= displayName %>
                    </div>
                <% } %>
            </div>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <div class="header">
    <h1>Librarian Dashboard</h1>
    <div class="action-buttons">
        <button onclick="window.location.href='addBook.jsp'">
            <i class="fas fa-plus-circle"></i> Add New Book
        </button>
        <button onclick="window.location.href='issueBook.jsp'">
            <i class="fas fa-exchange-alt"></i> Issue Book
        </button>
    </div>
</div>

<form action="BookRoomCheckServlet" method="get">
    <label for="roomId">Enter Room ID:</label>
    <input type="number" id="roomId" name="roomId" required>
    <button type="submit">Check Book Count</button>
</form>

    
   
            <!-- Stats Container -->
            <div class="stats-container">
              <%
    // Initialize variables
    int totalBooks = 0;
    int totalNewBooks = 0;
    

    // Database connection 
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        // Get database connection
        conn = DatabaseConnection.getConnection();

        // Query to count ALL books in the database
        String totalBooksQuery = "SELECT COUNT(*) AS total_books FROM books";
        stmt = conn.prepareStatement(totalBooksQuery);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            totalBooks = rs.getInt("total_books");
        }
        
        // Close current statement and result set
        rs.close();
        stmt.close();

            // Query to count new books
            

        } catch (SQLException e) {
            // Log the error
            e.printStackTrace();
        } finally {
            // Ensure resources are closed
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    
    int booksBorrowed = 0;
    // Database connection 
   

    try {
        // Get database connection
        conn = DatabaseConnection.getConnection();

        // Query to count ALL books in the database
        String booksBorrowedQuery = "SELECT COUNT(*) AS sum_books FROM borrowed_books";
        stmt = conn.prepareStatement(booksBorrowedQuery);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            totalBooks = rs.getInt("sum_books");
        }
        
        // Close current statement and result set
        rs.close();
        stmt.close();

            // Query to count new books
            

        } catch (SQLException e) {
            // Log the error
            e.printStackTrace();
        } finally {
            // Ensure resources are closed
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    %>
    
    
                <div class="stat-card">
                    <h3>Total Books</h3>
                    <div class="number"><%= totalBooks %></div>
                </div>

                <!-- Other stat cards similarly -->
                <div class="stat-card">
                    <h3>Books Borrowed</h3>
                    <div class="number"><%= booksBorrowed %></div>
                    
                </div>
                <div class="stat-card">
                    <h3>Active Members</h3>
                    <div class="number">5</div>
                </div>
                <div class="stat-card">
                    <h3>Pending Returns</h3>
                    <div class="number">9</div>
                </div>
            </div>

            <!-- Quick Actions Section -->
            <div class="quick-actions">
                <h3>Quick Library Space Management</h3>
                <div class="action-grid">
                    <div class="action-card" onclick="window.location.href='RoomManagementServlet#add-room'">
                        <i class="fas fa-plus-circle"></i>
                        <span>Add New Room</span>
                    </div>
                    <div class="action-card" onclick="window.location.href='ShelfManagementServlet#add-shelf'">
                        <i class="fas fa-plus-square"></i>
                        <span>Add New Shelf</span>
                    </div>
                    <div class="action-card" onclick="window.location.href='BookShelfAssignmentServlet'">
                        <i class="fas fa-book-medical"></i>
                        <span>Assign Book to Shelf</span>
                    </div>
                    
                    <div class="action-card" onclick="window.location.href='ShelfAssignmentServlet'">
                        <i class="fas fa-book-medical"></i>
                        <span>Assign Shelf to Room</span>
                    </div>
                </div>
            </div>

            <!-- Recent Activities Section -->
            <div class="recent-activities">
                <h3>Recent Activities</h3>
                <div class="activity-list">
                    <div class="activity-item">
                        <i class="fas fa-plus"></i> Added new book "The Great worior "
                    </div>
                    <div class="activity-item">
                        <i class="fas fa-user-plus"></i> New member "eric Manzi" registered
                    </div>
                    <div class="activity-item">
                        <i class="fas fa-exchange-alt"></i> Book "1984" issued
                    </div>
                    
                    <div class="action-buttons">
    <button onclick="window.location.href='signin1.html'" class="logout-button">
        <i class="fas fa-sign-out-alt"></i> Logout
    </button>
</div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>