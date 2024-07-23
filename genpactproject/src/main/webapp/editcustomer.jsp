<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Customer Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('imgs/Admin.jpg') no-repeat center center fixed; /* Add your background image path here */
            background-size: cover;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: rgba(255, 255, 255, 0.9); /* White background with slight transparency */
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
        input[type="text"], input[type="date"], select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            background-color: #007BFF; /* Blue color */
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 20px;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3; /* Darker blue on hover */
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
        <h1>Edit Customer Details</h1>
        <%
            String username = request.getParameter("username");
            if (username == null || username.isEmpty()) {
        %>
        <!-- Form to get username -->
        <form action="editcustomer.jsp" method="post">
            <div class="form-group">
                <label for="username">Enter Username:</label>
                <input type="text" id="username" name="username" required/>
            </div>
            <input type="submit" value="Next"/>
        </form>
        <%
            } else {
        %>
        <!-- Form to select detail and new value -->
        <form action="EditCustomerServlet" method="post">
            <input type="hidden" name="username" value="<%= username %>"/>
            <div class="form-group">
                <label for="detail">Select Detail to Change:</label>
                <select id="detail" name="detail">
                    <option value="fullname">Full Name</option>
                    <option value="address">Address</option>
                    <option value="mobile_number">Mobile Number</option>
                    <option value="account_type">Account Type</option>
                    <option value="balance">Balance</option>
                    <option value="date_of_birth">Date of Birth</option>
                    <option value="id_proof">ID Proof</option>
                </select>
            </div>
            <div class="form-group">
                <label for="newValue">Enter New Value:</label>
                <input type="text" id="newValue" name="newValue" required/>
            </div>
            <input type="submit" value="Update"/>
        </form>
        <%
            }
        %>
    </div>
</body>
</html>
