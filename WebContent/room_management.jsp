<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Room Management | AUCA Library</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2ecc71;
            --background-color: #f4f6f9;
            --card-background: #ffffff;
            --text-color: #2c3e50;
            --border-radius: 12px;
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
            max-width: 1400px;
            margin: 0 auto;
            padding: 20px;
        }

        .room-management-wrapper {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
        }

        .card {
            background-color: var(--card-background);
            border-radius: var(--border-radius);
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
            padding: 30px;
            transition: all 0.3s ease;
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            border-bottom: 2px solid var(--background-color);
            padding-bottom: 15px;
        }

        .card-header h2 {
            margin: 0;
            font-weight: 600;
            color: var(--primary-color);
        }

        .form-grid {
            display: grid;
            grid-template-columns: 1fr;
            gap: 15px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group label {
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
            padding: 12px 25px;
            border-radius: 8px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.3s ease;
            cursor: pointer;
            border: none;
        }

        .btn-primary {
            background-color: var(--primary-color);
            color: white;
        }

        .btn-primary:hover {
            background-color: #2980b9;
            transform: translateY(-2px);
        }

        .room-list-container {
            max-height: 600px;
            overflow-y: auto;
        }

        .room-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 15px;
            transition: all 0.3s ease;
        }

        .room-item:hover {
            background-color: #f0f0f0;
            transform: translateX( 5px);
        }

        .room-details {
            flex-grow: 1;
        }

        .room-details h3 {
            margin: 0 0 5px 0;
            color: var(--primary-color);
        }

        .room-details p {
            margin: 0;
            color: #6c757d;
        }

        .room-actions {
            display: flex;
            gap: 10px;
        }

        .icon-btn {
            background: none;
            border: none;
            cursor: pointer;
            color: #6c757d;
            transition: color 0.3s ease;
        }

        .icon-btn:hover {
            color: var(--primary-color);
        }

        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 8px;
            display: flex;
            align-items: center;
        }

        .alert i {
            margin-right: 10px;
            font-size: 1.2em;
        }

        .alert-success {
            background-color: rgba(46, 204, 113, 0.1);
            color: #2ecc71;
        }

        .alert-danger {
            background-color: rgba(231, 76, 60, 0.1);
            color: #e74c3c;
        }

        @media (max-width: 768px) {
            .room-management-wrapper {
                grid-template-columns: 1fr;
            }
        }

        .no-rooms-message {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 50px 20px;
            background-color: #f8f9fa;
            border-radius: 12px;
        }

        .text-center {
            text-align: center;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="room-management-wrapper">
            <!-- Add Room Section -->
            <div class="card">
                <div class="card-header">
                    <h2>Add New Room</h2>
                </div>
                
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">
                        <i class="fas fa-check-circle"></i> ${successMessage}
                    </div>
                </c:if>
                
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">
                        <i class="fas fa-exclamation-triangle"></i> ${errorMessage}
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/RoomManagementServlet" method="post" class="form-grid">
                    <input type="hidden" name="action" value="addRoom">
                    
                    <div class="form-group">
                        <label for="roomName">Room Name</label>
                        <input type="text" id="roomName" name="roomName" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="roomDescription">Description</label>
                        <textarea id="roomDescription" name="roomDescription" class="form-control" rows="4"></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="capacity">Capacity</label>
                        <input type="number" id="capacity" name="capacity" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="buildingId">Building ID</label>
                        <input type="number" id="buildingId" name="buildingId" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Create Room</button>
                    </div>
                </form>
            </div>

            <!-- Room List Section -->
            <div class="card room-list-container">
                <div class="card-header">
                    <h2>Existing Rooms</h2>
                </div>
                
                <c:choose>
                    <c:when test="${not empty rooms}">
                        <c:forEach var="room" items="${rooms}">
                            <div class="room-item">
                                <div class="room-details">
                                    <h3>${room.roomName}</h3>
                                    <p>${room.description}</p>
                                    <small>Capacity: ${room.capacity} | Building ID: ${room.buildingId}</small>
                                </div>
                                <div class="room-actions">
                                    <button class="icon-btn" title="Edit Room">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <button class="icon-btn" title="Delete Room">
                                        <i class="fas fa-trash-alt"></i>
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-rooms-message">
                            <p class="text-center">
                                <i class="fas fa-box-open" style="font-size: 3rem; color: #bdc3c7;"></i>
                            </p>
                            <p class="text-center" style="color: #7f8c8d;">
                                No rooms have been added yet. Create your first room!
                            </p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div> 
        </div> 
    </div> 
</body>
</html>