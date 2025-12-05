package com.servlet;

import com.bl.UserService;
import com.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.login(username, password);

            // 检查login方法返回的用户对象是否为null
            if (user != null) {
                // 登录成功
                // 1. 获取或创建Session对象
                HttpSession session = request.getSession();
                // 2. 将用户信息存入Session，以便后续页面判断登录状态
                session.setAttribute("user", user);
                // 3. 重定向到系统主页
                response.sendRedirect("home");
            } else {
                // 登录失败 (用户名或密码错误)
                // 1. 在request中设置错误消息
                request.setAttribute("error", "用户名或密码错误！");
                // 2. 转发回登录页面，并显示错误消息
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // 数据库连接等异常处理
            e.printStackTrace(); // 在服务器控制台打印错误，便于调试
            request.setAttribute("error", "登录时发生系统错误，请联系管理员。");
            request.getRequestDispatcher("home").forward(request, response);
        }
    }
}