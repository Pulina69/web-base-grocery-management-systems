package com.pgno248.dao;

import com.pgno248.model.Product;
import java.io.*;
import java.util.*;
public class ProductListDAO {
    private final String filePath;
    public ProductListDAO() {
        this.filePath = ProductListDAO.class.getClassLoader().getResource("product.txt").getPath();
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
}
