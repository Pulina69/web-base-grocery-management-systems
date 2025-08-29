package com.pgno248.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("/delivery/trackDelivery")
public class TrackDeliveryServlet extends HttpServlet {
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
        request.getRequestDispatcher("/delivery/trackDelivery.jsp").forward(request, response);
    }
}