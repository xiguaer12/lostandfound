<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- 权限检查 --%>
<c:if test="${empty sessionScope.user}">
  <c:redirect url="login.jsp" />
</c:if>
<!DOCTYPE html>
<html>
<head>
  <title>编辑物品信息</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <div class="brand"><a href="home">Lost & Found</a></div>
  <div class="nav-links"><a href="home">返回首页</a></div>
</nav>

<div class="container">
  <div class="card" style="max-width: 600px;">
    <h2>✏️ 编辑物品信息</h2>

    <form action="updateItem" method="post">
      <%-- 隐藏域：传递物品ID，否则后台不知道修改哪条 --%>
      <input type="hidden" name="id" value="${item.id}">

      <div class="form-group">
        <label>物品名称</label>
        <input type="text" name="name" value="${item.name}" required>
      </div>
      <div class="form-group">
        <label>捡到日期</label>
        <input type="date" name="foundDate" value="${item.foundDate}" required>
      </div>
      <div class="form-group">
        <label>捡到地点</label>
        <input type="text" name="location" value="${item.location}" required>
      </div>
      <div class="form-group">
        <label>详细描述</label>
        <textarea name="description">${item.description}</textarea>
      </div>

      <div style="display: flex; gap: 10px;">
        <a href="home" class="btn btn-secondary" style="text-decoration: none; line-height: 20px;">取消</a>
        <button type="submit" class="btn">保存修改</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>