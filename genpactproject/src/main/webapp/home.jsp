<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to Banking Application</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: url('imgs/home.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        .welcome-container {
            text-align: center;
            background: rgba(255, 255, 255, 0.85);
            padding: 50px;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
        }

        .welcome-container h1 {
            margin: 0;
            color: #333;
            font-weight: 700;
            font-size: 36px;
            letter-spacing: 2px;
        }

        .bank-name {
            font-size: 68px;
            font-weight: 700;
            margin-bottom: 20px;
            color: #333;
        }

        .button {
            display: inline-block;
            margin: 15px 10px;
            padding: 15px 40px;
            font-size: 18px;
            color: white;
            background-color: #4CAF50;
            border: none;
            border-radius: 50px;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .button:hover {
            background-color: #45a049;
            transform: scale(1.05);
        }
    </style>
</head>
<body>
    <div class="welcome-container">
        <div class="bank-name">GG BANK</div>
        <h1>WELCOME TO BANKING WEBSITE!</h1>
        <div>
            <a class="button" href="adminlogin.jsp">Admin Login</a>
            <a class="button" href="login.jsp">Customer Login</a>
            <a class="button" href="help.jsp">Learn More</a>
        </div>
    </div>
</body>
</html>
