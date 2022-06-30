package by.jwd.cafe.model.dao.mapper.impl;

import by.jwd.cafe.model.dao.mapper.Mapper;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.model.entity.MenuItemType;
import by.jwd.cafe.util.ImageEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.jwd.cafe.model.dao.ColumnName.*;

public class ItemMapper implements Mapper<MenuItem> {
    static Logger logger = LogManager.getLogger();
    private static final ItemMapper instance = new ItemMapper();

    private ItemMapper() {
    }

    public static ItemMapper getInstance() {
        return instance;
    }

    @Override
    public Optional<MenuItem> map(ResultSet resultSet) {
        Optional<MenuItem> optionalMenuItem;
        try {
            MenuItem menuItem = new MenuItem.MenuItemBuilder()
                    .withMenuItemId(resultSet.getInt(MENU_ITEM_ID))
                    .withMenuItemType(MenuItemType.valueOf(resultSet.getString(TYPE_NAME).toUpperCase()))
                    .withName(resultSet.getString(MENU_ITEM_NAME))
                    .withDescription(resultSet.getString(DESCRIPTION))
                    .withPrice(resultSet.getBigDecimal(PRICE))
                    .withIsAvailable(resultSet.getBoolean(AVAILABLE))
                    .build();
            Blob blob = resultSet.getBlob(PICTURE);
            if (blob != null) {
                byte[] imageContent = blob.getBytes(1, (int) blob.length());
                menuItem.setImage(ImageEncoder.encode(imageContent));
                menuItem.setPicture(imageContent);
            }
            optionalMenuItem = Optional.of(menuItem);
        } catch (SQLException e) {
            logger.error("SQL exception while map MenuItem resultSet", e);
            optionalMenuItem = Optional.empty();
        }
        return optionalMenuItem;
    }
}
