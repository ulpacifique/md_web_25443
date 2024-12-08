<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Borrowing Limit Reached</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2ecc71;
            --background-color: #f4f6f7;
            --text-color: #2c3e50;
            --card-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, var(--background-color) 0%, #e0e6ed 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
            line-height: 1.6;
        }

        .book-limit-container {
            background: white;
            border-radius: 20px;
            box-shadow: var(--card-shadow);
            max-width: 500px;
            width: 100%;
            padding: 40px;
            text-align: center;
            position: relative;
            overflow: hidden;
            border: 2px solid var(--primary-color);
        }

        .book-limit-container::before {
            content: '';
            position: absolute;
            top: -50%;
            left: -50%;
            width: 200%;
            height: 200%;
            background: rgba(52, 152, 219, 0.05);
            transform: rotate(-45deg);
            z-index: -1;
        }

        .limit-icon {
            font-size: 80px;
            color: var(--primary-color);
            margin-bottom: 20px;
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.05); }
            100% { transform: scale(1); }
        }

        .limit-title {
            font-size: 24px;
            font-weight: 700;
            color: var(--text-color);
            margin-bottom: 15px;
        }

        .limit-message {
            color: #7f8c8d;
            margin-bottom: 25px;
            font-size: 16px;
        }

        .membership-details {
            background: var(--background-color);
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 25px;
        }

        .membership-details p {
            margin: 5px 0;
            font-weight: 600;
            color: var(--text-color);
        }

        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 15px;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 12px 25px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background-color: var(--primary-color);
            color: white;
        }

        .btn-primary:hover {
            background-color: #2980b9;
            transform: translateY(-3px);
        }

        .btn-secondary {
            background-color: var(--background-color);
            color: var(--text-color);
            border: 2px solid var(--primary-color);
        }

        .btn-secondary:hover {
            background-color: var(--primary-color);
            color: white;
            transform: translateY(-3px);
        }
    </style>
</head>
<body>
    <div class="book-limit-container">
        <div class="limit-icon">
            <i class="fas fa-book-reader"></i>
        </div>
        
        <h2 class="limit-title">Borrowing Limit Reached</h2>
        
        <p class="limit-message">
            You have reached the maximum number of books allowed for your current membership type.
        </p>
        
        <div class="membership-details">
            <p>Membership Type: ${sessionScope.membershipType}</p>
            <p>Maximum Books Allowed: ${sessionScope.maxBooks}</p>
            <p>Current Borrowed Books: ${sessionScope.currentBorrowedBooks}</p>
        </div>
        
        <div class="action-buttons">
            <a href="upgrade_membership.jsp" class="btn btn-primary">
                <i class="fas fa-arrow-up"></i> Upgrade Membership
            </a>
            <a href="book_catalog.jsp" class="btn btn-secondary">
                <i class="fas fa-book"></i> Browse Books
            </a>
        </div>
    </div>

    <script>
        // Optional: Add some interactivity
        document.addEventListener('DOMContentLoaded', () => {
            const container = document.querySelector('.book-limit-container');
            container.addEventListener('mousemove', (e) => {
                const rect = container.getBoundingClientRect();
                const x = e.clientX - rect.left;
                const y = e.clientY - rect.top;
                
                container.style.setProperty('--x', `${x}px`);
                container.style.setProperty('--y', `${y}px`);
            });
        });
    </script>
</body>
</html>