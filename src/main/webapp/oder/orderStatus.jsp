<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Status</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <nav class="main-nav">
        <div class="nav-container">
            <span class="nav-logo">PNGO 248</span>
            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/product/index">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/checkout/checkout.jsp">Checkout</a></li>
                <li><a href="${pageContext.request.contextPath}/delivery/trackDelivery">delivers</a></li>
                <li><a href="${pageContext.request.contextPath}/delivery/refund.jsp">Refund</a></li>
                <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
            </ul>
        </div>
    </nav>
    <div class="container">        <div class="order-status-page"> <%-- Added wrapper div --%>
            <h1 class="page-title">Order Status</h1> <%-- Added class for styling --%>
            <div class="table-responsive"> <%-- Added for responsiveness --%>
                <table class="order-table">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Item Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                    </thead>                    <tbody>
                    <c:choose>
                        <c:when test="${not empty userOrders}">
                            <c:forEach var="order" items="${userOrders}">
                                <tr>
                                    <td>${order[0]}</td>
                                    <td>${order[2]}</td>
                                    <td>${order[4]}</td>
                                    <td>Rs. ${order[3]}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4">No orders found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>