<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.pgno248.model.Product, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Checkout - PNGO 248</title>
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

  <main class="container">    <h1>Checkout</h1>
    
    <c:if test="${not empty sessionScope.cartError}">
      <div class="error-message">
        ${sessionScope.cartError}
      </div>
      <c:remove var="cartError" scope="session" />
    </c:if>
    <div class="checkout-container">      
      <form action="${pageContext.request.contextPath}/checkout/process" method="post" id="checkout-form">        <input type="hidden" name="orderId" value="${orderId}">
        <input type="hidden" name="items" id="items-input">
        <input type="hidden" name="total" id="total-input" value="${total}">
        
        <div class="cart-table">
          <table>
            <thead>
              <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
                <th>Action</th>
              </tr>
            </thead>              <tbody id="cart-items">
              <c:choose>
                <c:when test="${not empty cart}">
                  <c:forEach var="item" items="${cart}">
                    <tr class="cart-item">
                      <td>${item.name}</td>
                      <td class="item-price">Rs. ${String.format("%.2f", item.price)}</td>
                      <td>
                        <input type="number" class="quantity-input" data-id="${item.id}" 
                               value="${item.quantity}" min="1" style="width: 60px;">
                      </td>
                      <td class="item-total">Rs. ${String.format("%.2f", item.price * item.quantity)}</td>
                      <td>
                        <button type="button" class="btn btn-small btn-danger remove-item" 
                                data-id="${item.id}">Remove</button>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr class="no-items">
                    <td colspan="5" style="text-align: center;">Your cart is empty. <a href="${pageContext.request.contextPath}/product/index">Continue shopping</a></td>
                  </tr>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>        
        <div class="checkout-summary">
          <%-- Cart values are already calculated at the top of the page --%>          <div class="summary-row">
            <span>Items:</span>
            <span id="total-items">${itemCount}</span>
          </div>          <div class="summary-row">
            <span>Subtotal:</span>
            <span id="subtotal">Rs. 234.00</span>
          </div>
          <div class="summary-row">
            <span>Delivery:</span>
            <span>Rs. 200.00</span>
          </div>
          <div class="summary-row total">
            <span>Total:</span>
            <span id="total">Rs. 434.00</span>
          </div>
        </div>
        <button type="submit" class="btn btn-large">Proceed to Delivery Details</button>
      </form>
    </div>  
  </main>
  <!-- Hidden forms for cart actions -->
  <form id="remove-item-form" action="${pageContext.request.contextPath}/checkout/cart/update" method="post" style="display: none;">
    <input type="hidden" name="action" value="remove">
    <input type="hidden" name="productId" id="remove-product-id">
  </form>
  
  <form id="update-quantity-form" action="${pageContext.request.contextPath}/checkout/cart/update" method="post" style="display: none;">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="productId" id="update-product-id">
    <input type="hidden" name="quantity" id="update-quantity">
  </form>  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Function to recalculate cart totals
      function updateCartTotals() {
        let subtotal = 0;
        let itemCount = 0;
        const DELIVERY_FEE = 200.00;
        
        // Get all cart items
        const cartItems = document.querySelectorAll('.cart-item');
        
        cartItems.forEach(function(item) {
          // Get price value (remove currency symbol and convert to number)
          const priceText = item.querySelector('.item-price').textContent.replace('Rs. ', '');
          const price = parseFloat(priceText);
          
          // Get quantity
          const quantity = parseInt(item.querySelector('.quantity-input').value);
          
          // Calculate item total
          const itemTotal = price * quantity;
          
          // Update item total in the table
          item.querySelector('.item-total').textContent = 'Rs. ' + itemTotal.toFixed(2);
          
          // Add to subtotal
          subtotal += itemTotal;
          
          // Count items
          itemCount += quantity;
        });
        
        // Update summary - Make sure we're updating the text content
        document.getElementById('total-items').textContent = itemCount;
        
        // Set default values if cart is empty
        if (cartItems.length === 0) {
          subtotal = 0;
          itemCount = 0;
        }
        
        document.getElementById('subtotal').textContent = 'Rs. ' + subtotal.toFixed(2);
        
        // Calculate and update total
        const total = subtotal + DELIVERY_FEE;
        document.getElementById('total').textContent = 'Rs. ' + total.toFixed(2);
        
        return { subtotal, itemCount, total };
      }

      // Handle Remove buttons
      const removeButtons = document.querySelectorAll('.remove-item');
      removeButtons.forEach(function(button) {
        button.addEventListener('click', function() {
          const productId = this.getAttribute('data-id');
          if (confirm('Are you sure you want to remove this item?')) {
            // Remove the item from the DOM
            const row = this.closest('.cart-item');
            row.parentNode.removeChild(row);
            
            // Update totals
            updateCartTotals();
            
            // If using the server-side form, submit it
            document.getElementById('remove-product-id').value = productId;
            document.getElementById('remove-item-form').submit();
          }
        });
      });
      
      // Handle Quantity changes
      const quantityInputs = document.querySelectorAll('.quantity-input');
      quantityInputs.forEach(function(input) {
        input.addEventListener('change', function() {
          const productId = this.getAttribute('data-id');
          const quantity = this.value;
          
          // Update the cart totals first for immediate feedback
          updateCartTotals();
          
          // Then submit the form for server-side update
          document.getElementById('update-product-id').value = productId;
          document.getElementById('update-quantity').value = quantity;
          document.getElementById('update-quantity-form').submit();
        });
      });
      
      // Prepare items data for checkout submission
      document.getElementById('checkout-form').addEventListener('submit', function(e) {
        const cartItems = document.querySelectorAll('.cart-item');
        if (cartItems.length === 0) {
          e.preventDefault();
          alert('Your cart is empty. Please add items before checkout.');
          return;
        }
        
        // Format cart items as string - using a simple format that's easy to parse
        let itemsList = '';
        cartItems.forEach(function(item, index) {
          const name = item.cells[0].textContent;
          const quantity = item.querySelector('.quantity-input').value;
          
          // Simple format: Product Name (xQuantity)
          itemsList += name + ' (x' + quantity + ')';
          if (index < cartItems.length - 1) {
            itemsList += ', ';
          }        
        });          
        document.getElementById('items-input').value = itemsList;
        console.log('Items for checkout: ' + itemsList);
        
        // Get the total from the DOM element that shows the total
        const totalText = document.getElementById('total').textContent.replace('Rs. ', '');
        const totalValue = parseFloat(totalText);
        document.getElementById('total-input').value = totalValue;
        console.log('Total for checkout: ' + totalValue);
        
        // Confirm checkout
        return confirm('Proceed to delivery details with these items?');
      });
    });
  </script>
</body>
</html>