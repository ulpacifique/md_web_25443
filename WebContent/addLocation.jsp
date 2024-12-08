<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.auca.library.util.DatabaseConnection" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Location</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-image: url('images/university.jpg'); /* Path to your background image */
    background-size: cover; /* Change to contain if you want to fit the image */
    background-position: center;
    background-repeat: no-repeat; /* Prevents the image from repeating */
    background-attachment: fixed; /* Makes the background fixed during scroll */
    margin: 0;
    padding: 20px;
    color: #fff; /* Change text color to white for better contrast */
}
        h1 {
            text-align: center;
            color: #fff; /* White color for the heading */
            margin-bottom: 30px;
            font-size: 2.5rem;
            font-weight: 600;
        }
        .form-container {
            background: rgba(255, 255, 255, 0.9); /* White background with transparency */
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
            max-width: 600px;
            margin: 0 auto;
        }
        h2 {
            color: #495057;
            margin-bottom: 20px;
            font-weight: 500;
            border-bottom: 2px solid #28a745;
            padding-bottom: 10px;
        }
        input[type="text"],
        select {
            width: 100%;
            padding: 12px 15px;
            margin: 10px 0;
            border: 1px solid #ced4da;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s ease;
        }
        input[type="text"]:focus,
        select:focus {
            border-color: #28a745;
            outline: none;
            box-shadow: 0 0 5px rgba(40, 167, 69, 0.5);
        }
        button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease, transform 0.2s ease;
            width: 100%;
            margin-top: 20px;
        }
        button:hover {
            background-color: #218838;
            transform: translateY(-2px);
        }
        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
        }
        @media (max-width: 576px) {
            h1 {
                font-size: 2rem;
            }
            .form-container {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <h1>Create Location</h1>

    <div class="form-container">
        <h2>Create Location</h2>
        <form action="AddLocationServlet" method="post">
            <select name="province" id="provinceId" required>
                <option value="">Select Province</option>
                <% 
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM provinces");
                         ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                %>
                <option value="<%= rs.getInt("id") %>"><%= rs.getString("name") %></option>
                <% 
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                %>
            </select>
            <select name="district" id 
="districtId" required>
                <option value="">Select District</option>
            </select>
            <select name="sector" id="sectorId" required>
                <option value="">Select Sector</option>
            </select>
            <select name="cell" id="cellId" required>
                <option value="">Select Cell</option>
            </select>
            <select name="village" id="villageId" required>
                <option value="">Select Village</option>
            </select>
            <button type="submit"><i class="fas fa-plus"></i> Create Location</button>
        </form>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            // Fetch districts based on province selection
            $('#provinceId').change(function () {
                var provinceId = $(this).val();
                if (provinceId) {
                    $.ajax({
                        url: 'GetDistrictsServlet',
                        type: 'GET',
                        data: { provinceId: provinceId },
                        success: function (data) {
                            $('#districtId').html('<option value="">Select District</option>');
                            $.each(data, function (index, district) {
                                $('#districtId').append('<option value="' + district.id + '">' + district.name + '</option>');
                            });
                        },
                        error: function () {
                            alert("Error loading districts");
                        }
                    });
                } else {
                    $('#districtId').html('<option value="">Select District</option>');
                }
            });

            // Similar AJAX for sector, cell, and village based on selections
            $('#districtId').change(fetchSectors);
            $('#sectorId').change(fetchCells);
            $('#cellId').change(fetchVillages);

            function fetchSectors() {
                var districtId = $('#districtId').val();
                if (districtId) {
                    $.ajax({
                        url: 'GetSectorsServlet',
                        type: 'GET',
                        data: { districtId: districtId },
                        success: function (data) {
                            $('#sectorId').html('<option value="">Select Sector</option>');
                            $.each(data, function (index, sector) {
                                $('#sectorId').append('<option value="' + sector.id + '">' + sector.name + '</option>');
                            });
                        },
                        error: function () {
                            alert("Error loading sectors");
                        }
                    });
                }
            }

            function fetchCells() {
                var sectorId = $('#sectorId').val();
                if (sectorId) {
                    $.ajax({
                        url: 'GetCellsServlet',
                        type: 'GET',
                        data: { sectorId: sectorId },
                        success: function (data) {
                            $('#cellId').html('<option value="">Select Cell</option>');
                            $.each(data, function (index, cell) {
                                $('#cellId').append('<option value="' + cell.id + '">' + cell.name + '</option>');
                            });
                        },
                        error: function () {
                            alert("Error loading cells");
                        }
                    });
                }
            }

            function fetchVillages() {
                var cellId = $('#cellId').val();
                if (cellId) {
                    $.ajax({
                        url: 'GetVillagesServlet',
                        type: 'GET',
                        data: { cellId: cellId },
                        success: function (data) {
                            $('#villageId').html('<option value="">Select Village</option>');
                            $.each(data, function (index, village) {
                                $('#villageId').append('<option value="' + village.id + '">' + village.name + '</option>');
                            });
                        },
                        error: function () {
                            alert("Error loading villages");
                        }
                    });
                }
            }
        });
    </script>
</body>
</html>