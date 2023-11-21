package Olivia;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class admin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Admin Dashboard</title>");
        out.println("<link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.4.1/css/all.css' "
                + "integrity='sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz' "
                + "crossorigin='anonymous'/>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Roboto:300,400,500,700' rel='stylesheet'/>");
        out.println("<style>");
        out.println("body { min-height: 100%; background: #f2f2f2; font-family: Roboto, Arial, sans-serif; font-size: 16px; color: #333; }");
        out.println(".main-block { display: flex; justify-content: space-between; align-items: center; height: 100%; padding: 25px; }");
        out.println(".left-part { text-align: center; }");
        out.println(".fa-graduation-cap { font-size: 72px; }");
        out.println("form { background: #fff; flex-grow: 1; margin: 0 20px; padding: 20px; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; overflow-y: auto; }");
        out.println("table, th, td { border: 1px solid #333; }");
        out.println("th, td { padding: 10px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println(".btn-group { display: flex; flex-direction: column; align-items: flex-start; }");
        out.println(".btn-item { padding: 10px 5px; margin-bottom: 10px; border-radius: 5px; border: none; background: #87CEEB; text-decoration: none; font-size: 15px; font-weight: 400; color: #fff; cursor: pointer; }");
        out.println(".btn-item:hover { background: #4682B4; }");
        out.println("</style>");
        out.println("<script>");
        out.println("function goToHome() { window.location.href = 'List.html'; }");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='main-block'>");
        out.println("<div class='left-part'>");
        out.println("<i class='fas fa-graduation-cap'></i>");
        out.println("<h1>Admin Dashboard</h1>");
        out.println("<div class='btn-group'>");
        out.println("<button type='button' class='btn-item' onclick='goToHome()'>Assign Mentor</button>");
        out.println("</div>");
        out.println("</div>");

        out.println("<form id='studentForm'>");
        out.println("<div class='title'>");
        out.println("<h2>Student Details</h2>");
        out.println("</div>");
        out.println("<table id='studentTable'>");

        try {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "Super123");
    PreparedStatement statement = connection.prepareStatement("SELECT * FROM studinfo");
    ResultSet resultSet = statement.executeQuery();

    int rowCount = 0;
    out.println("<tr><th>Name</th><th>Identity Code</th><th>Year</th><th>Department</th><th>Percentage</th></tr>");
    while (resultSet.next()) {
        rowCount++;
        out.println("<tr><td>" + resultSet.getString("sname") + "</td>"
                + "<td>" + resultSet.getString("id") + "</td>"
                + "<td>" + resultSet.getString("y") + "</td>"
                + "<td>" + resultSet.getString("dept") + "</td>"
                + "<td>" + resultSet.getString("per") + "</td></tr>");
    }

    // Debug prints
    System.out.println("Fetched " + rowCount + " records from the database.");

    connection.close();
} catch (SQLException e) {
    e.printStackTrace();
    out.println("<p>Error fetching data from the database. Please check the server logs for details.</p>");
}


        out.println("</table>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}
