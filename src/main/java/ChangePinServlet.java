import DAO.sqlDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChangePinServlet")
public class ChangePinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // SQL queries
    private static final String VALIDATE_PIN_SQL = "SELECT * FROM user WHERE username = ? AND password = ?";
    private static final String UPDATE_PIN_SQL = "UPDATE user SET password = ? WHERE username = ?";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String oldPin = request.getParameter("oldPin");
        String newPin = request.getParameter("newPin");
        String confirmNewPin = request.getParameter("confirmNewPin");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (newPin.equals(confirmNewPin)) {
            try (Connection connection = sqlDAO.getConnection()) {
                // Validate the old PIN
                boolean isOldPinValid = validateOldPin(connection, username, oldPin);

                if (isOldPinValid) {
                    // Update the PIN
                    boolean isUpdated = updatePinInDatabase(connection, username, newPin);

                    if (isUpdated) {
                        out.println("<html><body>");
                        out.println("<script type='text/javascript'>");
                        out.println("alert('PIN changed successfully!');");
                        out.println("window.location.href = 'dashboard.jsp';");
                        out.println("</script>");
                        out.println("</body></html>");
                    } else {
                        out.println("<html><body>");
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Error updating PIN. Please try again.');");
                        out.println("window.location.href = 'dashboard.jsp';");
                        out.println("</script>");
                        out.println("</body></html>");
                    }
                } else {
                    out.println("<html><body>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Invalid old PIN. Please try again.');");
                    out.println("window.location.href = 'dashboard.jsp';");
                    out.println("</script>");
                    out.println("</body></html>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<html><body>");
                out.println("<script type='text/javascript'>");
                out.println("alert('Database error. Please try again later.');");
                out.println("window.location.href = 'dashboard.jsp';");
                out.println("</script>");
                out.println("</body></html>");
            }
        } else {
            out.println("<html><body>");
            out.println("<script type='text/javascript'>");
            out.println("alert('New PINs do not match. Please try again.');");
            out.println("window.location.href = 'dashboard.jsp';");
            out.println("</script>");
            out.println("</body></html>");
        }
    }

    private boolean validateOldPin(Connection connection, String username, String oldPin) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(VALIDATE_PIN_SQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, oldPin);
            return preparedStatement.executeQuery().next();
        }
    }

    private boolean updatePinInDatabase(Connection connection, String username, String newPin) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PIN_SQL)) {
            preparedStatement.setString(1, newPin);
            preparedStatement.setString(2, username);
            return preparedStatement.executeUpdate() > 0;
        }
    }
}
