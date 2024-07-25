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

@WebServlet("/adminloginservlet")
public class AdminLoginServlet extends HttpServlet {
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

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = sqlDAO.getConnection()) {
            String sql = "SELECT username FROM admin WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Store admin details in session
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                // Redirect to admindashboard.jsp on successful login
                response.sendRedirect("admindashboard.jsp");
            } else {
                out.println("<html><head><title>Login Failed</title></head><body>");
                out.println("<h2>Login Failed</h2>");
                out.println("<p>Invalid username or password. Please try again.</p>");
                out.println("</body></html>");
            }
        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
