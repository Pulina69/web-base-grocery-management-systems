<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Users - PNGO 248</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <nav class="main-nav">
        <div class="nav-container">
            <span class="nav-logo">PNGO 248</span>
            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/product/productList">Product List</a></li>
                <li><a href="${pageContext.request.contextPath}/dashboard/itemanalize">item analize</a></li>
                <li><a href="${pageContext.request.contextPath}/dashboard/deliveryanalize">delivery analize</a></li>
                <li><a href="${pageContext.request.contextPath}/oder/oderanalize">Order Analysis</a></li>
                <li><a href="${pageContext.request.contextPath}/user/login.jsp">Logout</a></li>
            </ul>
        </div>
    </nav>
    <div class="container profile-page">
        <div class="profile-container">
            <h1 class="page-title">All Users</h1>
            <div class="table-responsive">
                <table class="user-table order-table">
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Change Status</th>
                        </tr>
                    </thead>
                    <tbody>                        <c:forEach var="user" items="${users}" varStatus="loop">
                            <c:choose>
                                <c:when test="${loop.index % 2 == 0}">
                                    <tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                </c:otherwise>
                            </c:choose>
                                <td>${user.username}</td>
                                <td>${user.email}</td>
                                <td>${user.phone}</td>
                                <td>${user.role}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.status == 'yes'}">
                                            <span class="status-yes">yes</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-no">no</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/user/profile">
                                        <input type="hidden" name="username" value="${user.username}" />
                                        <c:choose>
                                            <c:when test="${user.status == 'yes'}">
                                                <input type="hidden" name="status" value="no" />
                                                <button type="submit" class="btn btn-no">Set to no</button>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="hidden" name="status" value="yes" />
                                                <button type="submit" class="btn btn-yes">Set to yes</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>