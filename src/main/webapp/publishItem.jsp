<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user}">
  <c:redirect url="login.jsp" />
</c:if>
<!DOCTYPE html>
<html>
<head>
  <title>发布信息</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <div class="brand"><a href="index.jsp">Lost & Found</a></div>
  <div class="nav-links">
    <a href="index.jsp">返回首页</a>
  </div>
</nav>

<div class="container">
  <div class="card" style="max-width: 600px;">
    <h2>📝 发布失物招领</h2>
    <form action="publishItem" method="post">
      <div class="form-group">
        <label>物品名称</label>
        <input type="text" name="name" placeholder="例如：黑色皮钱包" required>
      </div>
      <div class="form-group">
        <label>捡到日期</label>
        <input type="date" name="foundDate" required>
      </div>
      <div class="form-group">
        <label>捡到地点</label>
        <input type="text" name="location" placeholder="例如：图书馆二楼阅览室" required>
      </div>
      <div class="form-group">
        <label>详细描述</label>
        <textarea name="description" placeholder="请详细描述物品特征，方便失主确认..."></textarea>
      </div>
      <div style="display: flex; gap: 10px;">
        <a href="index.jsp" class="btn btn-secondary" style="text-decoration: none; line-height: 20px;">取消</a>
        <button type="submit" class="btn">立即发布</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>