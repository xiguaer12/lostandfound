package com.servlet;

import com.bl.ItemService;
import com.entity.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home") // 访问入口改为 /home
public class HomeServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. 获取所有物品
            List<Item> items = itemService.getAllItems();
            // 2. 放入 request 域
            request.setAttribute("allItems", items);
            // 3. 转发到 index.jsp 显示
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}