package com.pgno248.dao;

import com.pgno248.model.Product;
import java.io.*;
import java.util.*;
public class ProductDAO {
    private final String filePath;
    public ProductDAO() {
        this.filePath = ProductDAO.class.getClassLoader().getResource("product.txt").getPath();
    }
    public ProductDAO(String filePath) {
        this.filePath = filePath;
    }
    public synchronized void addProduct(Product product) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(product.toString());
            writer.newLine();
        }
    }
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 4) {
                    products.add(new Product(parts[0], parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
    public synchronized void decreaseProductQuantity(String productId, int quantity) throws IOException {
        List<Product> products = getAllProducts();
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                p.setQuantity(p.getQuantity() - quantity);
                break;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Product p : products) {
                writer.write(p.toString());
                writer.newLine();
            }
        }
    }
}
