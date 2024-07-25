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
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String account_number = request.getParameter("account_number");
        String password = request.getParameter("password");

        try (Connection conn = sqlDAO.getConnection()) {
            String sql = "SELECT fullname, balance FROM user WHERE account_number = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, account_number);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            out.println("<html><body>");
            if (resultSet.next()) {
                String fullName = resultSet.getString("fullname");
                double balance = resultSet.getDouble("balance");

                // Store user details in session
                HttpSession session = request.getSession();
                session.setAttribute("account_number", account_number);
                session.setAttribute("fullname", fullName);
                session.setAttribute("balance", balance);

                // Add JavaScript to display success message and redirect
                out.println("<script type='text/javascript'>");
                out.println("setTimeout(function(){ window.location.href = 'dashboard.jsp'; }, 500);");
                out.println("</script>");
            } else {
                out.println("<script type='text/javascript'>");
                out.println("alert('Invalid account_number or password. Please try again.');");
                out.println("setTimeout(function(){ window.location.href = 'login.jsp'; }, 500);");
                out.println("</script>");
            }
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}
