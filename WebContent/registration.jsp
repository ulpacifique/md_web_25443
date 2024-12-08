<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register for Library Membership</title>
    <link rel="stylesheet" href="styling.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <div class="container">
        <h2>Register for Library Membership</h2>
        <form action="${pageContext.request.contextPath}/RegistrationServlet" method="post">
            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName" required>
            </div>
            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <select id="role" name="role" required>
                    <option value="STUDENT">Student</option>
                    <option value="TEACHER">Teacher</option>
                </select>
            </div>
            <div class="form-group">
                <label for="membershipType">Membership Type:</label>
                <select id="membershipType" name="membershipType" required>
                    <option value="GOLD">Gold (5 books, 50 Rwf/day)</option>
                    <option value="SILVER">Silver (3 books, 30 Rwf/day)</option>
                    <option value="STRIVER">Striver (2 books, 10 Rwf/day)</option>
                </select>
            </div>
            <button type="submit" class="btn">Register <i class="fas fa-user-plus"></i></button>
            <c:if test="${not empty errorMessage}">
                <div class="error">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="success">${message}</div>
            </c:if>
        </form>
    </div>
</body>
</html>