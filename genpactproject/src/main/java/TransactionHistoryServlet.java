import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/TransactionHistoryServlet")
public class TransactionHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String fullname = (String) session.getAttribute("fullname");

        if (fullname == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String order = request.getParameter("order");
        if (order == null || (!order.equals("asc") && !order.equals("desc"))) {
            order = "desc";
        }

        String jdbcURL = "jdbc:mysql://localhost:3306/banking";
        String jdbcUsername = "root";
        String jdbcPassword = "asdefg123";

        String sql = "SELECT transaction_id, transaction_date, amount, transaction_type FROM transactions WHERE fullname = ? ORDER BY transaction_date " + order;

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fullname);
            ResultSet resultSet = statement.executeQuery();
            request.setAttribute("transactionHistory", resultSet);
            request.setAttribute("order", order);
            request.getRequestDispatcher("transactionHistory.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
