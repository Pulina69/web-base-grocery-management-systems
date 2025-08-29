<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - PNGO 248</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
  <nav class="main-nav">
    <div class="nav-container">
      <span class="nav-logo">PNGO 248</span>
      <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/oder/orders">Orders</a></li>
        <li><a href="${pageContext.request.contextPath}/delivery/delivery">Delivery</a></li>
        <li><a href="${pageContext.request.contextPath}/product/addItem.jsp">Add Item</a></li>
        <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
      </ul>
    </div>
  </nav>
  <main class="container dashboard-page">
    <h1 class="page-title">STAFF Dashboard</h1>
    <div class="dashboard-grid">
      <div class="dashboard-card">
        <h3>Total Earnings</h3>
        <p class="highlight">Rs. ${totalEarnings}</p>
      </div>
      <div class="dashboard-card">
        <h3>Total Customers</h3>
        <p class="highlight">${totalCustomers}</p>
      </div>
      <div class="dashboard-card">
        <h3>Total Employees</h3>
        <p class="highlight">${totalEmployees}</p>
      </div>
      <div class="dashboard-card">
        <h3>Total Refunds</h3>
        <p class="highlight">${totalRefunds}</p>
      </div>
    </div>    <!-- Refund Requests Section -->
    <section class="refund-section">
      <h2>Refund Requests</h2>
      
      <!-- Status update notifications -->
      <c:if test="${not empty sessionScope.statusUpdateSuccess}">
        <div class="alert alert-success">
          ${sessionScope.statusUpdateSuccess}
          <c:remove var="statusUpdateSuccess" scope="session" />
        </div>
      </c:if>
      
      <c:if test="${not empty sessionScope.statusUpdateError}">
        <div class="alert alert-error">
          ${sessionScope.statusUpdateError}
          <c:remove var="statusUpdateError" scope="session" />
        </div>
      </c:if>
      
      <div class="table-responsive">
        <table class="refund-table">
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Username</th>
              <th>Reason</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${empty refunds}">
                <tr>
                  <td colspan="5" class="text-center">No refund requests found</td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:forEach items="${refunds}" var="refund">
                  <tr>
                    <td>${refund.orderId}</td>
                    <td>${refund.username}</td>
                    <td class="reason-cell">
                      <span class="reason-text">${refund.reason}</span>
                      <span class="reason-tooltip">${refund.reason}</span>
                    </td>
                    <td class="status-cell ${refund.status}">
                      ${refund.status}
                    </td>
                    <td>
                      <form action="${pageContext.request.contextPath}/dashboard/updateRefundStatus" method="post">
                        <input type="hidden" name="orderId" value="${refund.orderId}">
                        <select name="status" class="status-select">
                          <option value="pending" ${refund.status == 'pending' ? 'selected' : ''}>Pending</option>
                          <option value="approved" ${refund.status == 'approved' ? 'selected' : ''}>Approved</option>
                          <option value="rejected" ${refund.status == 'rejected' ? 'selected' : ''}>Rejected</option>
                        </select>
                        <button type="submit" class="update-btn">Update</button>
                      </form>
                    </td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </tbody>
        </table>
      </div>
    </section>
  </main>
  
  <style>
    .refund-section {
      margin-top: 2rem;
      padding: 1rem;
      background-color: #f9f9f9;
      border-radius: 5px;
    }
    
    .refund-section h2 {
      margin-bottom: 1rem;
      color: #333;
    }
    
    .table-responsive {
      overflow-x: auto;
    }
    
    .refund-table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 1rem;
    }
    
    .refund-table th,
    .refund-table td {
      padding: 0.8rem;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    
    .refund-table th {
      background-color: #f2f2f2;
      font-weight: 600;
    }
    
    .refund-table tr:hover {
      background-color: #f5f5f5;
    }
    
    .reason-cell {
      position: relative;
      max-width: 200px;
    }
    
    .reason-text {
      display: block;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      max-width: 200px;
    }
    
    .reason-tooltip {
      display: none;
      position: absolute;
      background-color: #333;
      color: #fff;
      padding: 8px 12px;
      border-radius: 4px;
      z-index: 10;
      width: 250px;
      top: 100%;
      left: 0;
      white-space: normal;
    }
    
    .reason-cell:hover .reason-tooltip {
      display: block;
    }
    
    .status-cell {
      font-weight: 600;
    }
    
    .status-cell.pending {
      color: #f39c12;
    }
    
    .status-cell.approved {
      color: #2ecc71;
    }
    
    .status-cell.rejected {
      color: #e74c3c;
    }
    
    .status-select {
      padding: 0.5rem;
      border: 1px solid #ddd;
      border-radius: 4px;
      margin-right: 0.5rem;
    }
    
    .update-btn {
      background-color: #3498db;
      color: white;
      border: none;
      padding: 0.5rem 0.75rem;
      border-radius: 4px;
      cursor: pointer;
      font-size: 0.9rem;
    }
      .update-btn:hover {
      background-color: #2980b9;
    }
    
    .alert {
      padding: 1rem;
      margin-bottom: 1rem;
      border-radius: 4px;
      font-weight: 500;
    }
    
    .alert-success {
      background-color: #d4edda;
      color: #155724;
      border: 1px solid #c3e6cb;
    }
    
    .alert-error {
      background-color: #f8d7da;
      color: #721c24;
      border: 1px solid #f5c6cb;
    }
  </style>
</body>
</html>