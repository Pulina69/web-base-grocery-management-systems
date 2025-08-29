package com.pgno248.sevlet;

import com.pgno248.dao.CheckoutDAO;
import com.pgno248.model.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet(urlPatterns = {"/checkout", "/checkout/process"})
public class CheckoutServlet extends HttpServlet {
    private CheckoutDAO checkoutDAO;
    private static final double DELIVERY_FEE = 200.00;

    @Override
    public void init() throws ServletException {
        String filePath = getServletContext().getRealPath("/WEB-INF/classes/checkout.txt");
        checkoutDAO = new CheckoutDAO(filePath);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart != null && !cart.isEmpty()) {
            // Generate order ID
            String orderId = java.util.UUID.randomUUID().toString().substring(0, 8);
            // Calculate total and items string
            double total = 0.0;
            StringBuilder items = new StringBuilder();
            for (int i = 0; i < cart.size(); i++) {
                Product p = cart.get(i);
                total += p.getPrice() * p.getQuantity();
                if (i > 0) items.append(", ");
                items.append(p.getName()).append(" (x").append(p.getQuantity()).append(")");
            }
            double orderTotal = total + DELIVERY_FEE;
            // Save order with orderId and username
            String username = (String) session.getAttribute("username");
            checkoutDAO.saveOrder(cart, orderId, username);
            // Decrease product quantities after checkout
            String productFilePath = getServletContext().getRealPath("/WEB-INF/classes/product.txt");
            com.pgno248.dao.ProductDAO productDAO = new com.pgno248.dao.ProductDAO(productFilePath);
            for (Product p : cart) {
                productDAO.decreaseProductQuantity(p.getId(), p.getQuantity());
            }
            // Set session attributes for delivery details page
            session.setAttribute("orderId", orderId);
            session.setAttribute("orderTotal", orderTotal);
            session.setAttribute("orderItems", items.toString());
            session.removeAttribute("cart");
        }
        response.sendRedirect(request.getContextPath() + "/checkout/delivery details.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get any sort parameter
        String sortOrder = request.getParameter("sort");

        // Get the session and cart data
        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        // Calculate cart values
        double subtotal = 0.0;
        int itemCount = 0;

        // Create an empty list if cart is null
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Calculate values based on cart items
        itemCount = cart.size();
        for (Product item : cart) {
            subtotal += item.getPrice() * item.getQuantity();
        }

        double total = subtotal + DELIVERY_FEE;

        // Format numbers with 2 decimal places
        String formattedSubtotal = String.format("%.2f", subtotal);
        String formattedDeliveryFee = String.format("%.2f", DELIVERY_FEE);
        String formattedTotal = String.format("%.2f", total);

        // Set cart values as request attributes
        request.setAttribute("cart", cart);
        request.setAttribute("subtotal", formattedSubtotal);
        request.setAttribute("itemCount", itemCount);
        request.setAttribute("deliveryFee", formattedDeliveryFee);
        request.setAttribute("total", formattedTotal);

        // Get past orders from the DAO
        List<Map<String, Object>> orders = checkoutDAO.getPastOrders();

        // Sort orders based on the sort parameter
        if (orders != null && !orders.isEmpty()) {
            if ("asc".equals(sortOrder)) {
                orders.sort(Comparator.comparingDouble(o -> Double.parseDouble(o.get("total").toString())));
            } else if ("desc".equals(sortOrder)) {
                orders.sort((o1, o2) ->
                        Double.compare(
                                Double.parseDouble(o2.get("total").toString()),
                                Double.parseDouble(o1.get("total").toString())
                        )
                );
            }
        }

        // Set sorted orders as request attribute
        request.setAttribute("sortedOrders", orders);

        // Forward to the checkout.jsp page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout/checkout.jsp");
        dispatcher.forward(request, response);
    }
}
