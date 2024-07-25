import DAO.sqlDAO;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String fullname = (String) session.getAttribute("fullname");

        if (fullname == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection connection = sqlDAO.getConnection()) {
            double amount = Double.parseDouble(request.getParameter("amount"));
            String transactionType = "";
            String updateSQL = "";
            String transactionSQL = "INSERT INTO transactions (fullname, amount, transaction_type) VALUES (?, ?, ?)";

            if ("add".equals(action)) {
                updateSQL = "UPDATE user SET balance = balance + ? WHERE fullname = ?";
                transactionType = "credit";
            } else if ("withdraw".equals(action)) {
                updateSQL = "UPDATE user SET balance = balance - ? WHERE fullname = ? AND balance >= ?";
                transactionType = "debit";
            } else if ("transfer".equals(action)) {
                String recipientAccountNumber = request.getParameter("recipient_account_number");

                if (amount <= (double) session.getAttribute("balance")) {
                    try {
                        connection.setAutoCommit(false);

                        // Check if the recipient exists and get their full name
                        String sqlCheckRecipient = "SELECT fullname FROM user WHERE account_number = ?";
                        try (PreparedStatement statementCheckRecipient = connection.prepareStatement(sqlCheckRecipient)) {
                            statementCheckRecipient.setString(1, recipientAccountNumber);
                            ResultSet resultSet = statementCheckRecipient.executeQuery();

                            if (resultSet.next()) {
                                String recipientFullName = resultSet.getString("fullname");

                                // Update sender's balance
                                String sqlUpdateSender = "UPDATE user SET balance = balance - ? WHERE fullname = ?";
                                try (PreparedStatement statementUpdateSender = connection.prepareStatement(sqlUpdateSender)) {
                                    statementUpdateSender.setDouble(1, amount);
                                    statementUpdateSender.setString(2, fullname);
                                    int rowsUpdatedSender = statementUpdateSender.executeUpdate();
                                    if (rowsUpdatedSender <= 0) {
                                        connection.rollback();
                                        throw new SQLException("Failed to update sender's balance");
                                    }
                                }

                                // Update recipient's balance
                                String sqlUpdateRecipient = "UPDATE user SET balance = balance + ? WHERE account_number = ?";
                                try (PreparedStatement statementUpdateRecipient = connection.prepareStatement(sqlUpdateRecipient)) {
                                    statementUpdateRecipient.setDouble(1, amount);
                                    statementUpdateRecipient.setString(2, recipientAccountNumber);
                                    int rowsUpdatedRecipient = statementUpdateRecipient.executeUpdate();
                                    if (rowsUpdatedRecipient <= 0) {
                                        connection.rollback();
                                        throw new SQLException("Failed to update recipient's balance");
                                    }
                                }

                                connection.commit();
                                session.setAttribute("balance", (double) session.getAttribute("balance") - amount);

                                // Log transactions for both sender and recipient
                                try (PreparedStatement transactionStatement = connection.prepareStatement(transactionSQL)) {
                                    transactionStatement.setString(1, fullname);
                                    transactionStatement.setDouble(2, amount);
                                    transactionStatement.setString(3, "Transfer to " + recipientFullName);
                                    transactionStatement.executeUpdate();

                                    transactionStatement.setString(1, recipientFullName);
                                    transactionStatement.setDouble(2, amount);
                                    transactionStatement.setString(3, "Transfer from " + fullname);
                                    transactionStatement.executeUpdate();
                                }

                                response.sendRedirect("dashboardDetails.jsp");
                            } else {
                                connection.rollback();
                                session.setAttribute("error", "Recipient account not found");
                                response.sendRedirect("dashboardDetails.jsp");
                            }
                        }
                    } catch (SQLException e) {
                        connection.rollback();
                        e.printStackTrace();
                        session.setAttribute("error", "Error processing transfer");
                        response.sendRedirect("dashboardDetails.jsp");
                    } finally {
                        connection.setAutoCommit(true);
                    }
                } else {
                    session.setAttribute("error", "Insufficient funds");
                    response.sendRedirect("dashboardDetails.jsp");
                }
                return;
            } else {
                response.sendRedirect("dashboardDetails.jsp");
                return;
            }

            try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                if ("withdraw".equals(action)) {
                    updateStatement.setDouble(1, amount);
                    updateStatement.setString(2, fullname);
                    updateStatement.setDouble(3, amount);
                } else {
                    updateStatement.setDouble(1, amount);
                    updateStatement.setString(2, fullname);
                }

                int rowsUpdated = updateStatement.executeUpdate();
                System.out.println("Rows updated: " + rowsUpdated); // Debug logging

                if (rowsUpdated > 0) {
                    // Update session with new balance
                    double newBalance = (double) session.getAttribute("balance");
                    newBalance = "add".equals(action) ? newBalance + amount : newBalance - amount;
                    session.setAttribute("balance", newBalance);

                    // Insert transaction record
                    try (PreparedStatement transactionStatement = connection.prepareStatement(transactionSQL)) {
                        transactionStatement.setString(1, fullname);
                        transactionStatement.setDouble(2, amount);
                        transactionStatement.setString(3, transactionType);
                        int rowsInserted = transactionStatement.executeUpdate();
                        System.out.println("Rows inserted into transactions: " + rowsInserted); // Debug logging

                        response.sendRedirect("dashboardDetails.jsp");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
