package com.pgno248.sevlet;

import com.pgno248.dao.UserDAO;
import com.pgno248.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        String filePath = getServletContext().getRealPath("/WEB-INF/classes/login.txt");
        userDAO = new UserDAO(filePath);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        String status = "no"; // Default status for new registration

        // Check if username already exists
        if (userDAO.usernameExists(username)) {
            request.getSession().setAttribute("error", "Username already exists. Please choose another.");
            response.sendRedirect(request.getContextPath() + "/user/register.jsp");
            return;
        }

        // Create a new User object
        User newUser = new User(username, password, email, phone, role, status);

        // Save the user via DAO
        if (userDAO.saveUser(newUser)) {
            request.getSession().setAttribute("success", "Registration successful! You can now log in.");
        } else {
            request.getSession().setAttribute("error", "Registration failed. Please try again.");
        }

        response.sendRedirect(request.getContextPath() + "/user/register.jsp");
    }
}
