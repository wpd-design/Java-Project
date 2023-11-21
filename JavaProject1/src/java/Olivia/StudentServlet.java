package Olivia;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String identityCode = request.getParameter("identityCode");
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String year = request.getParameter("year");
    String department = request.getParameter("department");
    String percentageParam = request.getParameter("percentage");

    // Check if the percentage parameter is not null and not empty
    if (percentageParam != null && !percentageParam.trim().isEmpty()) {
        try {
            double percentage = Double.parseDouble(percentageParam);

            // Update the database connection details
            String jdbcUrl = "jdbc:mysql://your-database-host:3306/your-database-name";
            String dbUser = "your-database-username";
            String dbPassword = "your-database-password";

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "INSERT INTO student (identity_code, name, email, year, department, percentage) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, identityCode);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, year);
                    preparedStatement.setString(5, department);
                    preparedStatement.setDouble(6, percentage);

                    preparedStatement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    } else {
        // Handle the case where the "percentage" parameter is null or empty
        System.out.println("Percentage parameter is null or empty");
    }

    //response.sendRedirect("admin.html"); // Redirect back to the registration page
}

}
