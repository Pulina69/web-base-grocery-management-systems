<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - PNGO 248</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <nav class="main-nav">
        <div class="nav-container">
            <span class="nav-logo">PNGO 248</span>
        </div>
    </nav>

    <div class="container">
        <div class="form-container">
            <h1>Register</h1>
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ${error}
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.success}">
                <div class="alert alert-success">
                    ${sessionScope.success}
                </div>
                <c:remove var="success" scope="session" />
            </c:if>
            <form action="${pageContext.request.contextPath}/user/register" method="post">
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
                    <label for="confirmPassword">Confirm Password</label>
                    <div class="password-input-container">
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" class="form-input" required>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone" class="form-input" required>
                </div>
                <div class="form-group">
                    <label for="role">Role</label>
                    <select id="role" name="role" class="form-input" required>
                        <option value="CUSTOMER">Customer</option>
                        <option value="EMPLOYEE">Employee</option>
                    </select>
                </div>
                <button type="submit" class="btn">Register</button>
            </form>
            <p class="form-footer">
                Already have an account? <a href="${pageContext.request.contextPath}/user/login.jsp">Login here</a>
            </p>
        </div>
    </div>
</body>
</html>