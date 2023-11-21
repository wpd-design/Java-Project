package Olivia;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class UserServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("login")) {
            loginUser(request, response);
        } else if (action.equals("signup")) {
            registerUser(request, response);
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String identityCode = request.getParameter("identityCode");
        String password = request.getParameter("password");

        // Check the credentials in the database
        boolean isValidUser = checkLoginCredentials(identityCode, password);

        if (isValidUser) {
            response.sendRedirect("admin");
        } else {
            response.getWriter().write("Invalid credentials");
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String identityCode = request.getParameter("identityCode");
        String password = request.getParameter("password");

        // Save user data to the database
        boolean isRegistered = saveUserData(name, identityCode, password);

        if (isRegistered) {
            response.getWriter().write("Registration successful");
        } else {
            response.getWriter().write("Registration failed");
        }
    }

    private boolean checkLoginCredentials(String identityCode, String password) {
        // Implement your JDBC code to check credentials in the database
        // Return true if valid, false otherwise
        // Ensure to use PreparedStatement to prevent SQL injection
        // Note: This is a basic example; use connection pooling and handle exceptions properly in production code

        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM adminuser WHERE identityCode=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, identityCode);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If a record is found, credentials are valid

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean saveUserData(String name, String identityCode, String password) {
        // Implement your JDBC code to save user data to the database
        // Return true if successful, false otherwise
        // Ensure to use PreparedStatement to prevent SQL injection
        // Note: This is a basic example; use connection pooling and handle exceptions properly in production code

        try {
            Connection connection = getConnection();
            String query = "INSERT INTO adminuser (name, identityCode, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, identityCode);
            preparedStatement.setString(3, password);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // If rows are affected, registration is successful

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private Connection getConnection() throws Exception {
        // Establish a database connection
        // Replace the placeholder values with your actual database information

        String url = "jdbc:mysql://localhost:3306/adminuser";
        String user = "root";
        String password = "Super123";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}