package com.pgno248.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class DeliveryDetailsDAO {
    private final String filePath;
    public DeliveryDetailsDAO(String filePath) {
        this.filePath = filePath;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            System.out.println("DeliveryDetailsDAO using file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveDetails(String orderId, String username, String name, String phone, String address, String items, String total, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(orderId + "," + username + "," + name + "," + phone + "," + address + "," + items + "," + total + "," + status);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}