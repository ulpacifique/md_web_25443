<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AUCA Library - Room Book Inventory</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .book-count-container {
            max-width: 600px;
            margin: 50px auto;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
            padding: 30px;
            text-align: center;
        }
        .book-count-badge {
            font-size: 3rem;
            font-weight: bold;
            color: #2c3e50;
            background-color: #ecf0f1;
            border-radius: 50%;
            width: 150px;
            height: 150px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }
        .room-info {
            background-color: #3498db;
            color: white;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .additional-info {
            background-color: #f1c40f;
            color: #2c3e50;
            padding: 10px;
            border-radius: 8px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="book-count-container">
            <% 
            Integer roomId = (Integer) request.getAttribute("roomId");
            Integer bookCount = (Integer) request.getAttribute("bookCount");
            String roomName = (String) request.getAttribute("roomName"); // Assuming you pass room name
            %>
            
            <div class="room-info">
                <h2 class="mb-0">Library Room Inventory</h2>
            </div>
            
            <div class="book-count-badge">
                <%= bookCount %>
            </div>
            
            <div class="details mt-4">
                <h4>Room Details</h4>
                <p class="text-muted">
                    <strong>Room ID:</strong> <%= roomId %>
                    <% if(roomName != null) { %>
                        | <strong>Room Name:</strong> <%= roomName %>
                    <% } %>
                </p>
            </div>
            
            <div class="additional-info">
                <small>
                    <i class="fas fa-info-circle"></i> 
                    This count represents the total number of books currently located in this room.
                </small>
            </div>
            
            <div class="mt-4">
                <a href="librarian_dashboard.jsp" class="btn btn-primary">
                    Back to Dashboard
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/your-fontawesome-kit.js" crossorigin="anonymous"></script>
</body>
</html>