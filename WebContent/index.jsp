<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AUCA Library Management System</title>
    
    <!-- Favicon -->
    <link rel="icon" type="image/png" href="images/auca-favicon.png">
    
    <!-- Modern CSS Reset and Base Styles -->
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2ecc71;
            --background-color: #f4f6f9;
            --text-color-dark: #2c3e50;
            --text-color-light: #7f8c8d;
            --transition-speed: 0.3s;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
            background-color: var(--background-color);
            color: var(--text-color-dark);
        }

        .dashboard-container {
            display: grid;
            grid-template-columns: 250px 1fr;
            min-height: 100vh;
        }

        /* Sidebar Styling */
        .sidebar {
            background-color: #ffffff;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
            padding: 20px;
            transition: all var(--transition-speed);
        }

        .sidebar-logo {
            display: flex;
            align-items: center;
            margin-bottom: 30px;
        }

        .sidebar-logo img {
            width: 50px;
            margin-right: 15px;
        }

        .sidebar-logo h1 {
            font-size: 1.5rem;
            color: var(--primary-color);
        }

        .sidebar-menu {
            list-style: none;
        }

        .sidebar-menu li {
            margin-bottom: 10px;
        }

        .sidebar-menu a {
            text-decoration: none;
            color: var(--text-color-dark);
            display: flex;
            align-items: center;
            padding: 10px 15px;
            border-radius: 8px;
            transition: all var(--transition-speed);
        }

        .sidebar-menu a:hover,
        .sidebar-menu a.active {
            background-color: var(--primary-color);
            color: white;
            transform: translateX(10px);
        }

        .sidebar-menu a i {
            margin-right: 10px;
            font-size: 1.2rem;
        }

        /* Main Content Area */
        .main-content {
            background-color: var(--background-color);
            padding: 30px;
            overflow-y: auto;
        }

        .dashboard-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .dashboard-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }

        .stat-card {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            display: flex;
            align-items: center;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            transition: all var(--transition-speed);
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0,0,0,0.15);
        }

        .stat-icon {
            font-size: 3rem;
            margin-right: 20px;
            color: var(--primary-color);
        }

        .stat-content h3 {
            font-size: 1.2rem;
            margin-bottom: 5px;
        }

        .stat-content p {
            color: var(--text-color-light);
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .dashboard-container {
                grid-template-columns: 1fr;
            }
        }
    </style>

    <!-- Font Awesome for Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="sidebar-logo">
                <img src="images/auca logo.png" alt="AUCA Logo">
                <h1>AUCA Library</h1>
            </div>
            
            <ul class="sidebar-menu">
                <li>
                    <a href="#" class="active">
                        <i class="fas fa-home"></i> Dashboard
                    </a>
                </li>
                <li>
                    <a href="books.jsp">
                        <i class="fas fa-book"></i> Books
                    </a>
                </li>
                <li>
                    <a href="members.jsp">
                        <i class="fas fa-users"></i> Members
                    </a>
                </li>
                <li>
                    <a href="borrowings.jsp">
                        <i class="fas fa-exchange-alt"></i> Borrowings
                    </a>
                </li>
                <li>
                    <a href="reports.jsp">
                        <i class="fas fa-chart-bar"></i> Reports
                    </a>
                </li>
                <li>
                    <a href="settings.jsp">
                        <i class="fas fa-cog"></i> Settings
                    </a>
                </li>
            </ul>
        </aside>

        <!-- Main Content -->
        <main class="main-content">
            <header class="dashboard-header">
                <h2>Dashboard</h2>
                <div class="user-profile">
                    <span>Welcome, Admin</span>
                    <a href="logout" class="btn-logout">Logout</a>
                </div>
            </header>

            <!-- Dashboard Statistics -->
            <section class="dashboard-stats">
                <div class="stat-card">
                    <i class="fas fa-book stat-icon"></i>
                    <div class="stat-content">
                        <h3>Total Books</h3>
                        <p>1,245 Books</p>
                    </div>
                </div>

                <div class="stat-card">
                    <i class="fas fa-users stat-icon"></i>
                    <div class="stat-content">
                        <h3>Total Members</h3>
                        <p>567 Active Members</p>
                    </div>
                </div>

                <div class="stat-card">
                    <i class="fas fa-exchange-alt stat-icon"></i>
                    <div class="stat-content">
                        <h3>Current Borrowings</h3>
                        <p>124 Active Loans</p>
                    </div>
                </div>

                <div class="stat-card">
                    <i class="fas fa-clock stat-icon"></i>
                    <div class="stat-content">
                        <h3>Overdue Books</h3>
                        <p>12 Overdue Loans</p>
                    </div>
                 </div>
            </section>
        </main>
    </div>

    <!-- JavaScript for Interactivity -->
    <script>
        // Optional: Add any JavaScript functionality here
        document.addEventListener('DOMContentLoaded', function() {
            // Example: Add event listeners or initialize components
            console.log('Dashboard loaded successfully!');
        });
    </script>
</body>
</html>