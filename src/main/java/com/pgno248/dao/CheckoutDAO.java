package com.pgno248.dao;

import com.pgno248.model.Product;
import java.io.*;
import java.util.*;
public class CheckoutDAO {
    private final String filePath;
    public CheckoutDAO(String filePath) {
        this.filePath = filePath;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            System.out.println("CheckoutDAO using file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveOrder(List<Product> cart, String orderId, String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (Product p : cart) {
                writer.write(orderId + " | " + username + " | " + p.getName() + " | " + p.getPrice() + " | " + p.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Map<String, Object>> getPastOrders() {
        List<Map<String, Object>> orders = new ArrayList<>();
        Map<String, List<Product>> groupedProducts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    String orderId = parts[0].trim();
                    String name = parts[1].trim();
                    double price;
                    int quantity;
                    try {
                        price = Double.parseDouble(parts[2].trim());
                        quantity = Integer.parseInt(parts[3].trim());
                    } catch (NumberFormatException e) {
                        // Log and skip invalid lines
                        System.err.println("Invalid order line (skipped): " + line);
                        continue;
                    }

                    Product product = new Product();
                    product.setId(orderId);
                    product.setName(name);
                    product.setPrice(price);
                    product.setQuantity(quantity);
                    groupedProducts.computeIfAbsent(orderId, k -> new ArrayList<>()).add(product);
                }
            }
            for (Map.Entry<String, List<Product>> entry : groupedProducts.entrySet()) {
                String orderId = entry.getKey();
                List<Product> products = entry.getValue();
                double total = 0.0;
                StringBuilder items = new StringBuilder();
                for (Product p : products) {
                    if (items.length() > 0) {
                        items.append(", ");
                    }
                    items.append(p.getName()).append(" (x").append(p.getQuantity()).append(")");
                    total += p.getPrice() * p.getQuantity();
                }
                Map<String, Object> order = new HashMap<>();
                order.put("orderId", orderId);
                order.put("items", items.toString());
                order.put("total", String.format("%.2f", total));
                orders.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
