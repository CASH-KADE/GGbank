<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Customer</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('imgs/Admin.jpg'); /* Background image */
            background-size: cover; /* Cover the entire viewport */
            background-position: center; /* Center the background image */
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: rgba(255, 255, 255, 0.9); /* White with slight transparency */
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 400px;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        input[type="text"], input[type="submit"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #f44336;
            color: white;
            cursor: pointer;
            margin-top: 20px;
        }
        input[type="submit"]:hover {
            background-color: #e31e10;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .message {
            text-align: center;
            color: red;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Delete Customer</h1>
        <%
            String username = request.getParameter("username");
            if (username == null || username.isEmpty()) {
        %>
        <!-- Form to get username -->
        <form action="deletecustomer.jsp" method="post">
            <div class="form-group">
                <label for="username">Enter Username:</label>
                <input type="text" id="username" name="username" required/>
            </div>
            <input type="submit" value="Next"/>
        </form>
        <%
            } else {
        %>
        <!-- Form to confirm deletion -->
        <form action="DeleteCustomerServlet" method="post">
            <input type="hidden" name="username" value="<%= username %>"/>
            <div class="form-group">
                <label>Are you sure you want to delete the customer with username: <%= username %>?</label>
            </div>
            <input type="submit" value="Delete"/>
        </form>
        <%
            }
        %>
    </div>
</body>
</html>
