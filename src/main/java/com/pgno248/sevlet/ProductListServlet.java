package com.pgno248.sevlet;

import com.pgno248.dao.ProductListDAO;
import com.pgno248.model.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/product/productList")
public class ProductListServlet extends HttpServlet {
    private ProductListDAO productListDAO;

    @Override
    public void init() throws ServletException {
        productListDAO = new ProductListDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productListDAO.getAllProducts();
        request.setAttribute("products", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product/productList.jsp");
        dispatcher.forward(request, response);
    }
}
