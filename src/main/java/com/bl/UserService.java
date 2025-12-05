package com.bl;

import com.dao.UserDao;
import com.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;

public class UserService {
    private UserDao userDao = new UserDao();

    public void register(User user) throws SQLException {
        // 可以在这里添加更多的业务逻辑，例如检查用户名是否已存在
        userDao.addUser(user);
    }

    public User login(String username, String password) throws SQLException {
        User user = userDao.getUserByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}