package com.pgno248.sevlet;

import com.pgno248.dao.PaymentDAO;
import com.pgno248.util.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/checkout/payment")
public class PaymentServlet extends HttpServlet {
    private PaymentDAO paymentDAO;
    @Override
    public void init() throws ServletException {
        // Initialize the PaymentDAO with the path to payment.txt
        String filePath = getServletContext().getRealPath("/WEB-INF/classes/payment.txt");
        paymentDAO = new PaymentDAO(filePath);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from request
        String cardholder = request.getParameter("cardholder");
        String cardNumber = request.getParameter("cardnumber").replaceAll("\\s+", ""); // Remove spaces
        String expiry = request.getParameter("expiry");
        String cvv = request.getParameter("cvv");

        // Get order info from session
        HttpSession session = request.getSession();
        String orderId = (String) session.getAttribute("orderId");
        Object totalObj = session.getAttribute("orderTotal");

        // Convert total to String if needed
        String total = totalObj != null ? totalObj.toString() : "0.00";


        // Validate that fields are not empty
        if (isEmpty(cardholder) || isEmpty(cardNumber) || isEmpty(expiry) || isEmpty(cvv)) {
            request.setAttribute("error", "All payment fields are required.");
            request.getRequestDispatcher("/checkout/payment.jsp").forward(request, response);
            return;
        }

        // Validate card number using AuthUtil
        if (!AuthUtil.validateCreditCard(cardNumber)) {
            request.setAttribute("error", "Invalid credit card number. Must be 13-19 digits.");
            request.getRequestDispatcher("/checkout/payment.jsp").forward(request, response);
            return;
        }

        // Validate CVV using AuthUtil
        if (!AuthUtil.validateCVV(cvv)) {
            request.setAttribute("error", "Invalid CVV. Must be 3 or 4 digits.");
            request.getRequestDispatcher("/checkout/payment.jsp").forward(request, response);
            return;
        }

        // Validate expiry date format (MM/YY)
        if (!expiry.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            request.setAttribute("error", "Invalid expiry date. Use MM/YY format.");
            request.getRequestDispatcher("/checkout/payment.jsp").forward(request, response);
            return;
        }

        try {
            // Save payment information
            String username = (String) session.getAttribute("username");
            boolean paymentSuccess = paymentDAO.savePayment(orderId, username, cardholder, cardNumber, total);

            if (paymentSuccess) {
                // Clear cart and payment-related session data
                session.removeAttribute("cart");

                // Set success message and keep order ID for confirmation page
                session.setAttribute("paymentSuccess", "Payment processed successfully!");
                session.setAttribute("confirmedOrderId", orderId);
                session.setAttribute("confirmedTotal", total);

                // Redirect to confirmation page
                response.sendRedirect(request.getContextPath() + "/checkout/confirmation.jsp");
            } else {
                // Payment failed
                request.setAttribute("error", "Payment processing failed. Please try again.");
                request.getRequestDispatcher("/checkout/payment.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error processing payment: " + e.getMessage());
            request.getRequestDispatcher("/checkout/payment.jsp").forward(request, response);
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
