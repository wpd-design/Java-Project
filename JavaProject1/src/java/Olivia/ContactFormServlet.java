package Olivia;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class ContactFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        // Perform any necessary validation

        // Send email (You should replace the placeholders with your actual email configuration)
        String to = "prashantsontakke386@gmail.com"; // Replace with your email address
        String subject = "Contact Form Submission";
        String emailBody = "Name: " + name + "\nEmail: " + email + "\nMessage: " + message;

        // You can use a library like JavaMail to send emails
        // Here, we are using a simple print statement for demonstration purposes
        PrintWriter out = response.getWriter();
        out.println("Email Body:\n" + emailBody);

        // Redirect back to the contact page or show a confirmation message
        response.sendRedirect("contact.html");
    }
}
