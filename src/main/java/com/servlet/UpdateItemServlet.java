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
import java.sql.Date;

@WebServlet("/updateItem")
public class UpdateItemServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    // GET: 获取物品信息并跳转到编辑页面
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int itemId = Integer.parseInt(idStr);
                Item item = itemService.getItemById(itemId);

                // 权限检查
                User user = (User) request.getSession().getAttribute("user");
                if (user == null || item == null || user.getId() != item.getUserId()) {
                    response.sendRedirect("home"); // 无权访问
                    return;
                }

                request.setAttribute("item", item);
                request.getRequestDispatcher("editItem.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // POST: 提交修改后的数据
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Item item = new Item();
            item.setId(id);
            item.setName(request.getParameter("name"));
            item.setDescription(request.getParameter("description"));
            item.setLocation(request.getParameter("location"));

            String dateStr = request.getParameter("foundDate");
            if (dateStr != null && !dateStr.isEmpty()) {
                item.setFoundDate(Date.valueOf(dateStr));
            }
            // 这里的 imagePath 暂时保留原值或处理上传，简单起见先设为空或隐藏域
            item.setImagePath("");

            itemService.updateItemInfo(item);

            response.sendRedirect("home");
        } catch (Exception e) {
            e.printStackTrace();
            // 错误处理...
        }
    }
}