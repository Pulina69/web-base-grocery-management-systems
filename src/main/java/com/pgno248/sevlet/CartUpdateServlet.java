package com.pgno248.sevlet;

import com.pgno248.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/checkout/cart/update")
public class CartUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String productId = request.getParameter("productId");

        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart != null && productId != null) {
            if ("remove".equals(action)) {
                // Remove item from cart
                Iterator<Product> iterator = cart.iterator();
                while (iterator.hasNext()) {
                    Product item = iterator.next();
                    if (item.getId().equals(productId)) {
                        iterator.remove();
                        break;
                    }
                }
            } else if ("update".equals(action)) {
                // Update item quantity
                String quantityStr = request.getParameter("quantity");
                if (quantityStr != null && !quantityStr.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        if (quantity > 0) {
                            // Check available stock
                            com.pgno248.dao.ProductDAO productDAO = new com.pgno248.dao.ProductDAO(getServletContext().getRealPath("/WEB-INF/classes/product.txt"));
                            List<com.pgno248.model.Product> products = productDAO.getAllProducts();
                            int available = -1;
                            for (com.pgno248.model.Product prod : products) {
                                if (prod.getId().equals(productId)) {
                                    available = prod.getQuantity();
                                    break;
                                }
                            }
                            if (available >= 0 && quantity > available) {
                                session.setAttribute("cartError", "Requested quantity exceeds available stock (" + available + ").");
                            } else {
                                for (Product item : cart) {
                                    if (item.getId().equals(productId)) {
                                        item.setQuantity(quantity);
                                        break;
                                    }
                                }
                                session.removeAttribute("cartError");
                            }
                        } else if (quantity <= 0) {
                            // If quantity is zero or negative, remove the item
                            Iterator<Product> iterator = cart.iterator();
                            while (iterator.hasNext()) {
                                Product item = iterator.next();
                                if (item.getId().equals(productId)) {
                                    iterator.remove();
                                    break;
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Update session with modified cart
            session.setAttribute("cart", cart);
        }

        // Redirect back to the checkout page
        response.sendRedirect(request.getContextPath() + "/checkout");
    }
}
