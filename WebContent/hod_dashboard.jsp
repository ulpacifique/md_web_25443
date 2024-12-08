<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HOD Dashboard - AUCA Library</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #3498db;
            --secondary: #2980b9;
            --success: #2ecc71;
            --warning: #f39c12;
            --danger: #e74c3c;
            --bg-primary: #f5f6fa;
            --text-primary: #2c3e50;
            --text-secondary: #7f8c8d;
            --card-bg: #ffffff;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            background-color: var(--bg-primary);
            color: var(--text-primary);
            min-height: 100vh;
            padding: 2rem;
        }

        .top-bar {
            background: var(--card-bg);
            padding: 1.5rem 2rem;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        .logo-section {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .logo-section i {
            font-size: 2rem;
            color: var(--primary);
        }

        .logo-text {
            font-size: 1.5rem;
            font-weight: 600;
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .hod-info {
            display: flex;
            align-items: center;
            gap: 1.5rem;
        }

        .action-buttons {
            display: flex;
            gap: 1rem;
        }

        .action-button {
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            transition: all 0.3s ease;
            color: white;
        }

        .primary-button {
            background: linear-gradient(135deg, var(--primary), var(--secondary));
        }

        .secondary-button {
            background: linear-gradient(135deg, var(--success), #27ae60);
        }

        .action-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        .stat-card {
            background: var(--card-bg);
            padding: 1.5rem;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
        }

        .stat-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
        }

        .stat-icon {
            width: 50px;
            height: 50px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            color: white;
        }

        .books-icon { background: var(--primary); }
        .members-icon { background: var(--success); }
        .fines-icon { background: var(--warning); }

        .stat-number {
            font-size: 2rem;
            font-weight: 600;
            color: var(--text-primary);
            margin: 0.5rem 0;
        }

        .activities-section {
            background: var(--card-bg);
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
        }

        .activities-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        .activity-item {
            padding: 1rem;
            background: var(--bg-primary);
            border-radius: 12px;
            display: flex;
            align-items: center;
            gap: 1rem;
            margin-bottom: 1rem;
            transition: transform 0.3s ease;
        }

        .activity-item:hover {
            transform: scale(1.01);
        }

        .activity-icon {
            width: 40px;
            height: 40px;
            background: white;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: var(--primary);
        }

        .activity-info span {
            display: block;
            color: var(--text-secondary);
            font-size: 0.875rem;
            margin-top: 0.25rem;
        }
    </style>
</head>
<body>
    <div class="top-bar">
        <div class="logo-section">
            <i class="fas fa-university"></i>
            <span class="logo-text">AUCA Library</span>
        </div>
        <div class="hod-info">
            <div class="action-buttons">
                <button class="action-button primary-button">
                    <i class="fas fa-chart-bar"></i>
                    View Reports
                </button>
                <button class="action-button secondary-button">
                    <i class="fas fa-user"></i>
                    View Profile
                </button>
            </div>
        </div>
    </div>

    <div class="stats-container">
        <div class="stat-card">
            <div class="stat-header">
                <div>
                    <h3>Total Books</h3>
                    <div class="stat-number">8K</div>
                </div>
                <div class="stat-icon books-icon">
                    <i class="fas fa-book"></i>
                </div>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-header">
                <div>
                    <h3>Active Members</h3>
                    <div class="stat-number">400</div>
                </div>
                <div class="stat-icon members-icon">
                    <i class="fas fa-users"></i>
                </div>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-header">
                <div>
                    <h3>Total Fines</h3>
                    <div class="stat-number">Rwf 10,000</div>
                </div>
                <div class="stat-icon fines-icon">
                    <i class="fas fa-money-bill-wave"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="activities-section">
        <div class="activities-header">
            <h2>Recent Activities</h2>
        </div>
        <div class="activity-list">
            <div class="activity-item">
                <div class="activity-icon">
                    <i class="fas fa-book"></i>
                </div>
                <div class="activity-info">
                    <p>New book "WEB TECHNOLOGY" added to the library</p>
                    <span>2 hours ago</span>
                </div>
            </div>
            <div class="activity-item">
                <div class="activity-icon">
                    <i class="fas fa-user-plus"></i>
                </div>
                <div class="activity-info">
                    <p>New member registration: UWIHIRWE PACIFIQUE</p>
                    <span>3 hours ago</span>
                </div>
            </div>
        </div>
    </div>
</body>
</html>