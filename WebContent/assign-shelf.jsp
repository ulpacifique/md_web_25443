<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AUCA Library - Shelf Assignment</title>
    
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome for icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    
    <!-- Custom Styles -->
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .shelf-assignment-container {
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
            padding: 30px;
            margin-top: 50px;
            position: relative;
            overflow: hidden;
        }
        .shelf-assignment-container::before {
            content: '';
            position: absolute;
            top: -50px;
            left: -50px;
            width: 150px;
            height: 150px;
            background-color: #3498db;
            border-radius: 50%;
            z-index: 0;
        }
        .form-header {
            position: relative;
            z-index: 1;
            color: #2c3e50;
            margin-bottom: 30px;
            display: flex;
            align-items: center;
        }
        .form-header i {
            margin-right: 15px;
            color: #3498db;
            font-size: 2.5rem;
        }
        .form-select, .form-control {
            border-radius: 8px;
            padding: 12px;
            transition: all 0.3s ease;
        }
        .form-select:focus, .form-control:focus {
            border-color: #3498db;
            box-shadow: 0 0 0 0.2rem rgba(52, 152, 219, 0.25);
        }
        .btn-primary {
            background-color: #3498db;
            border-color: #3498db;
            border-radius: 8px;
            padding: 12px 25px;
            transition: all 0.3s ease;
        }
        .btn-primary:hover {
            background-color: #2980b9;
            border-color: #2980b9;
        }
        .alert {
            border-radius: 8px;
        }
        .shelf-info {
            background-color: #ecf0f1;
            border-left: 4px solid #3498db;
            padding: 15px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="shelf-assignment-container">
            <div class="form-header">
                <i class="fas fa-book-medical"></i>
                <h2 class="mb-0">Shelf Assignment Management</h2>
            </div>

            <div class="shelf-info">
                <small>
                    <i class="fas fa-info-circle text-primary me-2"></i>
                    Assign unassigned shelves to specific rooms in the AUCA Library system.
                </small>
            </div>
            
            <c:if test="${not empty message}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="fas fa-check-circle me-2"></i>
                    ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fas fa-exclamation-triangle me-2"></i>
                    ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <form action="assign-shelf-to-room" method="post">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">
                            <i class="fas fa-layer-group me-2 text-primary"></i>
                            Select Unassigned Shelf
                        </label>
                        <select name="shelfId" class="form-select" required>
                            <option value="">Choose a Shelf</option>
                            <c:forEach items="${unassignedShelves}" var="shelf">
                                <option value="${shelf.shelfId}">
                                    ${shelf.shelfName} (Capacity: ${shelf.shelfCapacity} books)
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">
                            <i class="fas fa-building me-2 text-primary"></i>
                            Select Room
                        </label>
                        <select name="roomId" class="form-select" required>
                            <option value="">Choose a Room</option>
                            <c:forEach items="${rooms}" var="room">
                                <option value="${room.roomId}">
                                    ${room.roomName} (${room.description})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="text-end">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-2"></i>Assign Shelf
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    
    <!-- Optional: Add some interactivity -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Enhance form validation
            const form = document.querySelector('form');
            form.addEventListener('submit', function(event) {
                const shelfSelect = form.querySelector('select[name="shelfId"]');
                const roomSelect = form.querySelector('select[name="roomId"]');
                
                if (!shelfSelect.value || !roomSelect.value) {
                    event.preventDefault();
                    alert('Please select both a shelf and a room.');
                }
            });
        });
    </script>
</body>
</html>