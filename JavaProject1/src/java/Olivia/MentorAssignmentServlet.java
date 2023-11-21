package Olivia;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MentorAssignmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int ng;
    private List<String> mentors;
    private List<String> groups;
    private List<String> groupLeaders;

    @Override
    public void init() throws ServletException {
        // Initialize data during servlet initialization
        mentors = new ArrayList<>();
        groups = new ArrayList<>();
        groupLeaders = new ArrayList<>();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Get the number of groups from the request
        ng = Integer.parseInt(request.getParameter("numGroups"));

        // Initialize data and assign mentors to groups
        initializeData();
        assignMentorsToGroups();

        // Create PrintWriter for writing HTML response
        PrintWriter out = response.getWriter();

        // Write HTML response
        writeHtmlResponse(out);
    }

    private void writeHtmlResponse(PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Mentor Assignment Result</title>");
        out.println("<style>");
    out.println("    body {");
   out.println("        font-family: Arial, sans-serif;");
    out.println("        text-align: center;");
    out.println("        background-color: #f4f4f4;");
    // Add the background image rule
    out.println("        background-image: url('images/img1.jpg');");
    out.println("        background-size: cover;");
    out.println("        background-repeat: no-repeat;");
    out.println("    }");
    out.println("    h1 {");
    out.println("        color: #333;");
    out.println("    }");
    out.println("    table {");
    out.println("        margin: 20px auto;");
    out.println("        border-collapse: collapse;");
    out.println("        width: 70%;");
    out.println("    }");
    out.println("    th, td {");
    out.println("        border: 1px solid #ddd;");
    out.println("        padding: 10px;");
    out.println("        text-align: left;");
    out.println("    }");
    out.println("    th {");
    out.println("        background-color: #4CAF50;");
    out.println("        color: white;");
    out.println("    }");
    out.println("    a {");
    out.println("        display: inline-block;");
    out.println("        padding: 10px 20px;");
    out.println("        margin-top: 20px;");
    out.println("        text-decoration: none;");
    out.println("        color: white;");
    out.println("        background-color: #4CAF50;");
    out.println("        border-radius: 5px;");
    out.println("    }");
    out.println("</style>");

        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Mentor Assignment Result</h1>");
        out.println("<table border='1'>");
        out.println("<tr><td>Mentor</td><td>Group</td><td>Group Leader</td></tr>");
        // Loop through and display assigned mentors
        for (int i = 0; i < ng; i++) {
            String group = groups.get(i);
            String mentor = mentors.get(i);
            String groupLeader = groupLeaders.get(i % groupLeaders.size()); // Cycle through group leaders
            
            out.println("<tr><td>" + mentor + "</td><td>" + group + "</td><td>" + groupLeader + "</td></tr>");
        }

        out.println("</table>");
        out.println("<a href='List.html'>Re-Insert</a>");
        out.println("</body>");
        out.println("</html>");
    }

    private void assignMentorsToGroups() {
        // Shuffle mentors to assign them randomly to groups
        Collections.shuffle(mentors);
    }

    private void initializeData() {
        // Fetch top 14 students as group leaders from the database
        try {
           
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/mydb";
            String username = "root";
            String password = "Super123";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                if (connection != null) {
                    
                    // SQL query to get top 14 students based on percentage
                    String sql = "SELECT sname FROM studinfo ORDER BY per DESC";
                    try (PreparedStatement statement = connection.prepareStatement(sql);
                         ResultSet resultSet = statement.executeQuery()) {
                        
                        // Populate group leaders from the database result
                        while (resultSet.next()) {
                           groupLeaders.add(resultSet.getString("sname"));
                          
                        }
                    }
                } else {
                    System.out.println("Failed to connect to the database.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize your initial data
        mentors.add("Mrs. S.S.Chavhan");
        mentors.add("Mrs. V.M.Aswar");
        mentors.add("Mr. G.R.Sawant");
        mentors.add("Mrs. C.R.Chaudhary");
        mentors.add("Mrs. V.R.Rathod");
        mentors.add("Mr. R.G.Belsare");
        mentors.add("Mrs. S.Rangari");
        mentors.add("Mrs. S.Rangari");
        mentors.add("Mr. R.G.Belsare");
        mentors.add("Mrs. V.R.Rathod");
        mentors.add("Mrs. C.R.Chaudhary");
        mentors.add("Mr. G.R.Sawant");
        mentors.add("Mrs. V.M.Aswar");
        mentors.add("Mrs. S.S.Chavhan");

        // Ensure that the size of mentors list matches the number of groups
        int requiredSize = ng * groupLeaders.size();
        while (mentors.size() < requiredSize) {
            mentors.addAll(mentors);
        }

        for (int i = 0; i < ng; i++) {
            groups.add("Group " + (i + 1));
        }
    }
    
}