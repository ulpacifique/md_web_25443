<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Dashboard - AUCA Library</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
       <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Outfit', sans-serif;
        }

        body {
            background: #1a1c23;
            color: #e2e8f0;
        }

        .navbar {
            background: #2d3748;
            padding: 1rem 2rem;
            box-shadow: 0 4px 20px rgba(0,0,0,0.2);
            position: sticky;
            top: 0;
            z-index: 100;
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }

        .nav-content {
            max-width: 1400px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            display: flex;
            align-items: center;
            gap: 12px;
            color: #10b981;
            font-weight: 600;
            font-size: 1.4rem;
        }

        .nav-menu {
            display: flex;
            gap: 2.5rem;
            align-items: center;
        }

        .nav-item {
            color: #94a3b8;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-size: 1rem;
            transition: all 0.3s;
            padding: 0.5rem 1rem;
            border-radius: 8px;
        }

        .nav-item:hover {
            color: #10b981;
            background: rgba(16, 185, 129, 0.1);
        }

        .main-content {
            max-width: 1400px;
            margin: 2rem auto;
            padding: 0 2rem;
        }

        .header {
            background: #2d3748;
            padding: 2rem;
            border-radius: 1rem;
            box-shadow: 0 4px 20px rgba(0,0,0,0.2);
            margin-bottom: 2rem;
            border: 1px solid rgba(255,255,255,0.1);
        }

        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .registrar-info {
            display: flex;
            align-items: center;
            gap: 1.5rem;
        }

        .registrar-avatar {
            width: 4rem;
            height: 4rem;
            border-radius: 1rem;
            background: #10b981;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.5rem;
            box-shadow: 0 0 20px rgba(16, 185, 129, 0.3);
        }

        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        .stat-card {
            background: #2d3748;
            padding: 1.8rem;
            border-radius: 1rem;
            box-shadow: 0 4px 20px rgba(0,0,0,0.2);
            transition: all 0.3s;
            border: 1px solid rgba(255,255,255,0.1);
            position: relative;
            overflow: hidden;
        }

        .stat-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, transparent, rgba(16, 185, 129, 0.5), transparent);
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 30px rgba(0,0,0,0.3);
        }

        .stat-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .stat-header h3 {
            color: #94a3b8;
            font-size: 1.1rem;
            font-weight: 500;
        }

        .stat-icon {
            width: 3.5rem;
            height: 3.5rem;
            border-radius: 1rem;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.4rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        }

        .students-icon { background: linear-gradient(135deg, #10b981, #047857); }
        .courses-icon { background: linear-gradient(135deg, #6366f1, #4338ca); }
        .faculty-icon { background: linear-gradient(135deg, #f59e0b, #b45309); }
        .reports-icon { background: linear-gradient(135deg, #ec4899, #be185d); }

        .stat-number {
            font-size: 2rem;
            font-weight: 600;
            color: #ffffff;
            margin-top: 1rem;
        }

        .recent-activities {
            background: #2d3748;
            padding: 2rem;
            border-radius: 1rem;
            box-shadow: 0 4px 20px rgba(0,0,0,0.2);
            border: 1px solid rgba(255,255,255,0.1);
        }

        .activity-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        .activity-header h2 {
            font-size: 1.5rem;
            color: #e2e8f0;
        }

        .activity-list {
            display: grid;
            gap: 1rem;
        }

        .activity-item {
            padding: 1.2rem;
            border-radius: 1rem;
            background: rgba(255,255,255,0.05);
            display: flex;
            align-items: center;
            gap: 1.2rem;
            transition: all 0.3s;
            border: 1px solid rgba(255,255,255,0.05);
        }

        .activity-item:hover {
            background: rgba(255,255,255,0.08);
            transform: translateX(5px);
        }

        .activity-item p {
            color: #e2e8f0;
            font-weight: 500;
        }

        .activity-item span {
            color: #94a3b8;
            font-size: 0.9rem;
            display: block;
            margin-top: 0.3rem;
        }

        .view-all-btn {
            padding: 0.7rem 1.5rem;
            border: none;
            border-radius: 0.7rem;
            background: #10b981;
            color: white;
            cursor: pointer;
            transition: all 0.3s;
            font-weight: 500;
            font-size: 0.95rem;
        }

        .view-all-btn:hover {
            background: #059669;
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(16, 185, 129, 0.3);
        }

        .date {
            background: rgba(255,255,255,0.05);
            padding: 0.7rem 1.2rem;
            border-radius: 0.7rem;
            display: flex;
            align-items: center;
            gap: 0.8rem;
            color: #94a3b8;
            border: 1px solid rgba(255,255,255,0.1);
        }

        @media (max-width: 768px) {
            .nav-menu {
                display: none;
            }
            
            .stats-container {
                grid-template-columns: 1fr;
            }

            .header-content {
                flex-direction: column;
                gap: 1rem;
                align-items: flex-start;
            }

            .main-content {
                padding: 0 1rem;
            }
        }
    </style>
</head>
<body>
    <!-- Rest of the HTML structure remains the same as in your code -->
    <nav class="navbar">
        <div class="nav-content">
            <div class="logo">
                <i class="fas fa-university"></i>
                AUCA Library
            </div>
            <div class="nav-menu">
                <a href="#" class="nav-item">
                    <i class="fas fa-home"></i>
                    Dashboard
                </a>
                <a href="#" class="nav-item">
                    <i class="fas fa-user-graduate"></i>
                    Students
                </a>
                <a href="#" class="nav-item">
                    <i class="fas fa-book-open"></i>
                    Courses
                </a>
                <a href="#" class="nav-item">
                    <i class="fas fa-users"></i>
                    Faculty
                </a>
                <a href="#" class="nav-item">
                    <i class="fas fa-chart-bar"></i>
                    Reports
                </a>
                <a href="#" class="nav-item">
                    <i class="fas fa-cog"></i>
                    Settings
                </a>
            </div>
        </div>
    </nav>

    <main class="main-content">
        <div class="header">
            <div class="header-content">
                <div class="registrar-info">
                    <div class="registrar-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                    <div>
                        <h2>Welcome, Registrar</h2>
                        <p>AUCA Administration</p>
                    </div>
                </div>
                <div class="date">
                    <i class="fas fa-calendar"></i>
                    <span id="current-date"></span>
                </div>
            </div>
        </div>

        <div class="stats-container">
            <div class="stat-card">
                <div class="stat-header">
                    <div>
                        <h3>Total Students</h3>
                        <div class="stat-number">3,567</div>
                    </div>
                    <div class="stat-icon students-icon">
                        <i class="fas fa-user-graduate"></i>
                    </div>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-header">
                    <div>
                        <h3>Total Courses</h3>
                        <div class="stat-number">150</div>
                    </div>
                    <div class="stat-icon courses-icon">
                        <i class="fas fa-book-open"></i>
                    </div>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-header">
                    <div>
                        <h3>Total Faculty</h3>
                        <div class="stat-number">120</div>
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
                        <div class="stat-number">50</div>
                    </div>
                    <div class="stat-icon reports-icon">
                        <i class="fas fa-file-alt"></i>
                    </div>
                </div>
            </div>
        </div>

        <div class="recent-activities">
            <div class="activity-header">
                <h2>Recent Activities</h2>
                <button class="view-all-btn">View All</button>
            </div>
            <div class="activity-list">
                <div class="activity-item">
                    <div class="activity-icon students-icon">
                        <i class="fas fa-user-graduate"></i>
                    </div>
                    <div>
                        <p>New student "Peace Mukamana" registered</p>
                        <span>10 minutes ago</span>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon courses-icon">
                        <i class="fas fa-book-open"></i>
                    </div>
                    <div>
                        <p>New course "Advanced Web Development" added</p>
                        <span>3 minutes ago</span>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon faculty-icon">
                        <i class="fas fa-chalkboard-teacher"></i>
                    </div>
                    <div>
                        <p>Faculty member "Dr. lazaro" updated profile</p>
                        <span>1 hour ago</span>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <script>
        const currentDate = new Date();
        document.getElementById("current-date").innerHTML = currentDate.toLocaleDateString();
    </script>
</body>
</html>