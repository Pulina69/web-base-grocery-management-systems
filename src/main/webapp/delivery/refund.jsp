<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Refund - PNGO 248</title>
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
            <li><a href="${pageContext.request.contextPath}/delivery/trackDelivery">delivers</a></li>
            <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>

        </ul>
    </div>
</nav>

<div class="container">    <h1>Request Refund</h1>

    <c:if test="${sessionScope.refundSuccess}">
    <div class="success-message">
        Your refund request has been submitted successfully!
    </div>
    <c:remove var="refundSuccess" scope="session" />
    </c:if>

    <form class="refund-form" action="${pageContext.request.contextPath}/delivery/refund" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="orderId">Order ID:</label>
            <input type="text" id="orderId" name="orderId" required>
        </div>

        <div class="form-group">
            <label for="reason">Reason for Refund:</label>
            <textarea id="reason" name="reason" required></textarea>
        </div>        <div class="form-group">
            <label for="photo">Photo (Optional):</label>
            <input type="file" id="photo" name="photo" accept="image/*">
        </div>
        
        <!-- Hidden field for default status -->
        <input type="hidden" name="status" value="pending">

        <button type="submit" class="submit-btn">Submit Refund Request</button>
    </form>
</div>
</body>
</html>