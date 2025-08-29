package com.pgno248.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/delivery/refund")
@MultipartConfig
public class RefundServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/delivery/refund.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        String orderId = request.getParameter("orderId");
        // Get username from session rather than from parameter
        String username = (String) request.getSession().getAttribute("username");
        if (username == null || username.isEmpty()) {
            username = "guest"; // Default fallback if no username in session
        }
        String reason = request.getParameter("reason");
        String status = request.getParameter("status"); // Get the status value from the form
        // The input name in the form is 'photo', not 'image'
        Part imagePart = request.getPart("photo");
        String imageName = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            String ext = imagePart.getSubmittedFileName();
            ext = ext.substring(ext.lastIndexOf('.'));
            imageName = UUID.randomUUID() + ext;
            // Use File.separator for cross-platform compatibility
            String imageDirPath = getServletContext().getRealPath("/resources/");
            File imageDir = new File(imageDirPath);
            if (!imageDir.exists()) imageDir.mkdirs();
            String imagePath = imageDirPath + File.separator + imageName;
            try (InputStream in = imagePart.getInputStream();
                 OutputStream out = new FileOutputStream(imagePath)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            }
        }        String refundFile = getServletContext().getRealPath("/WEB-INF/classes/refund.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(refundFile, true))) {
            // Include status in the data written to the file
            writer.write(orderId + "," + username + "," + reason + "," + 
                        (imageName != null ? imageName : "") + "," + status + "\n");
        }
        request.getSession().setAttribute("refundSuccess", true);
        response.sendRedirect(request.getContextPath() + "/delivery/refund.jsp");
    }
}
