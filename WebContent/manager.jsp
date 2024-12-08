<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AUCA Library Manager Dashboard</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
</head>
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: 'Poppins', sans-serif;
    }

    body {
        background: #1a1a1f;
        color: #fff;
    }

    .dashboard-container {
        display: flex;
        min-height: 100vh;
    }

    /* Sidebar Styles */
    .sidebar {
        width: 250px;
        background: rgba(37, 37, 43, 0.95);
        border-right: 2px solid #2b27ff;
        padding: 20px;
    }

    .sidebar-header {
        text-align: center;
        padding: 20px 0;
        border-bottom: 1px solid rgba(107, 39, 255, 0.3);
    }

    .logo {
        width: 80px;
        margin-bottom: 10px;
    }

    .user-info {
        margin: 20px 0;
        text-align: center;
    }

    .user-info h3 {
        color: #fff;
        font-size: 18px;
        margin-bottom: 5px;
    }

    .user-role {
        color: #5d27ff;
        font-size: 14px;
    }

    .nav-menu {
        margin-top: 30px;
    }

    .nav-item {
        padding: 12px 15px;
        margin: 5px 0;
        display: flex;
        align-items: center;
        cursor: pointer;
        border-radius: 8px;
        transition: 0.3s;
    }

    .nav-item:hover {
        background: rgba(93, 39, 255, 0.2);
    }

    .nav-item.active {
        background: #5d27ff;
    }

    .nav-item i {
        margin-right: 10px;
        color: #5d27ff;
    }

    .nav-item.active i {
        color: #fff;
    }

    /* Main Content Styles */
    .main-content {
        flex: 1;
        padding: 20px;
    }

    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 30px;
        padding: 20px;
        background: rgba(37, 37, 43, 0.95);
        border-radius: 15px;
        border: 1px solid rgba(93, 39, 255, 0.3);
    }

    .search-bar {
        display: flex;
        align-items: center;
        background: rgba(255, 255, 255, 0.1);
        border-radius: 20px;
        padding: 8px 15px;
    }

    .search-bar input {
        background: transparent;
        border: none;
        color: #fff;
        padding: 5px;
        outline: none;
        width: 200px;
    }

    .stats-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 20px;
        margin-bottom: 30px;
    }

    .stat-card {
        background: rgba(37, 37, 43, 0.95);
        border: 1px solid rgba(93, 39, 255, 0.3);
        border-radius: 15px;
        padding: 20px;
        transition: transform 0.3s;
    }

    .stat-card:hover {
        transform: translateY(-5px);
    }

    .stat-card h3 {
        color: rgba(255, 255, 255, 0.7);
        font-size: 16px;
        margin-bottom: 10px;
    }

    .stat-card .value {
        font-size: 28px;
        color: #5d27ff;
        margin-bottom: 5px;
    }

    .stat-card .trend {
        font-size: 14px;
        color: #4CAF50;
    }

    .recent-activity {
        background: rgba(37, 37, 43, 0.95);
        border: 1px solid rgba(93, 39, 255, 0.3);
        border-radius: 15px;
        padding: 20px;
    }

    .recent-activity h2 {
        margin-bottom: 20px;
        color: #fff;
    }

    .activity-list {
        display: grid;
        gap: 15px;
    }

    .activity-item {
        display: flex;
        align-items: center;
        padding: 15px;
        background: rgba(93, 39, 255, 0.1);
        border-radius: 10px;
        transition: 0.3s;
    }

    .activity-item:hover {
        background: rgba(93, 39, 255, 0.2);
    }

    .activity-icon {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background: #5d27ff;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 15px;
    }

    .activity-details h4 {
        color: #fff;
        margin-bottom: 5px;
    }

    .activity-details p {
        color: rgba(255, 255, 255, 0.7);
        font-size: 14px;
    }

    .activity-time {
        margin-left: auto;
        color: rgba(255, 255, 255, 0.5);
        font-size: 12px;
    }
</style>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="sidebar-header">
                <img src="/api/placeholder/80/80" alt="AUCA Logo" class="logo">
                <div class="user-info">
                    <h3>John Doe</h3>
                    <span class="user-role">Library Manager</span>
                </div>
            </div>
            <nav class="nav-menu">
                <div class="nav-item active">
                    <i class="fas fa-home"></i>
                    Dashboard
                </div>
                <div class="nav-item">
                    <i class="fas fa-book"></i>
                    Books Management
                </div>
                <div class="nav-item">
                    <i class="fas fa-users"></i>
                    User Management
                </div>
                <div class="nav-item">
                    <i class="fas fa-exchange-alt"></i>
                    Loans & Returns
                </div>
                <div class="nav-item">
                    <i class="fas fa-chart-bar"></i>
                    Reports
                </div>
                <div class="nav-item">
                    <i class="fas fa-cog"></i>
                    Settings
                </div>
            </nav>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <div class="header">
                <h1>Dashboard Overview</h1>
                <div class="search-bar">
                    <i class="fas fa-search"></i>
                    <input type="text" placeholder="Search...">
                </div>
            </div>

            <!-- Statistics Grid -->
            <div class="stats-grid">
                <div class="stat-card">
                    <h3>Total Books</h3>
                    <div class="value">12,458</div>
                    <div class="trend">
                        <i class="fas fa-arrow-up"></i> +2.4% this month
                    </div>
                </div>
                <div class="stat-card">
                    <h3>Active Loans</h3>
                    <div class="value">847</div>
                    <div class="trend">
                        <i class="fas fa-arrow-up"></i> +1.8% this week
                    </div>
                </div>
                <div class="stat-card">
                    <h3>Registered Users</h3>
                    <div class="value">3,642</div>
                    <div class="trend">
                        <i class="fas fa-arrow-up"></i> +3.2% this month
                    </div>
                </div>
                <div class="stat-card">
                    <h3>Overdue Returns</h3>
                    <div class="value">24</div>
                    <div class="trend" style="color: #ff4444;">
                        <i class="fas fa-arrow-down"></i> -5.1% this week
                    </div>
                </div>
            </div>

            <!-- Recent Activity -->
            <div class="recent-activity">
                <h2>Recent Activity</h2>
                <div class="activity-list">
                    <div class="activity-item">
                        <div class="activity-icon">
                            <i class="fas fa-book"></i>
                        </div>
                        <div class="activity-details">
                            <h4>New Book Added</h4>
                            <p>"The Art of Programming" - Added to Computer Science section</p>
                        </div>
                        <span class="activity-time">2 hours ago</span>
                    </div>
                    <div class="activity-item">
                        <div class="activity-icon">
                            <i class="fas fa-user-plus"></i>
                        </div>
                        <div class="activity-details">
                            <h4>New User Registration</h4>
                            <p>Sarah Johnson - Student ID: ST2024015</p>
                        </div>
                        <span class="activity-time">3 hours ago</span>
                    </div>
                    <div class="activity-item">
                        <div class="activity-icon">
                            <i class="fas fa-exchange-alt"></i>
                        </div>
                        <div class="activity-details">
                            <h4>Book Return</h4>
                            <p>Database Management Systems - ID: BK7845</p>
                        </div>
                        <span class="activity-time">5 hours ago</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>