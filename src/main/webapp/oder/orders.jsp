<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Orders - PNGO 248</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
  <nav class="main-nav">
    <div class="nav-container">
      <span class="nav-logo">PNGO 248</span>
      <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/dashboard/dashboard">Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/delivery/delivery">Delivery</a></li>
        <li><a href="${pageContext.request.contextPath}/product/addItem.jsp">Add Item</a></li>
        <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
      </ul>
    </div>
  </nav>

  <main class="container">
    <h1>Order Management</h1>
    <div class="order-section">
      <table class="order-table">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Customer</th>
            <th>Items</th>
            <th>Total</th>
            <th>Status</th>
          </tr>
        </thead>        <tbody>
          <c:choose>
            <c:when test="${empty orders}">
              <tr>
                <td colspan="5" class="text-center">No orders found</td>
              </tr>
            </c:when>
            <c:otherwise>
              <c:forEach items="${orders}" var="order">
                <tr>
                  <td>${order.orderId}</td>
                  <td>${order.customer}</td>
                  <td>${order.items}</td>
                  <td>Rs. ${order.total}</td>
                  <td>${order.status}</td>
                </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>
    </div>
  </main>
</body>
</html>