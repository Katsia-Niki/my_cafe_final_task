package by.jwd.cafe.model.entity;

/**
 * The {@code MenuItemType} class
 * is enum that represents table 'menu_item_type' in the database.
 */

public enum MenuItemType {
    UNSUPPORTED,
    APPETIZER,
    MAIN_COURSE,
    SOUP,
    DESSERT,
    DRINK;

    private static final char UNDERSCORE = '_';
    private static final char SPACE = ' ';

    @Override
    public String toString() {
        return this.name().replace(UNDERSCORE, SPACE).toLowerCase();
    }


    public static MenuItemType valueOfMenuItemType(String name) {
        return MenuItemType.valueOf(name.toUpperCase().replace(SPACE, UNDERSCORE));
    }

}
