package com.pgno248.sevlet;

import com.pgno248.dao.ProductDAO;
import com.pgno248.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product/addItemServlet")
public class AddItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("itemId");
        String name = request.getParameter("itemName");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");
        String filePath = getServletContext().getRealPath("/WEB-INF/classes/product.txt");

        if (id == null || name == null || priceStr == null || quantityStr == null ||
            id.isEmpty() || name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/product/addItem.jsp").forward(request, response);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            Product product = new Product(id, name, price, quantity);
            ProductDAO dao = new ProductDAO(filePath);
            dao.addProduct(product);
            request.setAttribute("success", "Product added successfully!");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid price or quantity.");
        } catch (IOException e) {
            request.setAttribute("error", "Error saving product: " + e.getMessage());
        }
        request.getRequestDispatcher("/product/addItem.jsp").forward(request, response);
    }
}
