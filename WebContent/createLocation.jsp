<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Location</title>
    <style>
          body {
            font-family: 'Arial', sans-serif;
            background-color: #fafafa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 400px;
        }
        h2 {
            text-align: center;
            color: #262626;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #dbdbdb;
            border-radius: 5px;
            font-size: 14px;
        }
        input[type="submit"] {
            background-color: #0095f6;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #007bbd;
        }
        .message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create New Location</h2>
        <form action="${pageContext.request.contextPath}/createLocation" method="post" id="locationForm">
            <label for="locationType">Location Type:</label>
            <select name="locationType" id="locationType" required>
                <option value="">Select Location Type</option>
                <option value="province">Province</option>
                <option value="district">District</option>
                <option value="sector">Sector</option>
                <option value="cell">Cell</option>
                <option value="village">Village</option>
            </select>

            <div id="parentLocationContainer">
                <label for="parentId">Parent Location:</label>
                <select name="parentId" id="parentId" disabled>
                    <option value="">Select Parent Location</option> 
                </select>
            </div>

            <label for="locationName">Name:</label>
            <input type="text" id="locationName" name="locationName" required>

            <input type="submit" value="Create Location">
            <div class="error" id="errorMessage"></div> 
        </form>
    </div>

    <script>
        // ... (previous JavaScript code) ...

        function fetchParentLocations(locationType) {
            const url = '${pageContext.request.contextPath}/getParentLocations?locationType=' + locationType;

            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json(); // Assuming data is returned as JSON
                })
                .then(data => {
                    populateParentDropdown(data);
                    parentIdSelect.disabled = false; // Enable dropdown after populating
                })
                .catch(error => {
                    console.error('There was a problem fetching parent locations:', error);
                    errorMessageDiv.textContent = 'Error fetching parent locations.';
                });
        }

        function populateParentDropdown(locations) {
            parentIdSelect.innerHTML = '<option value="">Select Parent Location</option>';
            locations.forEach(location => {
                const option = document.createElement('option');
                option.value = location.id;
                option.text = location.name;
                parentIdSelect.add(option);
            });
        }

        function validateForm() {
            const locationName = document.getElementById('locationName').value.trim();
            errorMessageDiv.textContent = ''; // Clear previous error messages

            if (locationName === "") {
                errorMessageDiv.textContent = 'Please enter a location name.';
                return false;
            }

            if (parentLocationContainer.style.display !== "none" && parentIdSelect.value === "") {
                errorMessageDiv.textContent = 'Please select a parent location.';
                return false;
            }

            return true; // Form is valid
        }
    </script>
</body>
</html>