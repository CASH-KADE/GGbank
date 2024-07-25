<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help - Banking Application</title>
    <link rel="stylesheet" href="styles.css"> <!-- Link to your CSS file -->
    <style>
        body {
            font-family: Arial, sans-serif;
            color: #333;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }
        header {
            background: #333;
            color: #fff;
            padding: 10px 0;
            text-align: center;
        }
        header h1 {
            margin: 0;
        }
        .main-content {
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }
        .main-content h2 {
            color: #007bff;
            margin-top: 0;
        }
        .list-group {
            list-style: none;
            padding: 0;
        }
        .list-group li {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        .list-group li:last-child {
            border-bottom: none;
        }
        footer {
            background: #333;
            color: #fff;
            text-align: center;
            padding: 10px 0;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <header>
        <h1>Banking Application Help</h1>
    </header>

    <div class="container">
        <div class="main-content">
            <h2>Customer Roles & Features</h2>
            <ul class="list-group">
                <li><strong>Customer Registration:</strong> Admin handles customer registration. The generated account number and password will be provided to the customer by the admin.</li>
                <li><strong>Change Password:</strong> Use the account number and provided password to set up a new password.</li>
                <li><strong>Login:</strong> After setting the new password, log in using the account number and new password.</li>
                <li><strong>Logout:</strong> Customers can log out from the application at any time.</li>
                <li><strong>Customer Dashboard:</strong> After logging in, the customer will see the dashboard displaying the account details and balance.</li>
                <li><strong>View Transactions:</strong> Click on "View" to see the last 10 transactions sorted by date (increasing or decreasing order).</li>
                <li><strong>Deposit:</strong> Click on "Deposit" to open a dialogue box. Enter the amount and click "Submit" to increase the balance and save the transaction.</li>
                <li><strong>Withdraw:</strong> Click on "Withdraw" to open a dialogue box. Enter the amount and click "Submit" to decrease the balance and save the transaction.</li>
                <li><strong>Account Balance:</strong> Customers can maintain a balance of 0 but not below that.</li>
                <li><strong>Close Account:</strong> Customers can close their account without admin intervention, but only after withdrawing all the money.</li>
            </ul>

            <h2>Bonus Functionality</h2>
            <ul class="list-group">
                <li><strong>Download Transactions:</strong> Click "Print" in the mini statement to download a PDF of the last 10 transactions.</li>
            </ul>
        </div>
    </div>

    <footer>
        &copy; 2024 Banking Application. All rights reserved.
    </footer>
</body>
</html>
