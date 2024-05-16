package com.example.demo.servlet;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class AuthenticatedSessionServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Authenticate user
        User user = userService.authenticate(username, password);
        if (user == null) {
            response.sendRedirect("/login?error=true");
            return;
        }

        // Invalidate the old session and create a new one
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession newSession = request.getSession(true);

        // Set user info in session
        newSession.setAttribute("user", user);

        // Get the intended URL
        String intendedUrl = (String) newSession.getAttribute("url.intended");
        if (intendedUrl == null) {
            intendedUrl = request.getContextPath() + "/home";
        }

        // Remove the intended URL from session
        newSession.removeAttribute("url.intended");

        // Redirect to the intended URL
        response.sendRedirect(intendedUrl);
    }
}
