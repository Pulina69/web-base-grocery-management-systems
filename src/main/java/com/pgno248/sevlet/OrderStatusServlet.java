package com.pgno248.sevlet;

import com.pgno248.model.Queue;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("/oder/orderStatus")
public class OrderStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Read orders from checkout.txt and use Queue to maintain order
        String checkoutFile = getServletContext().getRealPath("/WEB-INF/classes/checkout.txt");
        Queue<String[]> orderQueue = new Queue<>();
        String username = (String) request.getSession().getAttribute("username");
        try (BufferedReader reader = new BufferedReader(new FileReader(checkoutFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String orderUser = parts[1].trim();
                    if (username != null && username.equals(orderUser)) {
                        orderQueue.enqueue(parts);
                    }
                }
            }
        } catch (Exception e) { /* ignore for now */ }
        // Collect orders in a list to preserve queue order for display
        List<String[]> orders = new ArrayList<>();
        while (!orderQueue.isEmpty()) {
            orders.add(orderQueue.dequeue());
        }
        // Reverse to show first added at the bottom (oldest at bottom)
        Collections.reverse(orders);
        request.setAttribute("userOrders", orders);
        request.getRequestDispatcher("/oder/orderStatus.jsp").forward(request, response);
    }
}

