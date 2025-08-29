<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>PNGO 248 - Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <nav class="main-nav">
      <div class="nav-container">
        <span class="nav-logo">PNGO 248</span>
        <ul class="nav-links">
          <li><a href="${pageContext.request.contextPath}/checkout/checkout.jsp">Checkout</a></li>
          <li><a href="${pageContext.request.contextPath}/oder/orderStatus">Orders</a></li>
          <li><a href="${pageContext.request.contextPath}/delivery/trackDelivery">Delivery</a></li>
            <li><a href="${pageContext.request.contextPath}/delivery/refund.jsp">Refund</a></li>
          <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
        </ul>
      </div>    </nav>    <main class="container">
        <c:if test="${not empty error}">
            <div class="error-message">
                <p>${error}</p>
            </div>
        </c:if>

        <div>
            <form method="get" style="margin:0;">
                <select name="priceFilter" onchange="this.form.submit()">
                    <option value="asc" <c:if test="${param.priceFilter == 'asc'}">selected</c:if>>Price: Low to High</option>
                    <option value="desc" <c:if test="${param.priceFilter == 'desc'}">selected</c:if>>Price: High to Low</option>
                </select>
            </form>
        </div>

        <div class="tab-navigation">
            <div id="featured-tab-btn" class="tab active" onclick="switchTab('featured')">Featured Products</div>
            <div id="all-products-tab-btn" class="tab" onclick="switchTab('all-products')">All Products</div>
        </div>

        <div id="featured-tab" class="active">
            <section class="featured-products">
                <h2>Featured Products</h2>
                <div class="product-boxes">
                    <c:choose>
                        <c:when test="${empty products}">
                            <div>No products available at the moment.</div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="product" items="${products}" begin="0" end="4">
                                <div class="product-box">
                                    <h3>${product.name}</h3>
                                    <div class="price">Rs. ${product.price}</div>
                                    <div class="quantity">Available: ${product.quantity}</div>
                                    <button class="add-to-cart-btn" 
                                            data-id="${product.id}"
                                            data-name="${product.name}"
                                            data-price="${product.price}">
                                        Add to Cart
                                    </button>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>
        </div>

        <div id="all-products-tab">
            <section class="all-products">
                <h2>Product List</h2>
                <div class="product-boxes">
                    <c:choose>
                        <c:when test="${empty products}">
                            <div>No products available at the moment.</div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="product" items="${products}">
                                <div class="product-box">
                                    <h3>${product.name}</h3>
                                    <div class="price">Rs. ${product.price}</div>
                                    <div class="quantity">Available: ${product.quantity}</div>
                                    <button class="add-to-cart-btn" 
                                            data-id="${product.id}"
                                            data-name="${product.name}"
                                            data-price="${product.price}">
                                        Add to Cart
                                    </button>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>
        </div>    </main>    <!-- Cart Form (Hidden) -->
    <form id="cart-form" action="${pageContext.request.contextPath}/product/index" method="post" style="display: none;">
        <input type="hidden" name="productId" id="selected-product-id">
        <input type="hidden" name="productName" id="selected-product-name">
        <input type="hidden" name="productPrice" id="selected-product-price">
        <input type="hidden" name="productQuantity" id="selected-product-quantity" value="1">
    </form>

    <script>
        // Add event listeners to all "Add to Cart" buttons
        document.addEventListener('DOMContentLoaded', function() {
            const addToCartButtons = document.querySelectorAll('.add-to-cart-btn');
            
            addToCartButtons.forEach(function(button) {
                button.addEventListener('click', function() {
                    // Get product data from data attributes
                    const productId = this.getAttribute('data-id');
                    const productName = this.getAttribute('data-name');
                    const productPrice = this.getAttribute('data-price');
                    
                    // Set values in the hidden form
                    document.getElementById('selected-product-id').value = productId;
                    document.getElementById('selected-product-name').value = productName;
                    document.getElementById('selected-product-price').value = productPrice;
                    
                    // Submit the form to add to cart, but do not redirect to checkout
                    document.getElementById('cart-form').submit();
                    // No redirect or auto load of checkout page
                });
            });
        });

        // Tab switching functionality
        function switchTab(tabName) {
            // Hide all tabs and remove active class
            document.getElementById('featured-tab').classList.remove('active');
            document.getElementById('all-products-tab').classList.remove('active');
            document.getElementById('featured-tab-btn').classList.remove('active');
            document.getElementById('all-products-tab-btn').classList.remove('active');
            
            // Show selected tab and add active class
            document.getElementById(tabName + '-tab').classList.add('active');
            document.getElementById(tabName + '-tab-btn').classList.add('active');
        }

        // --- Queue class implementation in JS ---
        class Queue {
            constructor() { this.items = []; }
            enqueue(item) { this.items.push(item); }
            dequeue() { return this.items.shift(); }
            isEmpty() { return this.items.length === 0; }
            size() { return this.items.length; }
            toArray() { return this.items.slice(); }
        }

        // --- MergeSort for price (imitating MergeShort.java) ---
        function mergeSort(arr) {
            if (arr.length <= 1) return arr;
            const mid = Math.floor(arr.length / 2);
            const left = mergeSort(arr.slice(0, mid));
            const right = mergeSort(arr.slice(mid));
            return merge(left, right);
        }
        function merge(left, right) {
            let result = [], i = 0, j = 0;
            while (i < left.length && j < right.length) {
                if (left[i].price <= right[j].price) {
                    result.push(left[i++]);
                } else {
                    result.push(right[j++]);
                }
            }
            return result.concat(left.slice(i)).concat(right.slice(j));
        }

        // --- Product filtering and rendering ---
        document.addEventListener('DOMContentLoaded', function() {
            // Collect all products into a JS array
            const allProductBoxes = Array.from(document.querySelectorAll('#all-products-tab .product-box'));
            const allProducts = allProductBoxes.map(box => ({
                name: box.querySelector('h3').textContent,
                price: parseFloat(box.querySelector('.price').textContent.replace(/[^\d.]/g, '')),
                quantity: parseInt(box.querySelector('.quantity').textContent.replace(/[^\d]/g, '')),
                box: box
            }));
        });
    </script>
</body>
</html>