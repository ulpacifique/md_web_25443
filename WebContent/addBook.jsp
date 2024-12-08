<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
    <style>
        /* General styling */
body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f0f2f5;
    margin: 0;
    padding: 0;
    color: #333;
}

.container {
    width: 90%;
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background: #ffffff;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    border-radius: 12px;
}

.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.header h1 {
    color: #007bff;
    font-size: 24px;
    font-weight: 600;
}

.nav {
    display: flex;
    gap: 15px;
}

.nav a {
    padding: 10px 15px;
    text-decoration: none;
    color: #ffffff;
    background: #007bff;
    border-radius: 5px;
    transition: background 0.3s, transform 0.2s;
}

.nav a:hover {
    background: #0056b3;
    transform: translateY(-2px);
}

.section {
    margin-top: 20px;
}

.section h2 {
    color: #007bff;
    font-size: 18px;
    margin-bottom: 10px;
}

.section label {
    font-weight: 500;
    display: block;
    margin-bottom: 5px;
}

.section input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ced4da;
    border-radius: 5px;
    transition: border-color 0.3s;
}

.section input:focus {
    border-color: #007bff;
    outline: none;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

.button {
    padding: 12px 20px;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background 0.3s, transform 0.2s;
    font-size: 16px;
}

.button:hover {
    background-color: #218838;
    transform: translateY(-2px);
}

/* Cards layout for book list */
.card {
    background: #e9ecef;
    padding: 15px;
    margin: 10px 0;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card h3 {
    margin: 0;
}

    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Add New Book</h1>
        <div class="nav">
            <a href="librarian_dashboard.jsp">Back to Dashboard</a>
        </div>
    </div>

    <form action="AddBookServlet" method="post">
        <div class="section">
            <label>Book Title:</label>
            <input type="text" name="title" required>
        </div>
        <div class="section">
            <label>Author:</label>
            <input type="text" name="author" required>
        </div>
        <div class="section">
            <label>Publication Date:</label>
            <input type="date" name="publication_date" required>
        </div>
        <div class="section">
            <label>ISBN:</label>
            <input type="text" name="isbn" required>
        </div>
        <div class="section">
            <label>Shelf ID:</label>
            <input type="text" name="shelf_id" required>
        </div>
        <div class="section">
            <label>Total Copies:</label>
            <input type="number" name="total_copies" required>
        </div>
        <div class="section">
            <label>Available Copies:</label>
            <input type="number" name="available_copies" required>
        </div>
        <div class="section">
            <label>Category:</label>
            <input type="text" name="category" required>
        </div>
        <div class="section">
            <label>Description:</label>
            <textarea name="description" required></textarea>
        </div>
        <button type="submit" class="button">Add Book</button>
    </form>
</div>
</body>
</html>
