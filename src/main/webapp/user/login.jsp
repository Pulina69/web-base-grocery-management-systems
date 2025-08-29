<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - PNGO 248</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
    <nav class="main-nav">
        <div class="nav-container">
            <span class="nav-logo">PNGO 248</span>
            <ul class="nav-links">
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="form-container">
            <h1>Login</h1>
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ${error}
                </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" id="username" name="username" class="form-input" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <div class="password-input-container">
                        <input type="password" id="password" name="password" class="form-input" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="role">Role</label>
                    <select id="role" name="role" class="form-input" required>
                        <option value="CUSTOMER">Customer</option>
                        <option value="EMPLOYEE">Employee</option>
                        <option value="ADMIN">Admin</option>
                    </select>
                </div>
                <button type="submit" class="btn">Login</button>
            </form>
            <p class="form-footer">
                Don't have an account? <a href="${pageContext.request.contextPath}/user/register.jsp">Register here</a>
            </p>
        </div>
    </div>
</body>
</html>