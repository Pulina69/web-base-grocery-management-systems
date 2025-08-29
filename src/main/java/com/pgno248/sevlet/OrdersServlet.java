package com.pgno248.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/oder/orders")
public class OrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Read deliverydetails.txt
        String deliveryFile = getServletContext().getRealPath("/WEB-INF/classes/deliverydetails.txt");
        String checkoutFile = getServletContext().getRealPath("/WEB-INF/classes/checkout.txt");
        Map<String, String[]> deliveryMap = new HashMap<>(); // orderId -> [username, name, phone, address, items, total, status]
        
        try (BufferedReader reader = new BufferedReader(new FileReader(deliveryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    deliveryMap.put(parts[0].trim(), parts);
                }
            }
        } catch (Exception e) {
            log("Error reading delivery details: " + e.getMessage());
        }

        // Read checkout.txt and count items per orderId
        Map<String, Integer> itemCountMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(checkoutFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String orderId = parts[0].trim();
                    itemCountMap.put(orderId, itemCountMap.getOrDefault(orderId, 0) + 1);
                }
            }
        } catch (Exception e) {
            log("Error reading checkout details: " + e.getMessage());
        }
        
        // Create a list of order data to pass to JSP
        List<Map<String, String>> orders = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : deliveryMap.entrySet()) {
            String orderId = entry.getKey();
            String[] d = entry.getValue();
            
            Map<String, String> order = new HashMap<>();
            order.put("orderId", orderId);
            order.put("customer", d.length > 2 ? d[2] : "Unknown"); // name
            order.put("items", String.valueOf(itemCountMap.getOrDefault(orderId, 0)));
            order.put("total", d.length > 6 ? d[6] : "0"); // total
            order.put("status", "New");
            
            orders.add(order);
        }
        
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/oder/orders.jsp").forward(request, response);
    }
}

