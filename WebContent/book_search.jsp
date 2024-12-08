<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>search books</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
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
            --input-bg: rgba(15, 23, 42, 0.3);
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

        .search-container {
            background: var(--card-bg);
            backdrop-filter: blur(20px);
            border: 1px solid var(--border);
            border-radius: 24px;
            width: 100%;
            max-width: 500px;
            padding: 40px;
            text-align: center;
            box-shadow: var(--card-shadow);
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

        .search-title {
            color: var(--text);
            margin-bottom: 30px;
            font-size: 24px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .search-title i {
            margin-right: 15px;
            color: var(--primary);
            font-size: 36px;
        }

        .search-form {
            display: flex;
            flex-direction: column;
        }

        .search-input {
            width: 100%;
            padding: 16px;
            margin-bottom: 20px;
            background: var(--input-bg);
            border: 1px solid var(--border);
            border-radius: 12px;
            color: var(--text);
            font-size: 16px;
            transition: all 0.3s ease;
        }

        .search-input:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(96, 165, 250, 0.2);
        }

        .search-button {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            color: white;
            border: none;
            padding: 16px;
            border-radius: 12px;
            font-size: 18px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .search-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 16px rgba(96, 165, 250, 0.3);
        }

        .search-hint {
            margin-top: 20px;
            color: var(--text-muted);
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="search-container">
        <div class="search-title">
            <i class="fas fa-book-open"></i>
            Looking for Book-borrowed
        </div>

        <form action="MyBooksServlet" method="get" class="search-form">
            <input 
                type="number" 
                name="member_id" 
                placeholder="Enter Your Member ID" 
                class="search-input" 
                required 
                min="1"
            >
            <button type="submit" class="search-button">
                <i class="fas fa-search"></i> Search 
            </button>
        </form>

        <div class="search-hint">
            <i class="fas fa-info-circle"></i> 
            To view your borrowed book you must enter memberID
    </div>
</body>
</html>
    