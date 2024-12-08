<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Borrow a Book | AUCA Library</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
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
        }

        body {
            font-family: 'Space Grotesk', sans-serif;
            background: var(--bg-gradient);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: var(--text);
            padding: 20px;
            position: relative;
            overflow: hidden;
        }

        body::before {
            content: '';
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: 
                radial-gradient(circle at 20% 20%, rgba(96, 165, 250, 0.15) 0%, transparent 40%),
                radial-gradient(circle at 80% 80%, rgba(244, 114, 182, 0.15) 0%, transparent 40%);
            pointer-events: none;
        }

        .container {
            background: var(--card-bg);
            backdrop-filter: blur(20px);
            border: 1px solid var(--border);
            border-radius: 24px;
            width: 100%;
            max-width: 580px;
            padding: 40px;
            position: relative;
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

        .header {
            text-align: center;
            margin-bottom: 40px;
            position: relative;
        }

        .header h1 {
            font-size: 36px;
            font-weight: 700;
            background: linear-gradient(135deg, var(--primary) 0%, var(--accent) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            margin-bottom: 12px;
        }

        .header .subtitle {
            color: var(--text-muted);
            font-size: 16px;
        }

        #memberInfo {
            background: linear-gradient(135deg, rgba(96, 165, 250, 0.1) 0%, rgba(244, 114, 182, 0.1) 100%);
            border: 1px solid var(--border);
            border-radius: 16px;
            padding: 24px;
            margin-bottom: 32px;
            backdrop-filter: blur(10px);
        }

        #memberInfo strong {
            color: var(--primary);
            display: block;
            margin-bottom: 8px;
            font-size: 15px;
        }

        #memberInfo span {
            color: var(--text);
            display: block;
            margin-bottom: 16px;
            font-size: 14px;
        }

        .alert {
            background: rgba(239, 68, 68, 0.1);
            border: 1px solid rgba(239, 68, 68, 0.2);
            color: #FCA5A5;
            padding: 16px;
            border-radius: 16px;
            margin-bottom: 32px;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .input-group {
            display: flex;
            flex-direction: column;
            gap: 20px;
            margin-bottom: 32px;
        }

        .input-group select,
        .input-group input[type="text"] {
            background: var(--input-bg);
            border: 1px solid var(--border);
            border-radius: 12px;
            padding: 16px 20px;
            color: var(--text);
            font-size: 16px;
            font-family: 'Space Grotesk', sans-serif;
            transition: all 0.3s ease;
        }

        .input-group select {
            cursor: pointer;
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke='%2394A3B8'%3E%3Cpath stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M19 9l-7 7-7-7'%3E%3C/path%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 20px center;
            background-size: 16px;
            padding-right: 50px;
        }

        .input-group select:focus,
        .input-group input[type="text"]:focus {
            border-color: var(--primary);
            outline: none;
            box-shadow: 0 0 0 3px rgba(96, 165, 250, 0.2);
        }

        input[type="submit"] {
            width: 100%;
            padding: 18px;
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            border: none;
            border-radius: 12px;
            color: white;
            font-weight: 600;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-family: 'Space Grotesk', sans-serif;
        }

        input[type="submit"]:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 16px rgba(96, 165, 250, 0.3);
        }

        input[type="submit"]:disabled {
            background: var(--text-muted);
            cursor: not-allowed;
            transform: none;
            box-shadow: none;
        }

        .footer {
            text-align: center;
            margin-top: 40px;
            color: var(--text-muted);
            font-size: 14px;
        }

        option {
            background: #1E293B;
            color: var(--text);
        }

        @media (max-width: 640px) {
            .container {
                padding: 30px 20px;
            }

            .header h1 {
                font-size: 28px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>
                <i class="fas fa-book-open"></i> 
                Library Portal
            </h1>
            <p class="subtitle">Access Our Digital Collection</p>
        </div>
        
        <c:if test="${empty sessionScope.email}">
            <div class="alert" id="loginAlert">
                <i class="fas fa-exclamation-circle"></i> 
                Authentication required to access library resources
            </div>
        </c:if>

        <c:if test="${not empty sessionScope.email}">
            <div id="memberInfo">
                <strong><i class="fas fa-envelope"></i> Email Address</strong>
                <span>${sessionScope.email}</span>
                <strong><i class="fas fa-id-badge"></i> Membership Status</strong>
                <span class="membership-type">
                    ${sessionScope.membership_type != null ? sessionScope.membership_type : 'Not Specified'}
                </span>
            </div>
        </c:if>

        <form id="borrowBookForm" method="post" action="${pageContext.request.contextPath}/BorrowBookServlet">
            <div class="input-group">
                <select id="identifierType" name="identifierType">
                    <option value="book_id">Book ID</option>
                    <option value="isbn">ISBN</option>
                </select>
                <input type="text" 
                       id="bookIdentifier" 
                       name="bookIdentifier" 
                       required 
                       placeholder="Enter Book ID or ISBN"
                       oninput="validateIdentifier(this)"
                       maxlength="50">
            </div>
            
            <input type="submit" 
                   value="Borrow Book" 
                   ${empty sessionScope.email ? 'disabled' : ''}>
        </form>

        <div class="footer">
            © 2024 AUCA Library System. All rights reserved.
        </div>
    </div>

    <script>
        function validateIdentifier(input) {
            const identifierType = document.getElementById('identifierType').value;
            input.value = identifierType === 'book_id' 
                ? input.value.replace(/[^0-9]/g, '')
                : input.value.replace(/[^a-zA-Z0-9-]/g, '');
        }

        document.getElementById('identifierType').addEventListener('change', function() {
            const input = document.getElementById('bookIdentifier');
            const type = this.value;
            
            input.placeholder = type === 'book_id' ? 'Enter Book ID' : 'Enter ISBN';
            input.pattern = type === 'book_id' ? '\\d+' : '[a-zA-Z0-9-]+';
            input.value = '';
        });
    </script>
</body>
</html>