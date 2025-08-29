package com.pgno248.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("/dashboard/itemanalize")
public class ItemAnalizeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productFile = getServletContext().getRealPath("/WEB-INF/classes/product.txt");
        List<String> labels = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(productFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                // Format: id:name:price:quantity
                if (parts.length == 4) {
                    labels.add(parts[1].trim());
                    try {
                        quantities.add(Integer.parseInt(parts[3].trim()));
                    } catch (Exception e) {
                        quantities.add(0);
                    }
                }
            }
        }
        // Build JSON manually
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"labels\": [");
        for (int i = 0; i < labels.size(); i++) {
            sb.append("\"").append(labels.get(i).replace("\"", "\\\"")).append("\"");
            if (i < labels.size() - 1) sb.append(",");
        }
        sb.append("],");
        sb.append("\"quantities\": [");
        for (int i = 0; i < quantities.size(); i++) {
            sb.append(quantities.get(i));
            if (i < quantities.size() - 1) sb.append(",");
        }
        sb.append("]");
        sb.append("}");
        String itemAnalysisJson = sb.toString();
        request.setAttribute("itemAnalysisJson", itemAnalysisJson);
        request.getRequestDispatcher("/dashboard/itemanalize.jsp").forward(request, response);
    }
}
