package com.pgno248.sevlet;

import com.pgno248.dao.DeliveryDetailsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/checkout/deliverydetails")
public class DeliveryDetailsServlet extends HttpServlet {
    private DeliveryDetailsDAO deliveryDetailsDAO;
    @Override
    public void init() throws ServletException {
        String filePath = getServletContext().getRealPath("/WEB-INF/classes/deliverydetails.txt");
        deliveryDetailsDAO = new DeliveryDetailsDAO(filePath);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String items = request.getParameter("items");
        String total = request.getParameter("total");
        deliveryDetailsDAO.saveDetails(orderId, username, name, phone, address, items, total, "new");
        response.sendRedirect(request.getContextPath() + "/checkout/payment.jsp");
    }
}
