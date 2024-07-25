<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Registration Details</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: url('imgs/Admin.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        .result-container {
            background: rgba(255, 255, 255, 0.9); /* Semi-transparent background */
            padding: 40px 60px;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .result-container h2 {
            margin-bottom: 20px;
            color: #333;
            font-weight: 700;
            font-size: 28px;
            letter-spacing: 1px;
        }

        .result-container table {
            width: 100%;
            border-collapse: collapse;
        }

        .result-container table, .result-container th, .result-container td {
            border: 1px solid #ddd;
            padding: 8px;
        }

        .result-container th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        .result-container td {
            text-align: left;
        }

        .result-container .button-container {
            margin-top: 20px;
        }

        .result-container button {
            background-color: #007BFF; /* Blue color */
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .result-container button:hover {
            background-color: #0056b3; /* Darker blue */
        }
    </style>
</head>
<body>
    <div class="result-container">
        <h2>Customer Registration Details</h2>
        <table>
            <tr>
                <th>Field</th>
                <th>Details</th>
            </tr>
            <tr><td>Username:</td><td>${username}</td></tr>
            <tr><td>Full Name:</td><td>${fullName}</td></tr>
            <tr><td>Address:</td><td>${address}</td></tr>
            <tr><td>Mobile Number:</td><td>${mobileNumber}</td></tr>
            <tr><td>Account Type:</td><td>${accountType}</td></tr>
            <tr><td>Initial Balance:</td><td>${initialBalance}</td></tr>
            <tr><td>Date of Birth:</td><td>${dateOfBirth}</td></tr>
            <tr><td>ID Proof:</td><td>${idProof}</td></tr>
            <tr><td>Account Number:</td><td>${accountNumber}</td></tr>
            <tr><td>Password:</td><td>${password}</td></tr>
        </table>
        <div class="button-container">
            <button onclick="window.location.href='admindashboard.jsp'">Back to Dashboard</button>
        </div>
    </div>
</body>
</html>
