<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AUCA Library - Book Shelf Assignment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2ecc71;
            --background-color: #f4f6f9;
            --text-color: #2c3e50;
        }
        body {
            background-color: var(--background-color);
            font-family: 'Inter', 'Arial', sans-serif;
        }
        .assignment-container {
            max-width: 700px;
            margin: 50px auto;
            background-color: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }
        .form-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .form-header h2 {
            color: var(--text-color);
            font-weight: 700;
        }
        .select2-container {
            width: 100% !important;
        }
        .assignment-details {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="assignment-container">
            <div class="form-header">
                <h2>Book Shelf Assignment</h2>
                <p class="text-muted">Efficiently manage library resources</p>
            </div>

            <%-- Alert Messages --%>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <%-- Assignment Details --%>
            <div class="assignment-details">
                <div class="row">
                    <div class="col-md-6">
                        <strong>Total Books:</strong> ${books.size()}
                    </div>
                    <div class="col-md-6">
                        <strong>Available Shelves:</strong> ${shelves.size()}
                    </div>
                </div>
            </div>

            <%-- Book Shelf Assignment Form --%>
            <form id="bookShelfForm" action="BookShelfAssignmentServlet" method="post">
                <div class="mb-4">
                    <label for="bookId" class="form-label">Select Book</label>
                    <select name="bookId" id="bookId" class="form-select select2" required>
                        <option value="">Choose a Book</option>
                        <c:forEach var="book" items="${books}">
                            <option value="${book.bookId}">
                                ${book.title} (ID: ${book.bookId}) - ${book.author}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-4">
                    <label for="shelfId" class="form-label">Select Shelf</label>
                    <select name="shelfId" id="shelfId" class="form-select select2" required>
                        <option value="">Choose a Shelf</option>
                        <c:forEach var="shelf" items="${shelves}">
                            <option value="${shelf.shelfId}">
                                ${shelf.shelfName} 
                                (Capacity: ${shelf.capacity} | 
                                Available: ${shelf.capacity - shelf.currentBookCount})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary w-100">
                    <i class="bi bi-bookmark-plus me-2"></i>Assign Book to Shelf
                </button>
            </form>
        </div>
    </div>

    <%-- Scripts --%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script>
        $(document).ready(function() {
            // Initialize Select2
            $('.select2').select2({
                placeholder: 'Select an option',
                allowClear: true,
                width: '100%'
            });

            // Form validation
            $('#bookShelfForm').on('submit', function(e) {
                const bookId = $('#bookId').val();
                const shelfId = $('#shelfId').val();

                if (!bookId || !shelfId) {
                    e.preventDefault();
                    Swal.fire({
                        icon: 'error',
                        title: 'Incomplete Selection',
                        text: 'Please select both a book and a shelf.'
                    });
                }
            });
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>