package com.pgno248.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("/dashboard/deliveryanalize")
public class DeliveryAnalizeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String deliveryFile = getServletContext().getRealPath("/WEB-INF/classes/deliverydetails.txt");
        int complete = 0, processing = 0, pending = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(deliveryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Format: orderId,username,name,phone,address,items,total,status
                if (parts.length >= 8) {
                    String status = parts[7].trim().toLowerCase();
                    if ("complete".equals(status)) complete++;
                    else if ("processing".equals(status)) processing++;
                    else pending++;
                }
            }
        }
        // Build JSON for Chart.js
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"labels\": [\"Complete\", \"Processing\", \"New\"],");
        sb.append("\"data\": [").append(complete).append(",").append(processing).append(",").append(pending).append("]");
        sb.append("}");
        String deliveryStatusJson = sb.toString();
        request.setAttribute("deliveryStatusJson", deliveryStatusJson);
        request.getRequestDispatcher("/dashboard/deliveryanalize.jsp").forward(request, response);
    }
}
