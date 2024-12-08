<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>Create Account</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">
    <link rel='stylesheet' href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css'>
    <style>
    * {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Poppins', sans-serif;
  color: aliceblue;
}

body {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: #1a1a1f;
  padding: 2rem;
}

.container {
  position: relative;
  width: 900px;
  min-height: 600px;
  max-height: 80vh;
  border: 2px solid #2b27ff;
  border-radius: 15px;
  box-shadow: 0 0 25px rgba(57, 39, 255, 0.3);
  overflow-y: auto;
  padding: 2rem;
  background: rgba(37, 37, 43, 0.95);
}

.container::-webkit-scrollbar {
  width: 8px;
}

.container::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 4px;
}

.container::-webkit-scrollbar-thumb {
  background: #3927ff;
  border-radius: 4px;
}

.form-box {
  position: relative;
  width: 50%;
  padding-right: 2rem;
}

.form-box h2 {
  font-size: 28px;
  text-align: center;
  margin-bottom: 1.5rem;
  color: #fff;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.input-box {
  position: relative;
  width: 100%;
  height: 50px;
  margin-bottom: 1.5rem;
}

.input-box input,
.input-box select {
  width: 100%;
  height: 100%;
  background: transparent;
  border: none;
  outline: none;
  font-size: 16px;
  color: #fff;
  font-weight: 500;
  border-bottom: 2px solid rgba(255, 255, 255, 0.5);
  padding: 0 35px 0 5px;
  transition: border-color 0.3s ease;
}

.input-box select {
  appearance: none;
  cursor: pointer;
}

.input-box option {
  color: #fff;
  background-color: #2a2a30;
  padding: 10px;
}

.input-box label {
  position: absolute;
  top: 50%;
  left: 5px;
  transform: translateY(-50%);
  font-size: 16px;
  color: rgba(255, 255, 255, 0.7);
  pointer-events: none;
  transition: 0.3s ease;
}

.input-box input:focus ~ label,
.input-box input:valid ~ label,
.input-box select:focus ~ label,
.input-box select:valid ~ label {
  top: -5px;
  font-size: 14px;
  color: #5d27ff;
}

.input-box i {
  position: absolute;
  top: 50%;
  right: 5px;
  transform: translateY(-50%);
  font-size: 18px;
  color: rgba(255, 255, 255, 0.7);
  transition: 0.3s ease;
}

.input-box input:focus ~ i,
.input-box input:valid ~ i,
.input-box select:focus ~ i,
.input-box select:valid ~ i {
  color: #5627ff;
}

.btn {
  width: 100%;
  height: 45px;
  background: transparent;
  border: 2px solid #6b27ff;
  border-radius: 40px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  color: #fff;
  position: relative;
  overflow: hidden;
  transition: 0.5s ease;
}

.btn:hover {
  background: #6b27ff;
  color: #fff;
  box-shadow: 0 0 20px rgba(107, 39, 255, 0.4);
}

.reg-link {
  font-size: 14px;
  text-align: center;
  margin: 20px 0;
}

.reg-link a {
  text-decoration: none;
  color: #5d27ff;
  font-weight: 600;
  transition: 0.3s ease;
}

.reg-link a:hover {
  color: #7b52ff;
  text-decoration: underline;
}

.info-content {
  position: absolute;
  top: 0;
  right: 0;
  width: 45%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 2rem;
  background: linear-gradient(135deg, rgba(43, 39, 255, 0.1), rgba(107, 39, 255, 0.2));
  border-left: 1px solid rgba(107, 39, 255, 0.3);
}

.info-content h3 {
  text-transform: uppercase;
  font-size: 32px;
  line-height: 1.3;
  margin-bottom: 1rem;
  background: linear-gradient(45deg, #fff, #5d27ff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.info-content p {
  font-size: 16px;
  color: rgba(255, 255, 255, 0.8);
  line-height: 1.6;
}

.curved-shape {
  display: none;
}

@media (max-width: 768px) {
  .container {
    width: 100%;
    padding: 1rem;
  }

  .form-box {
    width: 100%;
    padding-right: 0;
  }

  .info-content {
    display: none;
  }
}

/* Form Sections */
.form-section {
    margin-bottom: 2rem;
    padding-bottom: 1rem;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.form-section h3 {
    font-size: 18px;
    color: #fff;
    margin-bottom: 1.5rem;
    font-weight: 500;
}

/* Terms Checkbox */
.checkbox-wrapper {
    display: flex;
    align-items: center;
    gap: 10px;
    margin: 1rem 0;
}

.checkbox-wrapper input[type="checkbox"] {
    width: 18px;
    height: 18px;
    accent-color: #5d27ff;
}

.checkbox-wrapper label {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.8);
}

.checkbox-wrapper a {
    color: #5d27ff;
    text-decoration: none;
}

.checkbox-wrapper a:hover {
    text-decoration: underline;
}

/* Features Section */
.features {
    margin-top: 2rem;
}

.feature-item {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 1rem;
}

.feature-item i {
    font-size: 24px;
    color: #5d27ff;
}

.feature-item span {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.9);
}

