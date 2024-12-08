<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Borrowed Successfully - AUCA Library</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
      <style>
        :root {
            --bg-gradient: linear-gradient(135deg, #0F172A 0%, #1E293B 100%);
            --card-bg: rgba(30, 41, 59, 0.7);
            --primary: #60A5FA;
            --primary-dark: #3B82F6;
            --accent: #F472B6;
            --text: #E2E8F0;
            --text-muted: #94A3B8;
            --border: rgba(148, 163, 184, 0.1);
            --card-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.36);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Space Grotesk', sans-serif;
        }

        body {
            background: var(--bg-gradient);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .success-container {
            background: var(--card-bg);
            padding: 2rem;
            border-radius: 24px;
            box-shadow: var(--card-shadow);
            text-align: center;
            max-width: 500px;
            width: 90%;
            animation: fadeIn 0.6s ease-out;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .success-icon {
            width: 80px;
            height: 80px;
            background: var(--primary);
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 auto 1.5rem;
        }

        .success-icon i {
            color: white;
            font-size: 40px;
        }

        h1 {
            color: var(--text);
            margin-bottom: 1rem;
            font-size: 24px;
        }

        p {
            color: var(--text-muted);
            margin-bottom: 2rem;
            line-height: 1.6;
        }

        .buttons {
            display: flex;
            gap: 1rem;
            justify-content: center;
        }

        .btn {
            padding: 0.8rem 1.5rem;
            border: none;
            border-radius: 12px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s ease;
            text-decoration: none;
            font-size: 14px;
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            color: white;
        }

        .btn-secondary {
            background: var(--accent);
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 16px rgba(96, 165, 250, 0.3);
        }

        .details {
            background: rgba(255, 255, 255, 0.1);
            padding: 1rem;
            border-radius: 8px;
            margin: 1.5rem 0;
            text-align: left;
        }

        .detail-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 0.5rem;
            color: var(--text-muted);
        }

        .detail-item:last-child {
            margin-bottom: 0;
        }

        .detail-label {
            font-weight: 500;
            color: var(--text);
        }

        @media (max-width: 480px) {
            .buttons {
                flex-direction: column;
            }

            .btn {
                width: 100%;
                text-align: center;
            }
        }
    </style>
</head>
<body>
    <div class="success-container">
        <div class="success-icon">
            <i class="fas fa-check"></i>
        </div>
        
        <h1>Book Borrowed Successfully!</h1>
        <p>You have successfully borrowed the book from the AUCA Library.</p>
        
        <div class="details">
            <div class="detail-item">
                <span class="detail-label">Book ID:</span>
                <span>#<%= request.getParameter("bookId") %></span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Member ID:</span>
                <span>#<%= request.getParameter("memberId") %></span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Borrowed on:</span>
                <span id="currentDate">Loading...</span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Status:</span>
                <span style="color: var(--accent)">Borrowed</span>
            </div>
        </div>

        <div class="buttons">
            <a href="borrowBook.jsp" class="btn">
                <i class="fas fa-book"></i> Borrow Another Book
            </a>
            <a href="student_dashboard.jsp" class="btn btn-secondary">
                <i class="fas fa-home"></i> Back to Dashboard
            </a>
        </div>
    </div>

    <script>
        // Set current date
        document.getElementById('currentDate').textContent = new Date().toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    </script>
</body>
</html>
    