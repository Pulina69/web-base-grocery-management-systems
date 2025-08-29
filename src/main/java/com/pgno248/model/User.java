package com.pgno248.model;

public class User {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private String status;

    public User() {
    }

    public User(String username, String password, String email, String phone, String role, String status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return username + "," + password + "," + email + "," + phone + "," + role + "," + status;
    }

    public static User fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        
        String[] parts = line.split(",");
        if (parts.length >= 6) {
            return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;
    }
}