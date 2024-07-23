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

@WebServlet("/EditCustomerServlet")
public class EditCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String detail = request.getParameter("detail");
        String newValue = request.getParameter("newValue");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // SQL queries
        String getCurrentFullnameSQL = "SELECT fullname FROM user WHERE username = ?";
        String updateUserSQL = "UPDATE user SET " + detail + " = ? WHERE username = ?";
        String updateTransactionsSQL = "UPDATE transactions SET fullname = ? WHERE fullname = ?";

        try (Connection connection = sqlDAO.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Fetch current fullname before updating
            String oldFullname = "";
            try (PreparedStatement getFullnameStatement = connection.prepareStatement(getCurrentFullnameSQL)) {
                getFullnameStatement.setString(1, username);
                try (ResultSet resultSet = getFullnameStatement.executeQuery()) {
                    if (resultSet.next()) {
                        oldFullname = resultSet.getString("fullname");
                    } else {
                        out.println("<html><body><p>No customer found with username: " + username + "</p></body></html>");
                        return;
                    }
                }
            }

            // Update user table
            try (PreparedStatement userStatement = connection.prepareStatement(updateUserSQL)) {
                userStatement.setString(1, newValue);
                userStatement.setString(2, username);
                int userRowsAffected = userStatement.executeUpdate();

                // Update transactions table if fullname was updated
                if ("fullname".equalsIgnoreCase(detail)) {
                    try (PreparedStatement transactionsStatement = connection.prepareStatement(updateTransactionsSQL)) {
                        transactionsStatement.setString(1, newValue);
                        transactionsStatement.setString(2, oldFullname);
                        int transactionsRowsAffected = transactionsStatement.executeUpdate();
                        System.out.println("Updated fullname in transactions table. Rows affected: " + transactionsRowsAffected); // Debugging statement
                    }
                }

                connection.commit(); // Commit transaction

                out.println("<html><body>");
                if (userRowsAffected > 0) {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Customer details updated successfully!');");
                    out.println("setTimeout(function(){ window.location.href = 'admindashboard.jsp'; }, 500);");
                    out.println("</script>");
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Error updating customer details. No such user found.');");
                    out.println("setTimeout(function(){ window.location.href = 'admindashboard.jsp'; }, 500);");
                    out.println("</script>");
                }
                out.println("</body></html>");

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
