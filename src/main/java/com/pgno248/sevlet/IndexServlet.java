package com.pgno248.sevlet;

import com.pgno248.dao.ProductDAO;
import com.pgno248.model.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/product/index")
public class IndexServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDAO.getAllProducts();
        String priceFilter = request.getParameter("priceFilter");
        if (priceFilter != null && !priceFilter.isEmpty() && products != null && !products.isEmpty()) {
            if ("asc".equals(priceFilter)) {
                products.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
            } else if ("desc".equals(priceFilter)) {
                products.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
            } else if ("merge".equals(priceFilter)) {
                // Use MergeShort to merge-sort the product prices
                products = mergeSortProducts(products);
            }
        }
        request.setAttribute("products", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product/index.jsp");
        dispatcher.forward(request, response);
    }

    private List<Product> mergeSortProducts(List<Product> products) {
        if (products.size() <= 1) return products;
        int mid = products.size() / 2;
        List<Product> left = mergeSortProducts(products.subList(0, mid));
        List<Product> right = mergeSortProducts(products.subList(mid, products.size()));
        return merge(left, right);
    }

    private List<Product> merge(List<Product> left, List<Product> right) {
        List<Product> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getPrice() <= right.get(j).getPrice()) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));
        return merged;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle Add to Cart button
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("productPrice");
        String productQuantity = request.getParameter("productQuantity");

        // Store cart in session (simple cart: one product at a time)
        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(new Product(productId, productName, Double.parseDouble(productPrice), Integer.parseInt(productQuantity)));
        session.setAttribute("cart", cart);

        // Redirect to checkout page
        response.sendRedirect(request.getContextPath() + "/checkout/checkout.jsp");
    }
}
