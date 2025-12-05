<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>注册 - 失物招领</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container" style="display: flex; align-items: center; justify-content: center; height: 80vh;">
    <div class="card">
        <h2>创建新账号</h2>
        <form action="register" method="post">
            <div class="form-group">
                <label>设置用户名</label>
                <input type="text" name="username" placeholder="建议使用学号或手机号" required>
            </div>
            <div class="form-group">
                <label>设置密码</label>
                <input type="password" name="password" placeholder="请输入安全密码" required>
            </div>
            <button type="submit" class="btn">注 册</button>
        </form>
        <p class="error-msg">${error}</p>
        <div class="text-center">
            已有账号？<a href="login.jsp">去登录</a>
        </div>
    </div>
</div>
</body>
</html>