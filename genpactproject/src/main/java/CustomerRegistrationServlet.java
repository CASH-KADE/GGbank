import DAO.sqlDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CustomerRegistrationServlet")
public class CustomerRegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // SecureRandom for generating random account numbers and passwords
    private static final SecureRandom random = new SecureRandom();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetching form parameters
        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String mobileNumber = request.getParameter("mobileNumber");
        String accountType = request.getParameter("accountType");
        double initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
        String dateOfBirth = request.getParameter("dateOfBirth");
        String idProof = request.getParameter("idProof");

        // Generate random account number and password
        String accountNumber = generateRandomNumber(10);
        String password = generateRandomString(8);

        try (Connection conn = sqlDAO.getConnection()) {
            // SQL query to insert data
            String sql = "INSERT INTO user (username, password, fullname, balance, address, mobile_number, account_type, date_of_birth, id_proof, account_number) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Create prepared statement
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, fullName);
            stmt.setDouble(4, initialBalance);
            stmt.setString(5, address);
            stmt.setString(6, mobileNumber);
            stmt.setString(7, accountType);
            stmt.setString(8, dateOfBirth);
            stmt.setString(9, idProof);
            stmt.setString(10, accountNumber);

            // Execute the statement
            int rowsAffected = stmt.executeUpdate();

            // Check if the registration was successful
            if (rowsAffected > 0) {
                // Set the registration details as request attributes
                request.setAttribute("username", username);
                request.setAttribute("fullName", fullName);
                request.setAttribute("address", address);
                request.setAttribute("mobileNumber", mobileNumber);
                request.setAttribute("accountType", accountType);
                request.setAttribute("initialBalance", initialBalance);
                request.setAttribute("dateOfBirth", dateOfBirth);
                request.setAttribute("idProof", idProof);
                request.setAttribute("accountNumber", accountNumber);
                request.setAttribute("password", password);

                // Forward to the JSP page to display registration details
                RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrationDetails.jsp");
                dispatcher.forward(request, response);
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body><h3>Registration failed.</h3></body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body><h3>Database error: " + e.getMessage() + "</h3></body></html>");
        }
    }

    // Method to generate a random string of specified length
    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    // Method to generate a random number of specified length
    private String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
