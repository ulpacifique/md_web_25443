<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Location Created Successfully - AUCA Library</title>
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
            display: flex;
            justify-content: center; /* Center horizontally */
            align-items: center; /* Center vertically */
            height: 100vh; /* Full height of the viewport */
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-image: url('images/university.jpg'); /* Path to your background image */
            background-size: cover; /* Change to contain if you want to fit the image */
            background-position: center;
            background-repeat: no-repeat; /* Prevents the image from repeating */
            background-attachment: fixed; /* Makes the background fixed during scroll */
            margin: 0;
            padding: 0; /* Remove padding to prevent shifting */
            color: #fff; /* Change text color to white for better contrast */
        }

        .success-container {
            background: white;
            padding: 2.5rem;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 450px;
            width: 90%;
            animation: fadeIn 0.6s ease-out;
        }

        @keyframes fadeIn {
            0% { opacity: 0; transform: translateY(30px); }
            100% { opacity: 1; transform: translateY(0); }
        }

        .success-icon {
            width: 80px;
            height: 80px;
            background: #2ecc71;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 auto 1.2rem;
        }

        .success-icon i {
            color: white;
            font-size: 40px;
        }

        h1 {
            color: #34495e;
            margin-bottom: 1rem;
            font-size: 24px;
        }

        p {
            color: #7f8c8d;
            margin-bottom: 2rem;
            line-height: 1.6;
        }

        .buttons {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            padding: 0.7rem 1.3rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s ease;
            text-decoration: none;
            font-size: 14px;
            color: white;
        }

        .btn-primary {
            background: #3498db;
        }

        .btn-secondary {
            background: #e67e22;
        }

        .btn-list {
    background: #9b59b6; /* Ensure this color is distinct */
    color: white; /* Ensure text color is white or another contrasting color */
}
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .details {
            background: #ecf0f1;
            padding: 1.2rem;
            border-radius: 8px;
            margin: 1.5rem 0;
            text-align: left;
        }

        .detail-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 0.7rem;
            color: #34495e;
        }

        .detail-label {
            font-weight: 500;
        }

        @media (max-width: 480px) {
            .buttons {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="success-container">
        <div class="success-icon">
            <i class="fas fa-check"></i>
        </div>
        
        <h1>Location Created Successfully!</h1>
        <p>The new location has been added to the library system database.</p>
        
        <div class="details">
            <div class="detail-item">
                <span class="detail-label">Location ID:</span>
                <span>#LOC123</span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Added on:</span>
                <span id="currentDate">Loading...</span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Status:</span>
                <span style="color: #2ecc71">Active</span>
            </div>
        </div>

        <div class="buttons">
            <a href="addLocation.jsp" class="btn btn-primary">
                <i class="fas fa-plus"></i> Add Another Location
            </a>
            <a href="locationsList.jsp" class="btn btn-list">
                <i class="fas fa-list"></i> Locations List
            </a>
            <a href="librarian_dashboard.jsp" class="btn btn-secondary">
                <i class="fas fa-home"></i> Back to Dashboard
            </a>
        </div>
    </div>

    <script>
        // Set current date
        document.getElementById('currentDate').textContent = new Date().toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    </script>
</body>
</html>