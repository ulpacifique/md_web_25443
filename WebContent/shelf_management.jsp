<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shelf Management | AUCA Library</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2ecc71;
            --background-color: #f4f6f9;
            --card-background: #ffffff;
            --text-color: #2c3e50;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            background-color: var(--background-color);
            color: var(--text-color);
            line-height: 1.6;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .shelf-management-wrapper {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
        }

        .card {
            background-color: var(--card-background);
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
            padding: 30px;
            transition: all 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.1);
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: var(--text-color);
        }

        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e6ed;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            border-color: var(--primary-color);
            outline: none;
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
        }

        .btn {
            display: inline-block;
            padding: 12px 25px;
            border-radius: 8px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .btn-primary {
            background-color: var(--primary-color);
            color: white;
            border: none;
        }

        .btn-primary:hover {
            background-color: #2980b9;
            transform: translateY(-2px);
        }

        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 8px;
        }

        .alert-warning {
            background-color: rgba(241, 196, 15, 0.1);
            color: #f1c40f;
        }

        .room-debug {
            background-color: #f9f9f9;
            border: 1px solid #e0e0e0;
            padding: 15px;
            margin-top: 20px;
            border-radius: 8px;
        }

        .room-debug table {
            width: 100%;
            border-collapse: collapse;
        }

        .room-debug th, .room-debug td {
            border: 1px solid #e0e0e0;
            padding: 10px;
            text-align: left;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="shelf-management-wrapper">
            <!-- Add Shelf Section -->
            <div class="card">
                <h2>Add New Shelf</h2>
                
                <form action="ShelfManagementServlet" method="post">
                    <input type="hidden" name="action" value="addShelf">
                    
                    <div class="form-group">
                        <label>Shelf Name</label>
                        <input type="text" class="form-control" name="shelfName" required 
                               placeholder="Enter shelf name (e.g., Fiction Shelf)">
                    </div>
                    
                    <div class="form-group">
                        <label>Shelf Category</label>
                        <select class="form-control" name="shelfCategory">
                            <option value="GENERAL">General</option>
                            <option value="FICTION">Fiction</option>
                            <option value="NON_FICTION">Non-Fiction</option>
                            <option value="REFERENCE">Reference</option>
                            <option value="PERIODICALS">Periodicals</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Description (Optional)</label>
                        <textarea class="form-control" name="description" 
                                  placeholder="Additional details about the shelf"></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="roomSelect">Select Room</label>
                        <select id="roomSelect" name="roomId" class="form-control" required>
                            <option value="">-- Select a Room --</option>
                            <c:choose>
                                <c:when test="${not empty rooms}">
                                    <c:forEach var="room" items="${rooms}">
                                        <option value="${room.roomId}">
                                            ${room.roomName}
                                        </option>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <option disabled>No rooms available</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>

                    <c:if test="${empty rooms}">
                        <div class="alert alert-warning">
                            <strong>Warning:</strong> No rooms found. Please add rooms to the database.
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label>Shelf Capacity</label>
                        <input type="number" class="form-control" name="capacity" 
                               min="1" required placeholder="Maximum number of books">
                    </div>
                    
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-plus-circle"></i> Create Shelf
                    </button>
                </form>

                <!-- Room Debug Information -->
                <div class="room-debug">
                    <h3>Room Debug Information</h3>
                    <c:choose>
                        <c:when test="${not empty rooms}">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Room ID</th>
                                        <th>Room Name</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="room" items="${rooms}">
                                        <tr>
                                            <td>${room.roomId}</t> <td>${room.roomName}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p>No rooms found in the database.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Shelf List Section -->
            <div class="card shelf-list">
                <h2>Existing Shelves</h2>
                
                <c:choose>
                    <c:when test="${not empty shelves}">
                        <c:forEach var="shelf" items="${shelves}">
                            <div class="shelf-item">
                                <div>
                                    <strong>${shelf.shelfName}</strong>
                                    <p>Room: ${shelf.roomName}</p>
                                    <p>Capacity: ${shelf.capacity} books</p>
                                </div>
                                
                                <div class="shelf-actions">
                                    <form action="ShelfManagementServlet" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="editShelf">
                                        <input type="hidden" name="shelfId" value="${shelf.shelfId}">
                                        <button type="submit" class="icon-btn" title="Edit Shelf">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                    </form>
                                    <form action="ShelfManagementServlet" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="deleteShelf">
                                        <input type="hidden" name="shelfId" value="${shelf.shelfId}">
                                        <button type="submit" class="icon-btn" title="Delete Shelf" 
                                                onclick="return confirm('Are you sure you want to delete this shelf?');">
                                            <i class="fas fa-trash-alt"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            <i class="fas fa-info-circle"></i> No shelves have been created yet.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const roomSelect = document.getElementById('roomSelect');
            
            roomSelect.addEventListener('change', function() {
                console.log('Selected Room ID:', this.value);
            });
        });
    </script>
</body>
</html>