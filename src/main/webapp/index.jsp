<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <title>首页 - 失物招领平台</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
  <script>
    function confirmDelete(itemId) {
      if(confirm("确定要删除这条物品信息吗？此操作无法撤销。")) {
        window.location.href = "deleteItem?id=" + itemId;
      }
    }
  </script>
</head>
<body>

<!-- 导航栏 -->
<nav class="navbar">
  <div class="brand"><a href="home">Lost & Found 🔍</a></div>
  <div class="nav-links">
    <c:choose>
      <c:when test="${not empty sessionScope.user}">
        <span>你好, <strong>${sessionScope.user.username}</strong></span>
        <a href="publishItem.jsp">➕ 发布物品</a>
        <a href="logout" style="color: #e74c3c;">退出</a>
      </c:when>
      <c:otherwise>
        <a href="login.jsp">登录</a>
        <a href="register.jsp" class="btn" style="padding: 8px 15px; width: auto; color: white;">注册</a>
      </c:otherwise>
    </c:choose>
  </div>
</nav>

<div class="container">
  <!-- 搜索框 -->
  <div class="card" style="max-width: 100%; margin-bottom: 30px;">
    <form action="search" method="get" class="search-box">
      <input type="text" name="keyword" placeholder="请输入物品名称、特征或地点..." style="flex: 1;">
      <button type="submit" class="btn" style="width: auto;">🔍 搜索</button>
    </form>
  </div>

  <!-- 物品列表展示区 -->
  <h2 style="text-align: left; border-left: 5px solid var(--primary-color); padding-left: 15px;">最新发布的信息</h2>

  <div class="table-wrapper">
    <table>
      <thead>
      <tr>
        <th style="width: 15%;">物品名称</th>
        <th style="width: 15%;">发现日期</th>
        <th style="width: 20%;">地点</th>
        <th style="width: 30%;">描述</th>
        <th style="width: 20%;">操作</th>
      </tr>
      </thead>
      <tbody>
      <c:choose>
        <%-- 这里使用 allItems (HomeServlet 传来的) 或 items (搜索结果传来的) --%>
        <c:when test="${not empty allItems or not empty items}">
          <c:set var="displayList" value="${not empty items ? items : allItems}" />

          <c:forEach var="item" items="${displayList}">
            <tr>
              <td><strong><c:out value="${item.name}" /></strong></td>
              <td><c:out value="${item.foundDate}" /></td>
              <td><c:out value="${item.location}" /></td>
              <td style="color: #666;"><c:out value="${item.description}" /></td>
              <td>
                  <%-- 核心逻辑：判断当前登录用户ID是否等于物品发布者ID --%>
                <c:if test="${not empty sessionScope.user and sessionScope.user.id == item.userId}">
                  <a href="updateItem?id=${item.id}" class="btn" style="padding: 5px 10px; font-size: 0.8rem; width: auto;">编辑</a>
                  <button onclick="confirmDelete(${item.id})" class="btn btn-secondary" style="padding: 5px 10px; font-size: 0.8rem; width: auto; background-color: #e74c3c;">删除</button>
                </c:if>
                  <%-- 如果不是主人，或者是游客 --%>
                <c:if test="${empty sessionScope.user or sessionScope.user.id != item.userId}">
                  <span style="color: #999; font-size: 0.9rem;">(仅查看)</span>
                </c:if>
              </td>
            </tr>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <tr>
            <td colspan="5" style="text-align: center; padding: 30px; color: #999;">
              暂无物品信息
            </td>
          </tr>
        </c:otherwise>
      </c:choose>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>