package com.pgno248.sevlet;

import com.pgno248.dao.UserDAO;
import com.pgno248.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
        String selectedRole = request.getParameter("role");
        
        // Use UserDAO to find the user with the provided credentials
        User user = userDAO.findUser(username, password, selectedRole);
        
        if (user != null) {
            String role = user.getRole();
            String status = user.getStatus();
            
            if ("yes".equalsIgnoreCase(status)) {
                // Create session for authenticated user
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);
                
                // Redirect based on user role
                if ("EMPLOYEE".equalsIgnoreCase(role)) {
                    response.sendRedirect(request.getContextPath() + "/dashboard/dashboard");
                } else if ("ADMIN".equalsIgnoreCase(role)) {
                    response.sendRedirect(request.getContextPath() + "/user/profile");
                } else if ("CUSTOMER".equalsIgnoreCase(role)) {
                    response.sendRedirect(request.getContextPath() + "/product/index");
                }
            } else {
                request.setAttribute("error", "Your account is not active or not authorized.");
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        }
    }
}
