package com.servlet;

import com.bl.ItemService;
import com.entity.Item;
import com.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/publishItem")
public class PublishItemServlet extends HttpServlet {

    private ItemService itemService = new ItemService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 权限检查：确保用户已登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // 如果没登录，跳转回登录页
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. 获取表单数据
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String dateStr = request.getParameter("foundDate"); // 获取的是字符串 "2023-10-01"

        try {
            // 3. 构建 Item 对象
            Item item = new Item();
            item.setName(name);
            item.setLocation(location);
            item.setDescription(description);
            // 将当前登录用户的ID设置为物品发布者ID
            item.setUserId(user.getId());

            // 处理日期转换 (String -> java.sql.Date)
            if (dateStr != null && !dateStr.isEmpty()) {
                item.setFoundDate(Date.valueOf(dateStr));
            } else {
                item.setFoundDate(new Date(System.currentTimeMillis())); // 默认今天
            }

            // 4. 调用业务层保存数据
            itemService.publishItem(item);

            // 5. 成功后重定向回首页，或者显示成功页面
            response.sendRedirect("home");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "发布失败：" + e.getMessage());
            request.getRequestDispatcher("publishItem.jsp").forward(request, response);
        }
    }
}