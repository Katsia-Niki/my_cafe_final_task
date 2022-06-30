package by.jwd.cafe.validator;

import java.util.Map;

/**
 * {@code MenuItemValidator} interface represent functional to validate input data
 * for work with class {@link by.jwd.cafe.model.entity.MenuItem}
 */
public interface MenuItemValidator {
    /**
     * {@code WRONG_DATA_MARKER} constant represent string to mark wrong data
     */
    String WRONG_DATA_MARKER = "Wrong data";

    /**
     * {@code validateName} method to validate menu item name to create or update menu item
     *
     * @param name - menu item name value as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateName(String name);

    /**
     * {@code validateDescription} method to validate menu item description to create or update menu item
     *
     * @param description - menu item description value as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateDescription(String description);

    /**
     * {@code validatePrice} method to validate menu item price
     *
     * @param price - price value as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validatePrice(String price);

    /**
     * {@code validateItemData} method to validate menu item data to create or update menu item
     *
     * @param menuItemData - menu item data to create or update menu item
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateItemData(Map<String, String> menuItemData);

    /**
     * {@code validateType} method to validate menu item type
     *
     * @param menuItemType - menu item type value as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateType(String menuItemType);
}
