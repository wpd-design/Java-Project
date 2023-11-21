package Olivia;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StudInfo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve data from the form
        String name = request.getParameter("name");
        String identityCode = request.getParameter("identityCode");
        String year = request.getParameter("year");
        String department = request.getParameter("department");
        String percentage = request.getParameter("percentage");

        // Database connection parameters

        String jdbcUrl = "jdbc:mysql://localhost:3306/mydb";
        String dbUser = "root";
        String dbPassword = "Super123";

        boolean success = false; // Flag to check if data is successfully inserted

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            // SQL query to insert data into the database
            String sql = "INSERT INTO studinfo (name, id, y, dept, per) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, identityCode);
                statement.setString(3, year);
                statement.setString(4, department);
                statement.setString(5, percentage);

                // Execute the query
                int rowsInserted = statement.executeUpdate();

                // Check if the data was successfully inserted
                success = rowsInserted > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // Generate HTML response with JavaScript for the pop-up
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        if (success) {
            // Show success message
            out.println("<script>alert('Data successfully saved in the database.');</script>");
            response.sendRedirect("login.html");
        } else {
            // Show error message
            out.println("<script>alert('Error saving data in the database.');</script>");
        }
        out.println("</body></html>");
    }
}
