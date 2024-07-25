<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Login - Banking Application</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: url('imgs/home.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        .login-form {
            width: 100%;
            max-width: 400px;
            background: rgba(255, 255, 255, 0.85);
            padding: 40px 60px;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .login-form h1 {
            margin-top: 0;
            font-weight: bold;
            color: #333;
        }

        .login-form label {
            display: block;
            margin-bottom: 5px;
            text-align: left;
            color: #666;
        }

        .login-form input[type="text"], .login-form input[type="password"] {
            width: 100%;
            height: 40px;
            margin-bottom: 20px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box; /* Ensures padding and border are included in width */
        }

        .login-form input[type="submit"] {
            width: 100%;
            height: 40px;
            background-color: #4CAF50;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            box-sizing: border-box; /* Ensures padding and border are included in width */
            transition: background-color 0.3s ease;
        }

        .login-form input[type="submit"]:hover {
            background-color: #3e8e41;
        }

        .error {
            color: red;
            font-size: 12px;
            margin-top: -10px;
            margin-bottom: 10px;
            text-align: left;
        }
    </style>
</head>
<body>
    <div class="login-form">
        <h1>Customer Login</h1>
        <form action="LoginServlet" method="post">
            <label for="account_number">Account Number:</label>
            <input type="text" id="account_number" name="account_number" required/>
            <br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required/>
            <br>
            <input type="submit" value="Login"/>
            <% if (request.getAttribute("error") != null) { %>
                <p class="error"><%= request.getAttribute("error") %></p>
            <% } %>
        </form>
    </div>
</body>
</html>
