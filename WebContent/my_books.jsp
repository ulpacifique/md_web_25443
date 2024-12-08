<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Borrowed Books</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #4a6cf7;
            --secondary-color: #6a7adb;
            --background-color: #f4f7ff;
            --text-color: #333;
            --white: #ffffff;
            --border-radius: 12px;
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
            color: var(--text-color);
            padding: 20px;
        }

        .container {
            background-color: var(--white);
            border-radius: var(--border-radius);
            box-shadow: 
                0 10px 30px rgba(74, 108, 247, 0.1),
                0 15px 40px rgba(74, 108, 247, 0.1);
            max-width: 800px;
            margin: 0 auto;
            padding: 40px;
            position: relative;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            border-bottom: 2px solid rgba(74, 108, 247, 0.1);
            padding-bottom: 20px;
        }

        .header h1 {
            color: var(--primary-color);
            display: flex;
            align-items: center;
            font-size: 24px;
        } ```jsp
        .header h1 i {
            margin-right: 10px;
            color: var(--secondary-color);
        }

        .book-list {
            display: grid;
            gap: 20px;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
        }

        .book-item {
            background: #f8f9fa;
            border-left: 5px solid var(--primary-color);
            padding: 20px;
            border-radius: 8px;
            transition: all 0.3s ease;
        }

        .book-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .no-books {
            text-align: center;
            color: #7f8c8d;
            padding: 30px;
            background: #f1f2f6;
            border-radius: 10px;
        }

        .btn {
            display: inline-block;
            padding: 10px 15px;
            background: var(--primary-color);
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            transition: background 0.3s ease;
            margin-top: 20px;
        }

        .btn:hover {
            background: var(--secondary-color);
        }
         /* Previous styles remain the same */
        .return-btn {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
            transition: background-color 0.3s ease;
        }

        .return-btn:hover {
            background-color: #c82333;
        }
        
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><i class="fas fa-book"></i> My Borrowed Books</h1>
        </div>

        <c:choose>
            <c:when test="${not empty borrowedBooks}">
                <div class="book-list">
                    <c:forEach var="book" items="${borrowedBooks}">
                        <div class="book-item" id="book-${book.bookId}">
                            <h3>${book.title}</h3>
                            <p><strong>Author:</strong> ${book.author}</p>
                            <p><strong>ISBN:</strong> ${book.isbn}</p>
                            <p><strong>Category:</strong> ${book.category}</p>
                            <p><strong>Status:</strong> ${book.status}</p>
                            <p><strong>Borrow Date:</strong> 
                                <fmt:formatDate value="${book.borrowDate}" pattern="yyyy-MM-dd" />
                            </p>
                            <p><strong>Due Date:</strong> 
                                <fmt:formatDate value="${book.dueDate}" pattern="yyyy-MM-dd" />
                            </p>
                            <button class="return-btn" 
                                    onclick="returnBook(${book.bookId}, ${book.borrowedBookId})">
                                Return Book
                            </button>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="no-books">
                    <p>You have not borrowed any books.</p>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="search-again">
            <a href="book_search.jsp" class="btn">Search for More Books</a>
        </div>
    </div>

    <script>
    function returnBook(bookId, borrowedBookId) {
        if (!confirm('Are you sure you want to return this book?')) {
            return;
        }

        const returnBtn = event.target;
        returnBtn.disabled = true;
        returnBtn.textContent = 'Returning...';

        fetch('/ReturnBookServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `bookId=${bookId}&borrowedBookId=${borrowedBookId}`
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const bookElement = document.getElementById(`book-${bookId}`);
                bookElement.remove();

                if (data.fineAmount > 0) {
                    alert(`Book returned successfully. Late fee: ${data.fineAmount.toFixed(2)} Rwf`);
                } else {
                    alert('Book returned successfully');
                }

                checkBookListEmpty();
            } else {
                returnBtn.disabled = false;
                returnBtn.textContent = 'Return Book';
                alert(data.message || 'Failed to return book');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            returnBtn.disabled = false;
            returnBtn.textContent = 'Return Book';
            alert('An error occurred while returning the book');
        });
    }

    function checkBookListEmpty() {
        const bookList = document.querySelector('.book-list');
        const noBooksDiv = document.createElement('div');
        noBooksDiv.classList.add('no-books');
        noBooksDiv.innerHTML = '<p>You have not borrowed any books.</p>';

        if (bookList.children.length === 0) {
            bookList.appendChild(noBooksDiv);
        }
    }

  
    </script>
</body>
</html>
