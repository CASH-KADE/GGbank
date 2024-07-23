<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('imgs/Admin.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        .registration-form {
            width: 400px;
            background-color: rgba(255, 255, 255, 0.9); /* White with slight transparency */
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .registration-form h2 {
            margin-top: 0;
            font-weight: bold;
            color: #333;
            text-align: center;
        }

        .registration-form label {
            display: block;
            margin-bottom: 10px;
        }

        .registration-form input[type="text"],
        .registration-form input[type="email"],
        .registration-form input[type="number"],
        .registration-form input[type="password"],
        .registration-form select {
            width: calc(100% - 22px);
            height: 30px;
            margin-bottom: 10px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .registration-form input[type="submit"] {
            width: 100%;
            height: 40px;
            background-color: #007BFF; /* Blue color */
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .registration-form input[type="submit"]:hover {
            background-color: #0056b3; /* Darker blue */
        }

        .error {
            color: red;
            font-size: 12px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="registration-form">
        <h2>Customer Registration</h2>
        <form action="CustomerRegistrationServlet" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            
            <label for="fullName">Full Name:</label>
            <input type="text" id="fullName" name="fullName" required>
            
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" required>
            
            <label for="mobileNumber">Mobile Number:</label>
            <input type="text" id="mobileNumber" name="mobileNumber" required>
            
            <label for="accountType">Type of Account:</label>
            <select id="accountType" name="accountType" required>
                <option value="savings">Savings Account</option>
                <option value="current">Current Account</option>
            </select>
            
            <label for="initialBalance">Initial Balance (Minimum 1000):</label>
            <input type="number" id="initialBalance" name="initialBalance" min="1000" step="any" required>
            
            <label for="dateOfBirth">Date of Birth:</label>
            <input type="date" id="dateOfBirth" name="dateOfBirth" required>
            
            <label for="idProof">ID Proof (Aadhaar or PAN Card):</label>
            <select id="idProof" name="idProof" required>
                <option value="aadhaar">Aadhaar</option>
                <option value="pancard">PAN Card</option>
            </select>
            
            <input type="submit" value="Register">
        </form>
    </div>
</body>
</html>
