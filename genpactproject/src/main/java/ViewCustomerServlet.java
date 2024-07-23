import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewcustomerservlet")
public class ViewCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/banking";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "asdefg123";

    // SQL query to fetch customer details
    private static final String FETCH_CUSTOMER_SQL = "SELECT * FROM user WHERE username = ?";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(FETCH_CUSTOMER_SQL)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            out.println("<html><body>");
            if (resultSet.next()) {
                // Fetch customer details
                String fullname = resultSet.getString("fullname");
                String address = resultSet.getString("address");
                String mobile = resultSet.getString("mobile_number");
                String accountType = resultSet.getString("account_type");
                String dob = resultSet.getString("date_of_birth");
                String idProof = resultSet.getString("id_proof");

                // Set attributes to forward to JSP
                request.setAttribute("fullname", fullname);
                request.setAttribute("address", address);
                request.setAttribute("mobile", mobile);
                request.setAttribute("accountType", accountType);
                request.setAttribute("dob", dob);
                request.setAttribute("idProof", idProof);

                // Forward to JSP
                request.getRequestDispatcher("viewcustomerresult.jsp").forward(request, response);
            } else {
                out.println("<script type='text/javascript'>");
                out.println("alert('No customer found with username: " + username + "');");
                out.println("setTimeout(function(){ window.location.href = 'admindashboard.jsp'; }, 500);");
                out.println("</script>");
            }
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<script type='text/javascript'>");
            out.println("alert('Error connecting to the database: " + e.getMessage() + "');");
            out.println("setTimeout(function(){ window.location.href = 'admindashboard.jsp'; }, 500);");
            out.println("</script>");
            out.println("</body></html>");
        }
    }
}
