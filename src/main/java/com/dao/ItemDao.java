package com.dao; // 请确保包名与您的项目结构一致

import com.entity.Item; // 请确保包名与您的项目结构一致
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {

    /**
     * 将一个新的物品信息插入到数据库中
     * @param item 包含要添加的物品信息的对象 (id应为0或null)
     * @throws SQLException 如果发生数据库错误
     */
    public void addItem(Item item) throws SQLException {
        // user_id 是外键，必须提供
        String sql = "INSERT INTO items (name, description, found_date, location, user_id, image_path) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.setDate(3, item.getFoundDate()); // 确保 item.getFoundDate() 返回 java.sql.Date
            pstmt.setString(4, item.getLocation());
            pstmt.setInt(5, item.getUserId());
            pstmt.setString(6, item.getImagePath());

            pstmt.executeUpdate();
        }
    }

    /**
     * 更新数据库中已存在的物品信息
     * @param item 包含更新后信息的物品对象 (id必须有效)
     * @throws SQLException 如果发生数据库错误
     */
    public void updateItem(Item item) throws SQLException {
        String sql = "UPDATE items SET name = ?, description = ?, found_date = ?, location = ?, image_path = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.setDate(3, item.getFoundDate());
            pstmt.setString(4, item.getLocation());
            pstmt.setString(5, item.getImagePath());
            pstmt.setInt(6, item.getId()); // WHERE 条件参数放在最后

            pstmt.executeUpdate();
        }
    }

    /**
     * 根据物品ID从数据库中删除一个物品
     * @param itemId 要删除的物品的ID
     * @throws SQLException 如果发生数据库错误
     */
    public void deleteItem(int itemId) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);

            pstmt.executeUpdate();
        }
    }

    /**
     * 根据ID从数据库中查找一个物品
     * @param itemId 要查找的物品的ID
     * @return 如果找到，则返回Item对象；否则返回null
     * @throws SQLException 如果发生数据库错误
     */
    public Item getItemById(int itemId) throws SQLException {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 如果结果集中有数据，则映射并返回对象
                    return mapRowToItem(rs);
                }
            }
        }
        // 如果没有找到匹配的记录，返回null
        return null;
    }

    /**
     * 模糊查询物品，匹配物品名称或描述
     * @param keyword 搜索的关键词
     * @return 包含所有匹配物品的列表
     * @throws SQLException 如果发生数据库错误
     */
    public List<Item> findItems(String keyword) throws SQLException {
        List<Item> items = new ArrayList<>();
        // 使用 LIKE 进行模糊匹配
        String sql = "SELECT * FROM items WHERE name LIKE ? OR description LIKE ? ORDER BY found_date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // 遍历结果集，将每一行都映射成一个Item对象，并添加到列表中
                    items.add(mapRowToItem(rs));
                }
            }
        }
        return items;
    }

    /**
     * 辅助方法：将当前 ResultSet 行的数据映射到一个 Item 对象
     * @param rs 数据库查询结果集，游标已指向有效行
     * @return 填充了数据的 Item 对象
     * @throws SQLException
     */
    private Item mapRowToItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setFoundDate(rs.getDate("found_date"));
        item.setLocation(rs.getString("location"));
        item.setUserId(rs.getInt("user_id"));
        item.setImagePath(rs.getString("image_path"));
        return item;
    }

    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        // 按时间倒序排列，最新的在前面
        String sql = "SELECT * FROM items ORDER BY found_date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                items.add(mapRowToItem(rs)); // 复用之前的辅助方法
            }
        }
        return items;
    }
}