/* Password Validation */
input:invalid {
    border-bottom-color: #ff4646;
}

.password-requirements {
    font-size: 12px;
    color: rgba(255, 255, 255, 0.6);
    margin-top: 5px;
}

/* Responsive Adjustments */
@media (max-width: 768px) {
    .form-section h3 {
        font-size: 16px;
    }
    
    .feature-item {
        margin-bottom: 0.8rem;
    }
    
    .feature-item i {
        font-size: 20px;
    }
}
    </style>
    <script>
        function validateForm() {
            // Your existing validation logic
            var province = document.getElementById("province").value;
            var district = document.getElementById("district").value;
            var sector = document.getElementById("sector").value;
            var cell = document.getElementById("cell").value;
            var village = document.getElementById("village").value;

            // Add location validation
            if (province === "") {
                document.getElementById("message").innerHTML = "Please select a province.";
                return false;
            }
            if (district === "") {
                document.getElementById("message").innerHTML = "Please select a district.";
                return false;
            }
            if (sector === "") {
                document.getElementById("message").innerHTML = "Please select a sector.";
                return false;
            }
            if (cell === "") {
                document.getElementById("message").innerHTML = "Please select a cell.";
                return false;
            }
            if (village === "") {
                document.getElementById("message").innerHTML = "Please select a village.";
                return false;
            }

            return true;
        }

        $(document).ready(function() {
            // Province to District Dropdown
            $('#province').change(function() {
                var provinceId = $(this).val();
                if (provinceId) {
                    $.ajax({
                        url: 'GetDistrictsServlet',
                        type: 'GET',
                        data: { provinceId: provinceId },
                        success: function(data) {
                            $('#district').html('<option value="">Select District</option>');
                            $.each(data, function(index, district) {
                                $('#district').append('<option value="' + district.id + '">' + district.name + '</option>');
                            });
                            // Reset dependent dropdowns
                            $('#sector').html('<option value="">Select Sector</option>');
                            $('#cell').html('<option value="">Select Cell</option>');
                            $('#village').html('<option value="">Select Village</option>');
                        },
                        error: function() {
                            alert("Error loading districts");
                        }
                    });
                } else {
                    $('#district').html('<option value="">Select District</option>');
                }
            });

            // District to Sector Dropdown
            $('#district').change(function() {
                var districtId = $(this).val();
                if (districtId) {
                    $.ajax({
                        url: 'GetSectorsServlet',
                        type: 'GET',
                        data: { districtId: districtId },
                        success: function(data) {
                            $('#sector').html('<option value="">Select Sector</option>');
                            $.each(data, function(index, sector) {
                                $('#sector').append('<option value="' + sector.id + '">' + sector.name + '</option>');
                            });
                            // Reset dependent dropdowns
                            $('#cell').html('<option value="">Select Cell</option>');
                            $('#village').html('<option value="">Select Village</option>');
                        },
                        error: function() {
                            alert("Error loading sectors");
                        }
                    });
                } else {
                    $('#sector').html('<option value="">Select Sector</option>');
                }
            });

            // Sector to Cell Dropdown
            $('#sector').change(function() {
                var sectorId = $(this).val();
                if (sectorId) {
                    $.ajax({
                        url: 'GetCellsServlet',
                        type: 'GET',
                        data: { sectorId: sectorId },
                        success: function(data) {
                            $('#cell').html('<option value="">Select Cell</option>');
                            $.each(data, function(index, cell) {
                                $('#cell').append('<option value="' + cell.id + '">' + cell.name + '</option>');
                            });
                            // Reset dependent dropdown
                            $('#village').html('<option value="">Select Village</option>');
                        },
                        error: function() {
                            alert("Error loading cells");
                        }
                    });
                } else {
                    $('#cell').html('<option value="">Select Cell</option>');
                }
            });

            // Cell to Village Dropdown
            $('#cell').change(function() {
                var cellId = $(this).val();
                if (cellId) {
                    $.ajax({
                        url: 'GetVillagesServlet',
                        type: 'GET',
                        data: { cellId: cellId },
                        success: function(data) {
                            $('#village').html('<option value="">Select Village</option>');
                            $.each(data, function(index, village) {
                                $('#village').append('<option value="' + village.id + '">' + village.name + '</option>');
                            });
                        },
                        error: function() {
                            alert("Error loading villages");
                        }
                    });
                } else {
                    $('#village').html('<option value="">Select Village</option>');
                }
            });
        });

        function submitForm(event) {
            event.preventDefault();

            if (!validateForm()) {
                return;
            }

            var formData = {
                username: document.getElementById("username").value,
                email: document.getElementById("email").value,
                password: document.getElementById("password").value,
                phoneNumber: document.getElementById("phoneNumber").value,
                province: document.getElementById("province").value,
                district: document.getElementById("district").value,
                sector: document.getElementById("sector").value,
                cell: document.getElementById("cell").value,
                village: document.getElementById("village").value,
                role: document.getElementById("role").value
            };

            console.log("Form data to be sent:", formData);

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "CreateAccountServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onload = function() {
                if (xhr.status === 200) {
                    console.log("Server response:", xhr.responseText);
                    if (xhr.responseText.startsWith("Success")) {
                        window.location.href = "signin1.html";
                    } else {
                        document.getElementById("message").innerHTML = xhr.responseText;
                    }
                } else {
                    console.log("Error response:", xhr.responseText);
                    document.getElementById("message").innerHTML = "Error creating account: " + xhr.responseText;
                }
            };

            var encodedData = Object.keys(formData).map(function(key) {
                return encodeURIComponent(key) + '=' + encodeURIComponent(formData[key]);
            }).join('&');

            console.log("Encoded data to be sent:", encodedData);

            xhr.send(encodedData);
        }
    </script>
