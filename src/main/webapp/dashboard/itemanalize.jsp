<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Item Analysis - PNGO 248</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <nav class="main-nav">
        <div class="nav-container">
            <span class="nav-logo">PNGO 248</span>
            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/user/profile">profile</a></li>
                <li><a href="${pageContext.request.contextPath}/product/productList">Product List</a></li>
                <li><a href="${pageContext.request.contextPath}/dashboard/deliveryanalize">delivery analize</a></li>
                <li><a href="${pageContext.request.contextPath}/oder/oderanalize">Order Analysis</a></li>
                <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <h1>Item Analysis</h1>
        <div class="chart-container">
            <h2 class="chart-title">Current Stock Levels</h2>
            <canvas id="stockChart"></canvas>
        </div>
    </div>

    <script>
        // Parse the JSON data from the server
        const itemData = JSON.parse('${itemAnalysisJson}');
        // Create stock levels chart
        new Chart(document.getElementById('stockChart'), {
            type: 'bar',
            data: {
                labels: itemData.labels,
                datasets: [{
                    label: 'Current Stock',
                    data: itemData.quantities,
                    backgroundColor: 'rgba(54, 162, 235, 0.5)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Quantity'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Product Names'
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>