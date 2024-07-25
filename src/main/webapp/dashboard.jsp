<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: url('imgs/Customer.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            padding: 20px;
        }

        .container {
            width: 100%;
            max-width: 600px;
            text-align: center;
            background-color: rgba(255, 255, 255, 0.85);
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #333;
            margin-bottom: 10px;
        }
        
        h2 {
            color: #333;
            font-size: 18px;
            margin-bottom: 10px;
        }

        p {
            color: #555;
            font-size: 18px;
            margin: 5px 0;
        }

        .section {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .section h2 {
            margin-top: 0;
            color: #333;
        }

        form {
            margin: 10px 0;
        }

        input[type="text"], input[type="password"], input[type="number"] {
            width: calc(100% - 22px); /* Adjust width for padding and border */
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box; /* Include padding and border in element's total width and height */
        }

        button {
            width: 100%;
            height: 40px;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .add-money-button {
            background-color: #4CAF50;
        }

        .add-money-button:hover {
            background-color: #45a049;
        }

        .withdraw-money-button {
            background-color: #f44336;
        }

        .withdraw-money-button:hover {
            background-color: #e53935;
        }

        .view-history-button {
            background-color: #2196F3; /* Blue color */
            margin-bottom: 10px; /* Space between this and the next button */
        }

        .view-history-button:hover {
            background-color: #0b79d0; /* Darker blue for hover */
        }

        .change-password-button {
            background-color: #9c27b0; /* Light purple color */
        }

        .change-password-button:hover {
            background-color: #88209a; /* Darker purple for hover */
        }

        .transfer-money-button {
            background-color: #FF9800; /* Orange color */
        }

        .transfer-money-button:hover {
            background-color: #f57c00; /* Darker orange for hover */
        }

        .back-to-login {
            background-color: #3d3d3d; /* Pale black color */
            margin-top: 20px; /* Space from other elements */
        }

        .back-to-login:hover {
            background-color: #2a2a2a; /* Darker pale black for hover */
        }

        .collapse {
            cursor: pointer;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
            text-align: center;
            font-size: 16px;
            margin-top: 20px;
        }

        .collapse:hover {
            background-color: #e0e0e0;
        }

        .collapse-content {
            display: none;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            margin-top: 10px;
        }

        .collapse-content input[type="password"] {
            margin-bottom: 10px;
        }
    </style>
    <script>
        function toggleCollapse() {
            var content = document.getElementById("changePinContent");
            var display = content.style.display;

            if (display === "none" || display === "") {
                content.style.display = "block";
            } else {
                content.style.display = "none";
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Welcome, <%= session.getAttribute("fullname") %></h1>
        <h2>Account No: <%= session.getAttribute("account_number") %></h2>
        <p>Current Balance: <%= session.getAttribute("balance") %></p>

        <div class="section">
            <h2>Account Transactions</h2>
            <form action="DashboardServlet" method="post">
                <input type="hidden" name="action" value="add">
                Amount to Add: <input type="text" name="amount" required>
                <button type="submit" class="add-money-button">Add Money</button>
            </form>

            <form action="DashboardServlet" method="post">
                <input type="hidden" name="action" value="withdraw">
                Amount to Withdraw: <input type="text" name="amount" required>
                <button type="submit" class="withdraw-money-button">Withdraw Money</button>
            </form>
			
			 <!-- Money Transfer Section -->
            <form action="DashboardServlet" method="post">
		        <input type="hidden" name="action" value="transfer">
		        Amount to Transfer: <input type="text" name="amount" required>
		        Recipient Account Number: <input type="text" name="recipient_account_number" required>
		        <button type="submit" class="transfer-money-button">Transfer Money</button>
    		</form>
        
            <form action="TransactionHistoryServlet" method="post">
                <button type="submit" class="view-history-button">View Transaction History</button>
            </form>

        <div class="collapse" onclick="toggleCollapse()">Change Password</div>
        <div id="changePinContent" class="collapse-content">
            <form action="ChangePinServlet" method="post">
                <input type="password" name="oldPin" placeholder="Current Password" required>
                <input type="password" name="newPin" placeholder="New Password" required>
                <input type="password" name="confirmNewPin" placeholder="Confirm New Password" required>
                <button type="submit" class="change-password-button">Change PIN</button>
            </form>
        </div>

        <button class="back-to-login" onclick="window.location.href='home.jsp'">Log out</button>
    </div>
    </div>
</body>
</html>
