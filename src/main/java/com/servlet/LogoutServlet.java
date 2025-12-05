package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取当前的Session，参数为false表示如果Session不存在，则不创建新的
        HttpSession session = request.getSession(false);

        if (session != null) {
            // 2. 如果Session存在，则使其失效
            session.invalidate();
        }

        // 3. 重定向到登录页面
        response.sendRedirect("login.jsp");
    }
}