<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dean Dashboard - AUCA Library</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
     <style>
        :root {
            --primary: #4361ee;
            --secondary: #3f37c9;
            --success: #4cc9f0;
            --warning: #f72585;
            --info: #4895ef;
            --bg-primary: #f8f9fa;
            --text-primary: #2b2d42;
            --text-secondary: #6c757d;
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

        .dean-info {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .dean-avatar {
            width: 45px;
            height: 45px;
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
        }

        .stats-grid {
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
        .students-icon { background: var(--success); }
        .faculty-icon { background: var(--warning); }
        .reports-icon { background: var(--info); }

        .stat-number {
            font-size: 2rem;
            font-weight: 600;
            margin: 0.5rem 0;
            color: var(--text-primary);
        }

        .activities-section {
            background: var(--card-bg);
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
        }

        .activity-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        .view-all-btn {
            padding: 0.5rem 1rem;
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: opacity 0.3s ease;
        }

        .view-all-btn:hover {
            opacity: 0.9;
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

        .logout-button {
            padding: 0.75rem 1.5rem;
            background: linear-gradient(135deg, #f72585, #b5179e);
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            transition: opacity 0.3s ease;
            margin-left: auto;
        }

        .logout-button:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <div class="top-bar">
        <div class="logo-section">
            <i class="fas fa-university"></i>
            <span class="logo-text">AUCA Library</span>
        </div>
        <div class="dean-info">
            <div class="dean-avatar">
                <i class="fas fa-user"></i>
            </div>
            <div>
                <h3>Welcome, Dean</h3>
                <p>Faculty of Information Technology</p>
            </div>
            <button onclick="window.location.href='signin1.html'" class="logout-button">
                <i class="fas fa-sign-out-alt"></i>
                Logout
            </button>
        </div>
    </div>

    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-header">
                <div>
                    <h3>Total Books</h3>
                    <div class="stat-number">5,234</div>
                </div>
                <div class="stat-icon books-icon">
                    <i class="fas fa-book"></i>
                </div>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-header">
                <div>
                    <h3>Total Students</h3>
                    <div class="stat-number">2,567</div>
                </div>
                <div class="stat-icon students-icon">
                    <i class="fas fa-users"></i>
                </div>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-header">
                <div>
                    <h3>Total Faculty</h3>
                    <div class="stat-number">150</div>
                </div>
                <div class="stat-icon faculty-icon">
                    <i class="fas fa-chalkboard-teacher"></i>
                </div>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-header">
                <div>
                    <h3>Total Reports</h3>
                    <div class="stat-number">100</div>
                </div>
                <div class="stat-icon reports-icon">
                    <i class="fas fa-file-alt"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="activities-section">
        <div class="activity-header">
            <h2>Recent Activities</h2>
            <button class="view-all-btn">View All</button>
        </div>
        <div class="activity-list">
            <div class="activity-item">
                <div class="activity-icon">
                    <i class="fas fa-book"></i>
                </div>
                <div>
                    <p>New book "Data Structures" added to the library</p>
                    <span>10 minutes ago</span>
                </div>
            </div>
            <div class="activity-item">
                <div class="activity-icon">
                    <i class="fas fa-user-plus"></i>
                </div>
                <div>
                    <p>New faculty member "John Doe" registered</p>
                    <span>30 minutes ago</span>
                </div>
            </div>
            <div class="activity-item">
                <div class="activity-icon">
                    <i class="fas fa-chart-bar"></i>
                </div>
                <div>
                    <p>Library analytics report generated</p>
                    <span>1 hour ago</span>
                </div>
            </div>
        </div>
    </div>

    <script>
        const currentDate = new Date();
        document.getElementById("current-date").innerHTML = currentDate.toLocaleDateString();
    </script>
</body>
</html>