package com.pgno248.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class PaymentDAO {
    private final String filePath;

    public PaymentDAO(String filePath) {
        this.filePath = filePath;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("PaymentDAO created payment file at: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating payment file: " + e.getMessage());
        }
    }
    public boolean savePayment(String orderId, String username, String cardholderName, String cardNumber, String amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(new Date());
            String maskedCard = "xxxx-xxxx-xxxx-" + cardNumber.substring(cardNumber.length() - 4);
            writer.write(orderId + "," + username + "," + timestamp + "," + cardholderName + "," +
                    maskedCard + "," + amount + ",SUCCESSFUL");
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}