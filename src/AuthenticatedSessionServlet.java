package com.example.demo.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class AuthenticatedSessionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Dummy authentication logic
        if ("user".equals(username) && "password".equals(password)) {
            // Invalidate the old session and create a new one
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            HttpSession newSession = request.getSession(true);

            // Store user information in the new session
            newSession.setAttribute("username", username);

            // Retrieve the intended URL from the session
            String intendedUrl = (String) newSession.getAttribute("url.intended");
            if (intendedUrl == null) {
                intendedUrl = request.getContextPath() + "/home";
            }
            newSession.removeAttribute("url.intended");

            // Redirect to the intended URL
            response.sendRedirect(intendedUrl);
        } else {
            // Authentication failed, redirect back to login page with error message
            request.setAttribute("error", "Invalid username or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
