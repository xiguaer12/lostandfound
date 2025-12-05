package com.bl;

import com.dao.ItemDao;
import com.entity.Item;
import java.sql.SQLException;
import java.util.List;

public class ItemService {
    private ItemDao itemDao = new ItemDao();

    public void publishItem(Item item) throws SQLException {
        itemDao.addItem(item);
    }

    public void updateItemInfo(Item item) throws SQLException {
        itemDao.updateItem(item);
    }

    public void deleteItemInfo(int itemId) throws SQLException {
        itemDao.deleteItem(itemId);
    }

    public List<Item> searchItems(String keyword) throws SQLException {
        return itemDao.findItems(keyword);
    }

    public List<Item> getAllItems() throws SQLException {
        return itemDao.getAllItems();
    }

    public Item getItemById(int id) throws SQLException {
        return itemDao.getItemById(id);
    }
}