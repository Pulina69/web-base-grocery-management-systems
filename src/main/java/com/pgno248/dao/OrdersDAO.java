package com.pgno248.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrdersDAO {
    private final String deliveryFilePath;
    private final String checkoutFilePath;

    public OrdersDAO(String deliveryFilePath, String checkoutFilePath) {
        this.deliveryFilePath = deliveryFilePath;
        this.checkoutFilePath = checkoutFilePath;
    }

    public Map<String, Map<String, Object>> getAllOrders() {
        Map<String, Map<String, Object>> orders = new HashMap<>();
        Map<String, String[]> deliveryMap = readDeliveryDetails();
        Map<String, Integer> itemCountMap = readItemCounts();

        for (Map.Entry<String, String[]> entry : deliveryMap.entrySet()) {
            String orderId = entry.getKey();
            String[] deliveryDetails = entry.getValue();

            if (deliveryDetails.length >= 7) {
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("orderId", orderId);
                orderData.put("username", deliveryDetails[1].trim());
                orderData.put("customerName", deliveryDetails[2].trim());
                orderData.put("phone", deliveryDetails[3].trim());
                orderData.put("address", deliveryDetails[4].trim());
                orderData.put("items", itemCountMap.getOrDefault(orderId, 0));
                orderData.put("itemsList", deliveryDetails[5].trim());
                orderData.put("total", deliveryDetails[6].trim());

                // Add status if available
                if (deliveryDetails.length >= 8) {
                    orderData.put("status", deliveryDetails[7].trim());
                } else {
                    orderData.put("status", "New");
                }

                orders.put(orderId, orderData);
            }
        }

        return orders;
    }

    private Map<String, String[]> readDeliveryDetails() {
        Map<String, String[]> deliveryMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(deliveryFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    deliveryMap.put(parts[0].trim(), parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deliveryMap;
    }

    private Map<String, Integer> readItemCounts() {
        Map<String, Integer> itemCountMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(checkoutFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String orderId = parts[0].trim();
                    itemCountMap.put(orderId, itemCountMap.getOrDefault(orderId, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemCountMap;
    }
}
