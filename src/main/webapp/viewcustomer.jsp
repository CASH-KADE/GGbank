<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Customer - Banking Application</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-image: url('imgs/Admin.jpg'); /* Background image */
            background-size: cover; /* Cover the entire viewport */
            background-position: center; /* Center the background image */
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .form-container {
            background: rgba(255, 255, 255, 0.9); /* White background with slight transparency */
            padding: 40px 60px;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 500px;
            width: 100%;
        }

        .form-container h2 {
            margin-bottom: 20px;
            color: #333;
            font-weight: 700;
            font-size: 28px;
            letter-spacing: 1px;
        }

        .form-container label {
            display: block;
            margin: 10px 0 5px;
            font-size: 16px;
            color: #666;
        }

        .form-container input {
            width: calc(100% - 22px); /* Full width minus padding and border */
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box; /* Include padding and border in element's total width and height */
        }

        .button {
            width: 100%;
            padding: 15px;
            font-size: 18px;
            color: white;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .button:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>View Customer</h2>
        <form action="viewcustomerservlet" method="post">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
            <button type="submit" class="button">View Customer</button>
        </form>
    </div>
</body>
</html>
