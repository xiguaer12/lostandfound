<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>搜索结果</title>
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
    <h2 style="text-align: left; margin-bottom: 20px;">
        搜索结果
        <span style="font-size: 1rem; font-weight: normal; color: #666;">
                (共找到 ${empty items ? 0 : items.size()} 条相关信息)
            </span>
    </h2>

    <c:choose>
        <c:when test="${not empty items}">
            <div class="table-wrapper">
                <table>
                    <thead>
                    <tr>
                        <th style="width: 20%;">物品名称</th>
                        <th style="width: 15%;">发现日期</th>
                        <th style="width: 20%;">地点</th>
                        <th style="width: 45%;">描述</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${items}">
                        <tr>
                            <td><strong><c:out value="${item.name}" /></strong></td>
                            <td><c:out value="${item.foundDate}" /></td>
                            <td><c:out value="${item.location}" /></td>
                            <td style="color: #666;"><c:out value="${item.description}" /></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card" style="text-align: center; padding: 50px;">
                <h3 style="color: #999;">📭 没有找到相关物品</h3>
                <p style="margin-top: 10px;">尝试更换关键词，或者<a href="publishItem.jsp">发布一条新信息</a>。</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>