<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add Item - PNGO 248</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
  <nav class="main-nav">
    <div class="nav-container">
      <span class="nav-logo">PNGO 248</span>
      <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/dashboard/dashboard">Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/delivery/delivery">Delivery</a></li>
        <li><a href="${pageContext.request.contextPath}/oder/orders">Orders</a></li>
        <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
      </ul>
    </div>
  </nav>

  <main class="container add-item-page">
    <h1 class="page-title">Add New Item</h1>
    <div class="form-container add-item-form-container">
      <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error">
          <%= request.getAttribute("error") %>
        </div>
      <% } %>
      <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success">
          <%= request.getAttribute("success") %>
        </div>
      <% } %>
      <form action="${pageContext.request.contextPath}/product/addItemServlet" method="post" class="item-form">
        <div class="form-group">
          <label for="itemId" class="form-label">Item ID:</label>
          <input type="text" id="itemId" name="itemId" class="form-input" required>
        </div>

        <div class="form-group">
          <label for="itemName" class="form-label">Item Name:</label>
          <input type="text" id="itemName" name="itemName" class="form-input" required>
        </div>
        <div class="form-row">
            <div class="form-group">
              <label for="price" class="form-label">Price:</label>
              <input type="number" id="price" name="price" step="0.01" min="0" class="form-input" required>
            </div>

            <div class="form-group">
              <label for="quantity" class="form-label">Quantity:</label>
              <input type="number" id="quantity" name="quantity" min="0" class="form-input" required>
            </div>
        </div>

        <div class="form-group">
          <button type="submit" class="btn btn-primary submit-button">Add Item</button>
        </div>
      </form>
    </div>
  </main>
</body>
</html>
