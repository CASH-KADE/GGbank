<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Timestamp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            overflow: auto;
        }

        .container {
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            padding: 20px;
            width: 80%;
            max-width: 1000px;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        form {
            margin-bottom: 20px;
            display: flex;
            justify-content: center;
        }

        label {
            font-weight: bold;
            margin-right: 10px;
        }

        select, button {
            padding: 10px;
            margin: 0 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            font-size: 16px;
        }

        button {
            background-color: #007BFF;
            color: #fff;
            cursor: pointer;
            border: none;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            max-height: 400px; /* Adjust height as needed */
            overflow-y: auto;
            display: block;
        }

        thead, tbody {
            display: table;
            width: 100%;
            table-layout: fixed;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #007BFF;
            color: #fff;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #e9e9e9;
        }

        .no-transactions {
            text-align: center;
            color: #666;
            font-style: italic;
        }

        .credit {
            color: green;
        }

        .debit {
            color: red;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Transaction History</h1>

        <form action="TransactionHistoryServlet" method="post">
            <label for="order">Sort by Date:</label>
            <select name="order" id="order">
                <option value="asc" <%= "asc".equals(request.getAttribute("order")) ? "selected" : "" %>>Ascending</option>
                <option value="desc" <%= "desc".equals(request.getAttribute("order")) ? "selected" : "" %>>Descending</option>
            </select>
            <button type="submit">Sort</button>
        </form>

        <form action="DownloadPDFServlet" method="post">
            <button type="submit">Download Last 10 Transactions as PDF</button>
        </form>

        <table>
            <thead>
                <tr>
                    <th>Transaction ID</th>
                    <th>Date</th>
                    <th>Amount</th>
                    <th>Type</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ResultSet rs = (ResultSet) request.getAttribute("transactionHistory");
                    if (rs != null) {
                        try {
                            while (rs.next()) {
                                int transactionId = rs.getInt("transaction_id");
                                Timestamp transactionDate = rs.getTimestamp("transaction_date");
                                double amount = rs.getDouble("amount");
                                String type = rs.getString("transaction_type");

                             // Determine display type and class based on transaction type
                                String displayType;
                                String typeClass;

                                if (type.contains("Transfer")) {
                                    // Handle transfers
                                    if (type.contains("from")) {
                                    	displayType = type;
                                        typeClass = "credit"; // Money coming in is considered credit
                                    } else {
                                        displayType = type;
                                        typeClass = "debit"; // Money going out is considered debit
                                    }
                                } else {
                                    // Handle regular credit and debit
                                    displayType = "credit".equals(type) ? "Credited" : "Debited";
                                    typeClass = "credit".equals(type) ? "credit" : "debit";
                                }
                %>
                <tr>
                    <td><%= transactionId %></td>
                    <td><%= transactionDate %></td>
                    <td><%= amount %></td>
                    <td class="<%= typeClass %>"><%= displayType %></td>
                </tr>
                <%
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            out.println("<tr><td colspan='4' class='no-transactions'>Error retrieving transaction data.</td></tr>");
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="no-transactions">No transactions found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <button onclick="window.location.href='dashboard.jsp'">Back to Dashboard</button>
    </div>
</body>
</html>
