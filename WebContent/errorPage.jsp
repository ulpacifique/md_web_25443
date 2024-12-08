<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Something went wrong!</h1>
    <p><%= request.getAttribute("errorMessage") %></p>
    <a href="location.jsp">Go back to the location page</a>
</body>
</html>