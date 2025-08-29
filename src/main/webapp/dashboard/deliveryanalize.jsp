<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delivery Analysis - PNGO 248</title>
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
                <li><a href="${pageContext.request.contextPath}/dashboard/itemanalize">item analize</a></li>
                <li><a href="${pageContext.request.contextPath}/oder/oderanalize">Order Analysis</a></li>
                <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <h1>Delivery Status Analysis</h1>
        <div class="chart-container">
            <h2 class="chart-title">Delivery Status Overview</h2>
            <canvas id="deliveryStatusChart"></canvas>
        </div>
    </div>
    <script>
        // Parse the JSON data from the server
        const deliveryData = JSON.parse('${deliveryStatusJson}');
        const ctx = document.getElementById('deliveryStatusChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: deliveryData.labels,
                datasets: [{
                    label: 'Number of Deliveries',
                    data: deliveryData.data,
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.7)',
                        'rgba(255, 206, 86, 0.7)',
                        'rgba(255, 99, 132, 0.7)'
                    ],
                    borderColor: [
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(255, 99, 132, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Number of Deliveries'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Status'
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>