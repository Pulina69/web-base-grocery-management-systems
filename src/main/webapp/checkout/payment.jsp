<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment - PNGO 248</title>
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
        <div class="payment-container">
            <h1 class="payment-title">Payment Details</h1>
            
            <% if(request.getAttribute("error") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <div class="order-summary">
                <h2>Order Summary</h2>
                <p><strong>Order Total:</strong> Rs. <%= session.getAttribute("orderTotal") != null ? session.getAttribute("orderTotal") : "0.00" %></p>
            </div>
            
            <form action="${pageContext.request.contextPath}/checkout/payment" method="post">
                <div class="form-group">
                    <label for="cardholder">Cardholder Name</label>
                    <input type="text" id="cardholder" name="cardholder" class="form-input" required>
                </div>
                
                <div class="form-group">
                    <label for="cardnumber">Card Number</label>
                    <input type="text" id="cardnumber" name="cardnumber" class="form-input" maxlength="19" required 
                           placeholder="XXXX XXXX XXXX XXXX">
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="expiry">Expiry Date</label>
                        <input type="text" id="expiry" name="expiry" class="form-input" maxlength="5" required placeholder="MM/YY">
                    </div>
                    
                    <div class="form-group">
                        <label for="cvv">CVV</label>
                        <input type="password" id="cvv" name="cvv" class="form-input" maxlength="4" required placeholder="XXX">
                    </div>
                </div>
                
                <button type="submit" class="submit-button">Complete Payment</button>
            </form>
            
        </div>
    </div>
    
    <script>
        // Format card number as user types (add spaces)
        document.getElementById('cardnumber').addEventListener('input', function(e) {
            const input = e.target;
            let value = input.value.replace(/\s+/g, '').replace(/[^0-9]/gi, '');
            let formattedValue = '';
            
            for (let i = 0; i < value.length; i++) {
                if (i > 0 && i % 4 === 0) {
                    formattedValue += ' ';
                }
                formattedValue += value[i];
            }
            
            input.value = formattedValue;
        });
        
        // Format expiry date as MM/YY
        document.getElementById('expiry').addEventListener('input', function(e) {
            const input = e.target;
            let value = input.value.replace(/\D/g, '');
            
            if (value.length > 2) {
                input.value = value.substring(0, 2) + '/' + value.substring(2, 4);
            } else {
                input.value = value;
            }
        });
    </script>
</body>
</html>