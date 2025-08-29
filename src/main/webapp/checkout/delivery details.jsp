<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  // Get order details from session
  String orderId = (String) session.getAttribute("orderId");
  Double orderTotal = (Double) session.getAttribute("orderTotal");
  String orderItems = (String) session.getAttribute("orderItems");
  
  // If no order ID in session, generate a default
  if (orderId == null || orderId.isEmpty()) {
    orderId = "Unknown";
  }
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Delivery Details - PNGO 248</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
  </head>
  <body>
    <nav class="main-nav">
      <div class="nav-container">
        <span class="nav-logo">PNGO 248</span>
        <ul class="nav-links">
          <li><a href="${pageContext.request.contextPath}/product/index">Home</a></li>
        </ul>
      </div>
    </nav>

    <div class="container">
      <div class="delivery-page">
        <h1>Delivery Details</h1>
        
        <div class="order-summary">
          <h2>Order Summary</h2>
          <p><strong>Order ID:</strong> <%= orderId %></p>
          <p><strong>Total Amount:</strong> Rs. <%= String.format("%.2f", orderTotal != null ? orderTotal : 0.0) %></p>
        </div>
        
        <form class="delivery-form" action="${pageContext.request.contextPath}/checkout/deliverydetails" method="post">
          <div class="form-group">
            <label for="orderId">Order ID</label>
            <input type="text" id="orderId" name="orderId" class="form-input" value="<%= orderId %>" readonly>
          </div>

          <div class="form-group">
            <label for="name">Full Name</label>
            <input type="text" id="name" name="name" class="form-input" required>
          </div>

          <div class="form-group">
            <label for="phone">Phone Number</label>
            <input type="tel" id="phone" name="phone" class="form-input" required>
          </div>

          <div class="form-group">
            <label for="address">Delivery Address</label>
            <textarea id="address" name="address" class="form-input" required></textarea>
          </div>

          <input type="hidden" name="items" value="<%= orderItems != null ? orderItems : "" %>">
          <input type="hidden" name="total" value="<%= orderTotal != null ? orderTotal : 0.0 %>">
          <button type="submit" class="btn submit-button">Proceed to Payment</button>
        </form>
      </div>
    </div>
  </body>
</html>