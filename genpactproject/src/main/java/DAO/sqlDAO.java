package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/banking";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "asdefg123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Failed to load JDBC driver");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }
}
