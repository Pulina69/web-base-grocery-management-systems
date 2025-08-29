<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delivery - PNGO 248</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<nav class="main-nav">
    <div class="nav-container">
        <span class="nav-logo">PNGO 248</span>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/dashboard/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/oder/orders">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/product/addItem.jsp">Add Item</a></li>
            <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
        </ul>
    </div>
</nav>

<main class="container delivery-management-page">
    <h1 class="page-title">Delivery Management</h1>
    <div class="delivery-section">
        <h2 class="section-title">All Deliveries</h2>
        <div class="table-responsive">
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Username</th>
                        <th>Name</th>
                        <th>Phone</th>
                        <th>Address</th>
                        <th>Items</th>
                        <th>Total</th>
                        <th>Current Status</th>
                        <th>Change Status</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="d" items="${deliveries}">
                    <tr>
                        <td>${d[0]}</td>
                        <td>${d[1]}</td>
                        <td>${d[2]}</td>
                        <td>${d[3]}</td>
                        <td>${d[4]}</td>
                        <td>${d[5]}</td>
                        <td>Rs. ${d[6]}</td>
                        <td><span class="status-${fn:toLowerCase(d[7])}">${d[7]}</span></td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/delivery/delivery" class="status-update-form">
                                <input type="hidden" name="orderId" value="${d[0]}" />
                                <select name="status" class="form-select-small">
                                    <option value="new" ${d[7] == 'new' ? 'selected' : ''}>New</option>
                                    <option value="processing" ${d[7] == 'processing' ? 'selected' : ''}>Processing</option>
                                    <option value="shipped" ${d[7] == 'shipped' ? 'selected' : ''}>Shipped</option>
                                    <option value="out_for_delivery" ${d[7] == 'out_for_delivery' ? 'selected' : ''}>Out for Delivery</option>
                                    <option value="delivered" ${d[7] == 'delivered' ? 'selected' : ''}>Delivered</option>
                                    <option value="complete" ${d[7] == 'complete' ? 'selected' : ''}>Complete</option>
                                    <option value="cancelled" ${d[7] == 'cancelled' ? 'selected' : ''}>Cancelled</option>
                                </select>
                                <button type="submit" class="btn btn-primary btn-small">Update</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
</body>
</html>