package com.pgno248.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

@WebServlet("/delivery/delivery")
public class DeliveryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String deliveryFile = getServletContext().getRealPath("/WEB-INF/classes/deliverydetails.txt");
        List<String[]> deliveries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(deliveryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    deliveries.add(parts);
                } else if (parts.length == 7) {
                    String[] withStatus = Arrays.copyOf(parts, 8);
                    withStatus[7] = "new";
                    deliveries.add(withStatus);
                }
            }
        } catch (Exception e) { /* ignore for now */ }
        request.setAttribute("deliveries", deliveries);
        request.getRequestDispatcher("/delivery/delivery.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        String newStatus = request.getParameter("status");
        String filePath = getServletContext().getRealPath("/WEB-INF/classes/deliverydetails.txt");
        File file = new File(filePath);
        File tempFile = new File(file.getAbsolutePath() + ".tmp");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[0].trim().equals(orderId)) {
                    // Update status (last field)
                    if (parts.length == 7) {
                        // Add status if missing
                        writer.write(line + "," + newStatus);
                    } else {
                        parts[7] = newStatus;
                        writer.write(String.join(",", parts));
                    }
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        }
        // Replace original file
        Files.delete(file.toPath());
        tempFile.renameTo(file);
        response.sendRedirect(request.getContextPath() + "/delivery/delivery");
    }
}