</head>
<body>
    <div class="container">
    <div class="form-box Register">
        <form id="createAccountForm" onsubmit="submitForm(event);">
            <h2>Create Account</h2>
            <div id="message" style="color: red;"></div>
            <div class="input-box">
          
            <input type="text" id="username" name="username" required>
              <label for="username">Username:</label>
            <i class='bx bx-user-circle'></i>
            </div>
            
            
             
            <div class="input-box"> 
            
            <input type="email" id="email" name="email" required>
            <label for="email">Email:</label>
            <i class='envelope'></i>
            </div>
            <br><br>
            
            <div class="input-box">
            <input type="password" id="password" name="password" required>
            <label for="password"> Password:</label>
            <i class='bx bx-lock-alt'></i>
            </div>
            
            
            <div class="input-box">

            <input type="password" id="confirmPassword" name="confirmPassword" required>
            <label for="confirmPassword">Confirm Password:</label>
            <i class='bx bx-lock-alt'></i>
            </div>
            
            
            <div class="input-box">
            
            <input type="text" id="phoneNumber" name="phoneNumber" required>
            <label for="phoneNumber">Phone Number:</label>
            <i class='bx bx-phone'></i>
            </div>

            <div class="input-box">
            <select id="province" name="province" required>
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
            <label for="province">Province:</label>
            <i class='bx bx-map'></i>
            </div>
            

                <div class="input-box">
            <select id="district" name="district" required>
                <option value="">Select District</option>
                
           
            </select>
            <label for="district">District:</label>
            <i class='bx bx-map-pin'></i>
            </div>
            
            
            <div class="input-box">
            <select id="sector" name="sector" required>
           
                <option value="">Select Sector</option>
                 
            </select>
            <label for="sector">Sector:</label>
            <i class='bx bx-building-house'></i>
            </div>
            <br><br>
            
            <div class="input-box">
            
            <select id="cell" name="cell" required>
            
                <option value="">Select Cell</option>
            </select>
            <label for="cell">Cell:</label>
            <i class='bx bx-building-house'></i>
            </div>
           
            
            <div class="input-box">
            <select id="village" name="village" required>
            
                <option value="">Select Village</option>
            </select>
            <label for="village">Village:</label>
            <i class='bx bx-home'></i>
            </div>
           
            
            <div class="input-box">
            <select id="role" name="role" required>
                <option value="" disabled selected>Select your role</option>
                <option value="student">Student</option>
                <option value="teacher">Teacher</option>
                <option value="HOD">Head of Department</option>
                <option value="Dean">Dean</option>
                <option value="Registrar">Registrar</option>
                 <option value="Manager">Manager</option>
            </select>
            <label for="role">Role:</label>
            <i class='bx bx-user-pin'></i>
            </div>
           

              <!-- Terms and Privacy -->
                <div class="form-section terms">
                    <div class="checkbox-wrapper">
                        <input type="checkbox" id="terms" name="terms" required>
                        <label for="terms">I agree to the <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a></label>
                    </div>
                </div>

                <!-- Submit Button -->
                <input type="submit" value="Create Account" class="btn">

                <!-- Sign In Link -->
                <div class="reg-link">
                    <p>Already have an account? <a href="login.html">Sign In</a></p>
                </div>
            </form>
        </div>

        <!-- Info Section -->
        <div class="info-content">
            <h3>Welcome to Auca Library</h3>
            <p>Join our community and discover endless possibilities in learning and teaching.</p>
            <div class="features">
                <div class="feature-item">
                    <i class='bx bx-book-reader'></i>
                    <span>Access Quality Education</span>
                </div>
                <div class="feature-item">
                    <i class='bx bx-network-chart'></i>
                    <span>Connect with Experts</span>
                </div>
                <div class="feature-item">
                    <i class='bx bx-time-five'></i>
                    <span>Learn at Your Pace</span>
                </div>
                </div>
                </div>
    </div>
    
</body>
</html>