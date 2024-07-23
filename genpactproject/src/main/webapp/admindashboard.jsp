<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Banking Application</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-image: url('imgs/Admin.jpg'); /* Background image */
            background-size: cover; /* Cover the entire viewport */
            background-position: center; /* Center the background image */
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .dashboard-container {
            background-color: rgba(255, 255, 255, 0.9); /* White background with slight transparency */
            padding: 50px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 600px;
            width: 100%;
        }

        .dashboard-container h1 {
            font-family: 'Open Sans', sans-serif;
            color: #333333;
            margin-bottom: 30px;
            font-weight: 700;
        }

        .dashboard-container .button {
            display: inline-block;
            margin: 15px;
            padding: 15px 30px;
            font-size: 18px;
            color: white;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .dashboard-container .button:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }
        
        .logout-button {
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #f44336; /* Red */
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .logout-button:hover {
            background-color: #d32f2f;
            transform: scale(1.05);
        }
    </style>
</head>
<body>
    <form method="post">
        <button type="submit" name="logout" class="logout-button">Logout</button>
    </form>

    <%
        if (request.getParameter("logout") != null) {
            session.invalidate();
            response.sendRedirect("home.jsp");
        }
    %>

    <div class="dashboard-container">
        <h1>Admin Dashboard</h1>
        <a class="button" href="viewcustomer.jsp">View Customer</a>
        <a class="button" href="customerregistration.jsp">Register Customer</a>
        <a class="button" href="editcustomer.jsp">Edit Customer</a>
        <a class="button" href="deletecustomer.jsp">Delete Customer</a>
    </div>
</body>
<script>
    // Function to send a request to the servlet on page load
    function callAdminDashboardServlet() {
        fetch('AdminDashboardServlet', {
            method: 'POST'
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                response.text().then(data => {
                    document.getElementById("response").innerHTML = data;
                });
            }
        })
        .catch(error => console.error('Error:', error));
    }

    // Call the function on page load
    window.onload = callAdminDashboardServlet;
</script>
</html>
