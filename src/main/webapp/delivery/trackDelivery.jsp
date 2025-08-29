<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Track Delivery - PNGO 248</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<nav class="main-nav">
    <div class="nav-container">
        <span class="nav-logo">PNGO 248</span>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/product/index">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/checkout/checkout.jsp">Checkout</a></li>
            <li><a href="${pageContext.request.contextPath}/oder/orderStatus">Orders</a></li>
                <li><a href="${pageContext.request.contextPath}/delivery/refund.jsp">Refund</a></li>
            <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
        </ul>
    </div>
</nav>
<div class="container track-delivery-page">
    <div class="delivery-grid">
        <div class="delivery-section">
            <h2 class="page-title">Delivery Status</h2>
            <div class="table-responsive">
                <table class="order-table">
                    <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Customer Name</th>
                        <th>Phone</th>
                        <th>Address</th>
                        <th>Items</th>
                        <th>Total</th>
                        <th>Payment Method</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${deliveries}" var="delivery">
                        <tr>
                            <td>${delivery[0]}</td>
                            <td>${delivery[1]}</td>
                            <td>${delivery[2]}</td>
                            <td>${delivery[3]}</td>
                            <td>${delivery[4]}</td>
                            <td>${delivery[5]}</td>
                            <td>Rs:${delivery[6]}</td>
                            <td>
                                <span class="status-${delivery[7].toLowerCase()}">${delivery[7]}</span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>