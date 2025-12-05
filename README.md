# 校园失物招领系统 (Lost & Found System)

这是一个基于 Java Web (Servlet + JSP) 开发的失物招领信息管理平台。系统采用分层架构设计，实现了用户注册登录、物品发布、物品搜索（模糊匹配）、以及对个人发布物品的修改与删除功能。

本项目**不使用**任何第三方重型框架（如 Spring, MyBatis），旨在演示原生的 Java EE (Jakarta EE) 开发流程和 MVC 设计模式。

---

## 🛠 技术栈

*   **开发语言**: Java (JDK 8 或更高)
*   **Web 服务器**: Apache Tomcat 10.0+ (支持 Jakarta EE 9 规范)
*   **数据库**: MySQL 8.0
*   **构建工具**: Apache Maven
*   **后端技术**: Jakarta Servlet, JDBC, jBCrypt (密码加密)
*   **前端技术**: JSP, JSTL, CSS (自定义样式), HTML5

---

## 🏗 系统架构设计

本项目严格遵循分层架构原则，实现了高内聚低耦合：

1.  **View (视图层 - JSP)**
    *   位于 `src/main/webapp`。
    *   负责页面展示和用户交互。
    *   使用 JSTL 标签库进行数据渲染，CSS 进行样式美化。

2.  **Controller (控制层 - Servlet)**
    *   位于 `com.yourdomain.servlet`。
    *   负责接收 HTTP 请求，解析参数。
    *   调用业务逻辑层（Service）处理业务。
    *   控制页面跳转（转发或重定向）。

3.  **Service (业务逻辑层 - BL)**
    *   位于 `com.yourdomain.bl`。
    *   负责核心业务逻辑处理（如密码校验、权限判断）。
    *   作为 Controller 和 DAO 之间的桥梁，确保事务的一致性。

4.  **DAO (数据访问层)**
    *   位于 `com.yourdomain.dao`。
    *   封装 JDBC 操作，直接与数据库交互。
    *   实现 CRUD（增删改查）的具体 SQL 逻辑。
    *   包含数据库连接工具类 `DatabaseUtil`。

5.  **Entity (实体层 - POJO)**
    *   位于 `com.yourdomain.entity`。
    *   简单的 Java 对象，对应数据库表结构，用于层间数据传输。

---

## 💾 数据库结构说明

请在 MySQL 中创建一个名为 `lostandfound` 的数据库，并执行以下 SQL 脚本创建表结构。

### 1. 用户表 (`users`)

用于存储注册用户的信息，密码经过 BCrypt 加密存储，**严禁明文存储**。

sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '加密后的密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
2. 物品表 (items)
用于存储失物招领信息，关联到发布用户。
code
SQL
CREATE TABLE items (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '物品ID',
    name VARCHAR(100) NOT NULL COMMENT '物品名称',
    description TEXT COMMENT '详细描述',
    found_date DATE COMMENT '捡到日期',
    location VARCHAR(255) COMMENT '捡到地点',
    image_path VARCHAR(255) COMMENT '图片路径(预留)',
    user_id INT NOT NULL COMMENT '发布者ID',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
🚀 快速开始 (使用方法)
1. 环境准备
安装 Java JDK 8+。
安装 Maven。
安装 MySQL 数据库。
下载并解压 Tomcat 10+。
IDE 推荐使用 IntelliJ IDEA 或 Eclipse。
2. 数据库配置
执行上述 SQL 脚本创建数据库和表。
打开项目中 src/main/java/com/yourdomain/dao/DatabaseUtil.java 文件。
修改 USER 和 PASSWORD 常量为您本地 MySQL 的账号和密码。
code
Java
private static final String URL = "jdbc:mysql://localhost:3306/lostandfound";
private static final String USER = "root";       // 修改这里
private static final String PASSWORD = "root";   // 修改这里
3. 构建与部署
使用 IntelliJ IDEA:
导入项目为 Maven 项目。
配置 Tomcat Server：
Run -> Edit Configurations -> Tomcat Server -> Local。
Deployment: 添加 Artifact ...:war exploded。
Application context: 设置为 /lostandfound (或者 /)。
运行项目。
使用命令行:
在项目根目录运行 mvn clean package。
将生成的 .war 文件复制到 Tomcat 的 webapps 目录下。
启动 Tomcat。
4. 访问系统
打开浏览器访问：http://localhost:8080/lostandfound/home
(注意：如果您的 Application context 设置不同，请相应调整 URL)
✨ 功能特性
用户认证:
注册：密码使用 jBCrypt 自动加盐哈希。
登录/登出：使用 HttpSession 维持登录状态。
权限拦截：未登录用户无法发布或修改物品。
物品浏览 (/home):
首页展示所有最新发布的失物招领信息。
列表按时间倒序排列。
物品搜索:
支持模糊查询。
输入关键词可同时匹配“物品名称”和“描述”内容。
物品发布:
登录用户可以填写表单发布捡到的物品信息。
管理功能:
修改: 用户只能编辑自己发布的物品。
删除: 用户只能删除自己发布的物品。
系统会在前端和后端同时校验用户权限（Owner Check）。
📂 项目目录结构
code
Text
lost-and-found-app
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.yourdomain
│   │   │       ├── bl          // 业务逻辑层 (Service)
│   │   │       ├── dao         // 数据访问层 (DAO + DBUtil)
│   │   │       ├── entity      // 实体类 (POJO)
│   │   │       └── servlet     // 控制层 (Controller)
│   │   │           ├── HomeServlet.java
│   │   │           ├── LoginServlet.java
│   │   │           ├── RegisterServlet.java
│   │   │           ├── PublishItemServlet.java
│   │   │           ├── SearchItemServlet.java
│   │   │           └── ...
│   │   ├── resources
│   │   └── webapp
│   │       ├── css
│   │       │   └── style.css   // 全局样式表
│   │       ├── WEB-INF
│   │       │   └── web.xml
│   │       ├── index.jsp       // 首页 (列表展示)
│   │       ├── login.jsp       // 登录页
│   │       ├── register.jsp    // 注册页
│   │       ├── publishItem.jsp // 发布页
│   │       ├── editItem.jsp    // 编辑页
│   │       └── search-results.jsp
└── pom.xml                     // Maven 依赖配置
⚠️ 注意事项
Tomcat 版本: 本项目使用了 jakarta.servlet 包，必须运行在 Tomcat 10 或更高版本上。Tomcat 9 及以下版本（使用 javax.servlet）无法运行。
乱码问题: 项目已配置 UTF-8 编码，如遇乱码请检查 JSP 头部配置及 Tomcat 的 URIEncoding 设置。
