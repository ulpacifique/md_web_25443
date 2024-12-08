<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Locations - AUCA Library</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            background: #f0f4f8;
            color: #333;
            padding: 2rem;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
            background: white;
            border-radius: 10px;
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            font-size: 2rem;
            color: #2c3e50;
            margin-bottom: 1rem;
        }

        .locations-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1.5rem;
        }

        .locations-table th, .locations-table td {
            padding: 1rem;
            text-align: left;
        }

        .locations-table th {
            background: #3498db;
            color: white;
            font-weight: 600;
        }

        .locations-table tr:nth-child(even) {
            background: #f8f9fa;
        }

        .locations-table tr:hover {
            background: #e3f2fd;
        }

        .status {
            padding: 0.3rem 0.6rem;
            border-radius: 4px;
            font-weight: 500;
            display: inline-block;
            text-align: center;
        }

        .status.active {
            background: #2ecc71;
            color: white;
        }

        .status.inactive {
            background: #e74c3c;
            color: white;
        }

        .action-buttons {
            display: flex;
            gap: 0.5rem;
        }

        .btn {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.9rem;
            color: white;
            text-decoration: none;
            display: inline-block;
        }

        .btn-edit {
            background: #3498db;
        }

        .btn-delete {
            background: #e74c3c;
        }

        .btn:hover {
            transform: scale(1.05);
        }

        .add-location {
            display: flex;
            justify-content: flex-end;
            margin-top: 1.5rem;
        }

        .btn-add {
            padding: 0.7rem 1.5rem;
            background: #2ecc71;
            color: white;
            font-weight: 500;
            border: none;
            border-radius: 5px;
            transition: all 0.3s ease;
            cursor: pointer;
            text-decoration: none;
        }

        .btn-add:hover {
            background: #27ae60;
            transform: translateY(-2px);
        }

        @media (max-width: 768px) {
            .locations-table th, .locations-table td {
                font-size: 0.9rem;
                padding: 0.8rem;
            }

            .btn-add {
                padding: 0.6rem 1.2rem;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>All Created Locations</h1>
        
        <!-- Display Table of Locations -->
        <table class="locations-table">
            <thead>
                <tr>
                    <th>Location ID</th>
                    <th>Location Name</th>
                    <th>Created Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- Sample data row, replace this with dynamic content in your JSP -->
                <tr>
                    <td>#LOC001</td>
                    <td>Main Library</td>
                    <td>2024-11-05</td>
                    <td><span class="status active">Active</span></td>
                    <td>
                        <div class="action-buttons">
                            <a href="editLocation.jsp?locationId=LOC001" class="btn btn-edit"><i class="fas fa-edit"></i> Edit</a>
                            <a href="deleteLocation.jsp?locationId=LOC001" class="btn btn-delete"><i class="fas fa-trash"></i> Delete</a>
                        </div>
                    </td>
                </tr>
                <!-- End Sample data row -->
                
                <!-- Use a loop to dynamically generate rows for each location -->
                <%-- 
                    for(Location location : locations) {
                        String statusClass = location.isActive() ? "active" : "inactive";
                        out.write("<tr>");
                        out.write("<td>" + location.getId() + "</td>");
                        out.write("<td>" + location.getName() + "</td>");
                        out.write("<td>" + location.getCreatedDate() + "</td>");
                        out.write("<td><span class='status " + statusClass + "'>" + (location.isActive() ? "Active" : "Inactive") + "</span></td>");
                        out.write("<td><div class='action-buttons'><a href='editLocation.jsp?locationId=" + location.getId() + "' class='btn btn-edit'><i class='fas fa-edit'></i> Edit</a><a href='deleteLocation.jsp?locationId=" + location.getId() + "' class='btn btn-delete'><i class='fas fa-trash'></i> Delete</a></div></td>");
                        out.write("</tr>");
                    }
                --%>
            </tbody>
        </table>
        
        <!-- Add Location Button -->
        <div class="add-location">
            <a href="addLocation.jsp" class="btn-add"><i class="fas fa-plus"></i> Add New Location</a>
        </div>
    </div>
</body>
</html>
