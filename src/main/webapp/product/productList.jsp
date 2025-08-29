<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List - PNGO 248</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <nav class="main-nav">
        <div class="nav-container">
            <span class="nav-logo">PNGO 248</span>
            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/user/profile">Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/dashboard/itemanalize">item analize</a></li>
                <li><a href="${pageContext.request.contextPath}/dashboard/deliveryanalize">delivery analize</a></li>
                <li><a href="${pageContext.request.contextPath}/oder/oderanalize">Order Analysis</a></li>
                <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
            </ul>
        </div>
    </nav>    
    <main class="container product-list-page">
        <h1 class="page-title">Product List</h1>
        <div class="table-responsive">
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Item ID</th>
                        <th>Item Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                    </tr>
                </thead>                <tbody>
                    <c:choose>
                        <c:when test="${not empty products}">
                            <c:forEach var="product" items="${products}">
                                <tr>
                                    <td>${product.id}</td>
                                    <td>${product.name}</td>
                                    <td>Rs: ${product.price}</td>
                                    <td>${product.quantity}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr><td colspan="4">No products available</td></tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>