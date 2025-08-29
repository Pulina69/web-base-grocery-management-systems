package com.pgno248.dao;

import com.pgno248.model.User;
import java.io.*;
import java.util.*;

public class UserDAO {
    private final String filePath;

    public UserDAO(String filePath) {
        this.filePath = filePath;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User findUser(String username, String password, String role) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null &&
                        user.getUsername().equals(username) &&
                        user.getPassword().equals(password) &&
                        user.getRole().equalsIgnoreCase(role)) {
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save a new user
     * @param user the user to save
     * @return true if saved successfully, false otherwise
     */
    public boolean saveUser(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(user.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all users
     * @return List of all users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Update a user's status
     * @param username the username of the user to update
     * @param newStatus the new status
     * @return true if updated successfully, false otherwise
     */
    public boolean updateUserStatus(String username, String newStatus) {
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        // Read all lines and update the target user
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null && user.getUsername().equals(username)) {
                    user.setStatus(newStatus);
                    line = user.toString();
                    updated = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Write all lines back to the file
        if (updated) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, false))) {
                for (String line : lines) {
                    writer.println(line);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    /**
     * Check if a username already exists
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null && user.getUsername().equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}