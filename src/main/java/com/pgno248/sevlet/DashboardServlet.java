package com.pgno248.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@WebServlet(urlPatterns = {"/dashboard/dashboard", "/dashboard/updateRefundStatus"})
public class DashboardServlet extends HttpServlet {    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle refund status updates
        String orderId = request.getParameter("orderId");
        String newStatus = request.getParameter("status");
        
        if (orderId != null && !orderId.isEmpty() && newStatus != null && !newStatus.isEmpty()) {
            // Validate status value
            if ("pending".equals(newStatus) || "approved".equals(newStatus) || "rejected".equals(newStatus)) {
                updateRefundStatus(orderId, newStatus);
                // Add success message to session
                request.getSession().setAttribute("statusUpdateSuccess", 
                    "Refund status for Order " + orderId + " has been updated to " + newStatus);
            } else {
                // Add error message to session
                request.getSession().setAttribute("statusUpdateError", 
                    "Invalid status value: " + newStatus);
            }
        } else {
            // Add error message to session
            request.getSession().setAttribute("statusUpdateError", 
                "Missing required parameters for status update");
        }
        
        // Redirect back to dashboard
        response.sendRedirect(request.getContextPath() + "/dashboard/dashboard");
    }// Method to update refund status in refund.txt
    private void updateRefundStatus(String orderId, String newStatus) {
        try {
            String refundFilePath = getServletContext().getRealPath("/WEB-INF/classes/refund.txt");
            File inputFile = new File(refundFilePath);
            File tempFile = new File(refundFilePath + ".tmp");
            
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                 
                String line;
                boolean found = false;
                
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 1 && parts[0].trim().equals(orderId)) {
                        // This is the line to update
                        found = true;
                        StringBuilder updatedLine = new StringBuilder();
                        
                        // Always keep orderId, username, and reason
                        updatedLine.append(parts[0]).append(",");
                        updatedLine.append(parts.length > 1 ? parts[1] : "").append(",");
                        updatedLine.append(parts.length > 2 ? parts[2] : "").append(",");
                        
                        // Handle imageName and status
                        if (parts.length >= 5) {
                            // Format: orderId,username,reason,imageName,status
                            updatedLine.append(parts[3]).append(",").append(newStatus);
                        } else if (parts.length == 4) {
                            // Format: orderId,username,reason,imageName (no status)
                            updatedLine.append(parts[3]).append(",").append(newStatus);
                        } else {
                            // Format: orderId,username,reason (no image, no status)
                            updatedLine.append(",").append(newStatus);
                        }
                        
                        writer.write(updatedLine.toString());
                    } else {
                        // Keep line unchanged
                        writer.write(line);
                    }
                    writer.newLine();
                }
                
                // If we didn't find the orderId, log an error
                if (!found) {
                    System.err.println("Error: OrderID " + orderId + " not found in refund.txt");
                }
            }
            
            // Replace original file with updated file
            if (tempFile.exists()) {
                Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Use the actual deployed path for Tomcat exploded deployment
        String base = getServletContext().getRealPath("/WEB-INF/classes/");
        int totalOrders = 0, newOrders = 0, processingOrders = 0, completeOrders = 0, totalCustomers = 0, totalEmployees = 0, totalRefunds = 0;
        double totalEarnings = 0.0;
        Set<String> customers = new HashSet<>();
        Set<String> employees = new HashSet<>();


        // Orders and status from deliverydetails.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(base + "/deliverydetails.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    totalOrders++;
                    String status = parts[7].trim();
                    if ("new".equalsIgnoreCase(status)) newOrders++;
                    if ("processing".equalsIgnoreCase(status)) processingOrders++;
                    if ("complete".equalsIgnoreCase(status)) completeOrders++;
                    customers.add(parts[1].trim());
                }
            }
        } catch (Exception ignored) {}

        // Earnings from payment.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(base + "/payment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Find the amount field (should be the 6th field, index 5)
                if (parts.length >= 6) {
                    try { totalEarnings += Double.parseDouble(parts[5]); } catch (Exception ignored) {}
                }
            }
        } catch (Exception ignored) {}

        // Customers and employees from login.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(base + "/login.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String role = parts[4].trim().toLowerCase();
                    if ("customer".equals(role)) totalCustomers++;
                    if ("employee".equals(role)) totalEmployees++;
                }
            }
        } catch (Exception ignored) {}        // Refunds from refund.txt
        List<Map<String, String>> refunds = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(base + "/refund.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines or comments
                if (line.trim().isEmpty() || line.trim().startsWith("//")) {
                    continue;
                }
                
                totalRefunds++;
                String[] parts = line.split(",");
                Map<String, String> refund = new HashMap<>();
                
                // Make sure we have at least an order ID
                if (parts.length > 0) {
                    refund.put("orderId", parts[0].trim());
                    
                    // Add username if available
                    refund.put("username", parts.length > 1 ? parts[1].trim() : "Unknown");
                    
                    // Add reason if available
                    refund.put("reason", parts.length > 2 ? parts[2].trim() : "No reason provided");
                    
                    // Add image name if available
                    refund.put("imageName", parts.length > 3 ? parts[3].trim() : "");
                    
                    // Add status if available, default to "pending"
                    refund.put("status", parts.length > 4 ? parts[4].trim() : "pending");
                    
                    refunds.add(refund);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading refunds: " + e.getMessage());
        }

        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("newOrders", newOrders);
        request.setAttribute("processingOrders", processingOrders);
        request.setAttribute("completeOrders", completeOrders);
        request.setAttribute("totalEarnings", totalEarnings);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("totalEmployees", totalEmployees);
        request.setAttribute("totalRefunds", totalRefunds);
        request.setAttribute("refunds", refunds);
        request.getRequestDispatcher("/dashboard/dashboard.jsp").forward(request, response);
    }
}
