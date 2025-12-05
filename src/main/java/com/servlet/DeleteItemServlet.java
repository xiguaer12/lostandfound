package com.servlet;

import com.bl.ItemService;
import com.entity.Item;
import com.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteItem")
public class DeleteItemServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取要删除的物品ID
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int itemId = Integer.parseInt(idStr);
            try {
                // 安全检查：只有发布者才能删除（可选，建议加上）
                User currentUser = (User) request.getSession().getAttribute("user");
                Item item = itemService.getItemById(itemId);

                if (currentUser != null && item != null && item.getUserId() == currentUser.getId()) {
                    itemService.deleteItemInfo(itemId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 删除完成后重定向回主页
        response.sendRedirect("home");
    }
}