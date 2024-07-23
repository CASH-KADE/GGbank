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
