<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation - PNGO 248</title>
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
        <div class="confirmation-container">
            <div class="success-icon">✓</div>
            <h1 class="success-title">Order Confirmed!</h1>
            <p>Thank you for your purchase. Your order has been placed successfully.</p>
            
            <div class="order-details">
                <h2>Order Summary</h2>
                <p><strong>Order ID:</strong> <%= session.getAttribute("confirmedOrderId") != null ? session.getAttribute("confirmedOrderId") : "Generated at checkout" %></p>
                <p><strong>Amount Paid:</strong> Rs. <%= session.getAttribute("confirmedTotal") != null ? session.getAttribute("confirmedTotal") : "0.00" %></p>
                <p><strong>Date:</strong> <%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) %></p>
                <p><strong>Status:</strong> <span class="order-status-paid">Paid</span></p>
            </div>
            
            <p>A confirmation email will be sent to your registered email address.</p>
            
            <div class="btn-container">
                <a href="${pageContext.request.contextPath}/product/index" class="btn">Continue Shopping</a>
            </div>
        </div>
    </div>
</body>
</html>
