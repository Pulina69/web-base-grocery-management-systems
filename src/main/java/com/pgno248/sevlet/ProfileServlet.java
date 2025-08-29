package com.pgno248.sevlet;

import com.pgno248.dao.UserDAO;
import com.pgno248.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("/user/profile")
public class ProfileServlet extends HttpServlet {
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        String filePath = getServletContext().getRealPath("/WEB-INF/classes/login.txt");
        userDAO = new UserDAO(filePath);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all users from UserDAO
        List<User> users = userDAO.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameToChange = request.getParameter("username");
        String newStatus = request.getParameter("status");
        
        // Update user status using UserDAO
        boolean updated = userDAO.updateUserStatus(usernameToChange, newStatus);
        
        if (updated) {
            request.getSession().setAttribute("success", "User status updated successfully.");
        } else {
            request.getSession().setAttribute("error", "Failed to update user status.");
        }
        
        response.sendRedirect(request.getContextPath() + "/user/profile");
    }
}
