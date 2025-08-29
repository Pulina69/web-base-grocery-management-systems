<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Analysis - PNGO 248</title>
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
                <li><a href="${pageContext.request.contextPath}/dashboard/deliveryanalize">delivery analize</a></li>
                <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <h1>Order Analysis</h1>
        <div class="chart-container">
            <h2 class="chart-title">Sold Items and Delivered Items</h2>
            <canvas id="orderChart"></canvas>
        </div>
    </div>

    <script>
        // Parse the JSON data from the server
        const orderData = JSON.parse('${orderAnalysisJson}');
        // Create the chart
        const ctx = document.getElementById('orderChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: orderData.labels,
                datasets: [
                    {
                        label: 'Sold Items',
                        data: orderData.soldData,
                        backgroundColor: 'rgba(54, 162, 235, 0.5)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    },
                    {
                        label: 'Delivered Items',
                        data: orderData.deliveredData,
                        backgroundColor: 'rgba(75, 192, 192, 0.5)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }
                ]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Number of Items'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Item Names'
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>