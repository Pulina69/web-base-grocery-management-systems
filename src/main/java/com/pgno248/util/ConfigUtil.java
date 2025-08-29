package com.pgno248.util;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

public class ConfigUtil {
    private static final String[] FILES = {
            "product.txt",
            "checkout.txt",
            "deliverydetails.txt",
            "login.txt",
            "payment.txt",
            "refund.txt"
    };

    public static void initializeFiles(String basePath) {
        // Ensure all required txt files exist in classes
        for (String fileName : FILES) {
            File file = new File(basePath, fileName);
            if (!file.exists()) {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // Always copy login.txt data from resources to classes
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources";
        File src = new File(resourcesPath, "login.txt");
        File dest = new File(basePath, "login.txt");
        if (src.exists()) {
            try (
                    FileInputStream in = new FileInputStream(src);
                    FileOutputStream out = new FileOutputStream(dest, false)
            ) {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ensureAllTxtFilesInClasses(String classesPath) {
        File dir = new File(classesPath);
        if (!dir.exists()) dir.mkdirs();
        Set<String> existing = new HashSet<>();
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files != null) {
            for (File f : files) {
                existing.add(f.getName());
            }
        }
        for (String fileName : FILES) {
            if (!existing.contains(fileName)) {
                try {
                    File file = new File(dir, fileName);
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Copy all txt files from src/main/resources to target/PNGO248/WEB-INF/classes (overwrite content only)
    public static void copyResourcesToTarget(String resourcesPath, String targetPath) {
        for (String fileName : FILES) {
            File src = new File(resourcesPath, fileName);
            File dest = new File(targetPath, fileName);
            if (src.exists()) {
                try {
                    if (!dest.exists()) {
                        dest.getParentFile().mkdirs();
                        dest.createNewFile();
                    }
                    try (
                            FileInputStream in = new FileInputStream(src);
                            FileOutputStream out = new FileOutputStream(dest, false)
                    ) {
                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Copy all txt files from target/PNGO248/WEB-INF/classes to src/main/resources (overwrite content only)
    public static void copyTargetToResources(String targetPath, String resourcesPath) {
        for (String fileName : FILES) {
            File src = new File(targetPath, fileName);
            File dest = new File(resourcesPath, fileName);
            if (src.exists()) {
                try {
                    if (!dest.exists()) {
                        dest.getParentFile().mkdirs();
                        dest.createNewFile();
                    }
                    try (
                            FileInputStream in = new FileInputStream(src);
                            FileOutputStream out = new FileOutputStream(dest, false)
                    ) {
                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Sync both ways: always copy target to resources, then resources to target (resources is the backup)
    public static void syncTxtFiles(String resourcesPath, String targetPath) {
        copyTargetToResources(targetPath, resourcesPath);
        copyResourcesToTarget(resourcesPath, targetPath);
    }
}
