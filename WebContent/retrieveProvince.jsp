<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retrieve Province by Phone Number</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            background: rgba(255, 255, 255, 0.95);
            padding: 2rem;
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 500px;
            backdrop-filter: blur(10px);
            transform: translateY(0);
            transition: all 0.3s ease;
        }

        .container:hover {
            transform: translateY(-5px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
        }

        h1 {
            color: #2d3748;
            text-align: center;
            margin-bottom: 2rem;
            font-size: 2em;
            font-weight: 600;
            position: relative;
        }

        h1::after {
            content: '';
            position: absolute;
            bottom: -10px;
            left: 50%;
            transform: translateX(-50%);
            width: 50px;
            height: 4px;
            background: linear-gradient(90deg, #667eea, #764ba2);
            border-radius: 2px;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #4a5568;
            font-weight: 500;
        }

        input[type="text"] {
            width: 100%;
            padding: 1rem;
            border: 2px solid #e2e8f0;
            border-radius: 10px;
            font-size: 1rem;
            transition: all 0.3s ease;
            outline: none;
        }

        input[type="text"]:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.25);
        }

        button {
            width: 100%;
            padding: 1rem;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 1rem;
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }

        button:active {
            transform: translateY(0);
        }

        #result {
            margin-top: 2rem;
            padding: 1rem;
            border-radius: 10px;
            background: #f7fafc;
            text-align: center;
            font-size: 1.1rem;
            color: #2d3748;
            transition: all 0.3s ease;
            opacity: 0;
            transform: translateY(20px);
        }

        #result.show {
            opacity: 1;
            transform: translateY(0);
        }

        .loading {
            display: none;
            text-align: center;
            margin-top: 1rem;
        }

        .loading::after {
            content: '';
            display: inline-block;
            width: 20px;
            height: 20px;
            border: 3px solid #667eea;
            border-radius: 50%;
            border-top-color: transparent;
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            to {
                transform: rotate(360deg);
            }
        }

        @media (max-width: 480px) {
            .container {
                padding: 1.5rem;
            }

            h1 {
                font-size: 1.5em;
            }

            input[type="text"] {
                padding: 0.8rem;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Province Lookup</h1>
        <!-- Important: Changed the form to use the original working function -->
        <form id="provinceForm">
            <div class="form-group">
                <label for="phoneNumber">Enter Phone Number</label>
                <input type="text" id="phoneNumber" name="phoneNumber" required 
                       placeholder="e.g., +250 XXX XXX XXX">
            </div>
            <button type="button" onclick="getProvince()">Find Province</button>
        </form>
        <div class="loading" id="loading"></div>
        <div id="result"></div>
    </div>

    <script>
        // Simplified JavaScript function that was working before
        function getProvince() {
            var phoneNumber = document.getElementById("phoneNumber").value;
            var resultDiv = document.getElementById("result");
            
            // Show loading
            document.getElementById("loading").style.display = "block";
            
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "GetProvinceByPhoneServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4) {
                    // Hide loading
                    document.getElementById("loading").style.display = "none";
                    
                    if (xhr.status == 200) {
                        resultDiv.innerHTML = xhr.responseText;
                        resultDiv.style.display = "block";
                        resultDiv.classList.add('show');
                        
                        // Style based on response
                        if (xhr.responseText.includes("Error")) {
                            resultDiv.style.backgroundColor = "#FED7D7";
                            resultDiv.style.color = "#C53030";
                        } else {
                            resultDiv.style.backgroundColor = "#C6F6D5";
                            resultDiv.style.color = "#2F855A";
                        }
                    } else {
                        resultDiv.innerHTML = "Error connecting to server";
                        resultDiv.style.backgroundColor = "#FED7D7";
                        resultDiv.style.color = "#C53030";
                        resultDiv.classList.add('show');
                    }
                }
            };

            // Send the request
            xhr.send("phoneNumber=" + encodeURIComponent(phoneNumber));
        }
    </script>
</body>
</html>