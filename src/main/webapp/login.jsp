<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录 - 失物招领</title>
    <!-- 引入外部 CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container" style="display: flex; align-items: center; justify-content: center; height: 80vh;">
    <div class="card">
        <h2>欢迎回来</h2>
        <form action="login" method="post">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" name="username" placeholder="请输入您的账号" required>
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" placeholder="请输入您的密码" required>
            </div>
            <button type="submit" class="btn">登 录</button>
        </form>
        <p class="error-msg">${error}</p>
        <div class="text-center">
            还没有账号？<a href="register.jsp">立即注册</a>
        </div>
    </div>
</div>
</body>
</html>