package com.pgno248.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("/oder/oderanalize")
public class OderAnalizeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oderFile = getServletContext().getRealPath("/WEB-INF/classes/checkout.txt");
        Map<String, Integer> itemSales = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(oderFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                // Format: OrderID | Username | ProductName | Price | Quantity
                if (parts.length >= 5) {
                    String itemName = parts[2].trim();
                    int quantity = 1;
                    try { quantity = Integer.parseInt(parts[4].trim()); } catch (Exception ignored) {}
                    itemSales.put(itemName, itemSales.getOrDefault(itemName, 0) + quantity);
                }
            }
        }
        // Prepare JSON for chart.js (manual, no external library)
        List<String> labels = new ArrayList<>(itemSales.keySet());
        List<Integer> soldData = new ArrayList<>();
        for (String key : labels) soldData.add(itemSales.get(key));
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"labels\": [");
        for (int i = 0; i < labels.size(); i++) {
            sb.append("\"").append(labels.get(i).replace("\"", "\\\"")).append("\"");
            if (i < labels.size() - 1) sb.append(",");
        }
        sb.append("],");
        sb.append("\"soldData\": [");
        for (int i = 0; i < soldData.size(); i++) {
            sb.append(soldData.get(i));
            if (i < soldData.size() - 1) sb.append(",");
        }
        sb.append("],");
        sb.append("\"deliveredData\": [");
        for (int i = 0; i < soldData.size(); i++) {
            sb.append(soldData.get(i));
            if (i < soldData.size() - 1) sb.append(",");
        }
        sb.append("]");
        sb.append("}");
        String orderAnalysisJson = sb.toString();
        request.setAttribute("orderAnalysisJson", orderAnalysisJson);
        request.getRequestDispatcher("/oder/oderanalize.jsp").forward(request, response);
    }
}
