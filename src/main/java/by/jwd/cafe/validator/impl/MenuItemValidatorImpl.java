package by.jwd.cafe.validator.impl;

import by.jwd.cafe.model.entity.MenuItemType;
import by.jwd.cafe.validator.MenuItemValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public final class MenuItemValidatorImpl implements MenuItemValidator {
    static Logger logger = LogManager.getLogger();
    private static final String NAME_REGEX = "[A-zА-яЁё\\s\\-]{2,100}";
    private static final String DESCRIPTION_REGEX = "[^><]+";
    private static final String PRICE_REGEX = "\\d{1,5}\\.?\\d{0,2}";
    private static final MenuItemValidatorImpl instance = new MenuItemValidatorImpl();

    private MenuItemValidatorImpl() {
    }

    public static MenuItemValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateName(String name) {
        return (name != null && name.matches(NAME_REGEX));
    }

    @Override
    public boolean validateDescription(String description) {
        return (description != null && description.matches(DESCRIPTION_REGEX));
    }

    @Override
    public boolean validatePrice(String price) {
        return (price != null && price.matches(PRICE_REGEX));
    }

    @Override
    public boolean validateType(String menuItemType) {
        try {
            MenuItemType.valueOfMenuItemType(menuItemType);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validateItemData(Map<String, String> menuItemData) {
        boolean isValid = true;
        String itemName = menuItemData.get(MENU_ITEM_NAME_SESSION);
        String description = menuItemData.get(MENU_ITEM_DESCRIPTION_SESSION);
        String price = menuItemData.get(MENU_ITEM_PRICE_SESSION);
        String menuItemType = menuItemData.get(MENU_ITEM_TYPE_SESSION);
        if (itemName != null && !validateName(itemName)) {
            isValid = false;
            logger.error("Invalid menu item name.");
            menuItemData.put(WRONG_MENU_ITEM_NAME, WRONG_DATA_MARKER);
        }
        if (description != null && !validateDescription(description)) {
            isValid = false;
            logger.error("Invalid menu item description.");
            menuItemData.put(WRONG_MENU_ITEM_DESCRIPTION, WRONG_DATA_MARKER);
        }
        if (price != null && !validatePrice(price)) {
            isValid = false;
            logger.error("Invalid menu item price.");
            menuItemData.put(WRONG_MENU_ITEM_PRICE, WRONG_DATA_MARKER);
        }
        if (menuItemType != null && !validateType(menuItemType)) {
            isValid = false;
            logger.error("Invalid menu item type.");
            menuItemData.put(WRONG_MENU_ITEM_TYPE, WRONG_DATA_MARKER);
        }
        return isValid;
    }
}
