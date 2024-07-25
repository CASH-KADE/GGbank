import DAO.sqlDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // SQL queries
    private static final String GET_FULLNAME_SQL = "SELECT fullname FROM user WHERE username = ?";
    private static final String DELETE_FROM_TRANSACTIONS_SQL = "DELETE FROM transactions WHERE fullname = ?";
    private static final String DELETE_CUSTOMER_SQL = "DELETE FROM user WHERE username = ?";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection connection = sqlDAO.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Fetch current fullname
            String fullname = null;
            try (PreparedStatement getFullnameStatement = connection.prepareStatement(GET_FULLNAME_SQL)) {
                getFullnameStatement.setString(1, username);
                try (ResultSet resultSet = getFullnameStatement.executeQuery()) {
                    if (resultSet.next()) {
                        fullname = resultSet.getString("fullname");
                    } else {
                        out.println("<html><body><p>No customer found with username: " + username + "</p></body></html>");
                        return;
                    }
                }
            }

            // Delete records from transactions table
            try (PreparedStatement deleteTransactionsStatement = connection.prepareStatement(DELETE_FROM_TRANSACTIONS_SQL)) {
                deleteTransactionsStatement.setString(1, fullname);
                deleteTransactionsStatement.executeUpdate();
            }

            // Delete customer from user table
            try (PreparedStatement deleteCustomerStatement = connection.prepareStatement(DELETE_CUSTOMER_SQL)) {
                deleteCustomerStatement.setString(1, username);
                int rowsAffected = deleteCustomerStatement.executeUpdate();

                connection.commit(); // Commit transaction

                if (rowsAffected > 0) {
                    out.println("<html><body>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Customer deleted successfully!');");
                    out.println("setTimeout(function(){ window.location.href = 'admindashboard.jsp'; }, 1000);");
                    out.println("</script>");
                    out.println("</body></html>");
                } else {
                    out.println("<html><body>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Error deleting customer. No such user found.');");
                    out.println("setTimeout(function(){ window.location.href = 'admindashboard.jsp'; }, 1000);");
                    out.println("</script>");
                    out.println("</body></html>");
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction on error
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body><p>Error connecting to the database: " + e.getMessage() + "</p></body></html>");
        }
    }
}
