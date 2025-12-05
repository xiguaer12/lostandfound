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

@WebServlet("/search")
public class SearchItemServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        try {
            List<Item> items = itemService.searchItems(keyword);
            request.setAttribute("items", items);
            request.getRequestDispatcher("search-results.jsp").forward(request, response);
        } catch (Exception e) {
            // 错误处理
        }
    }
}