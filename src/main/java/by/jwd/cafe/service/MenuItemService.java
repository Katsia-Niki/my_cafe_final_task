package by.jwd.cafe.service;

import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.MenuItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code MenuItemService} interface represent functional business logic
 * for work with class {@link by.jwd.cafe.model.entity.MenuItem}
 */
public interface MenuItemService {

    /**
     * Finds all menu items on current page
     *
     * @param currentPageNumber - number of current page
     * @return list of menu items or empty list if menu items not found
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    List<MenuItem> findAllMenuItems(int currentPageNumber) throws ServiceException;

    /**
     * Finds number of all pages
     *
     * @return number of all pages
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    int findNumberOfPages() throws ServiceException;

    /**
     * Finds number of all available pages
     *
     * @return number of all available pages
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    int findNumberOfAvailablePages() throws ServiceException;

    /**
     * Finds all available menu items on current page
     *
     * @param currentPageNumber - number of current page
     * @return list of menu items or empty list if menu items not found
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    List<MenuItem> findAvailablePaginatedMenuItems(int currentPageNumber) throws ServiceException;

    /**
     * Finds menu item by id
     *
     * @param menuItemId - menu item id
     * @return an Optional describing menu item, or an empty Optional if menu item not found
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    Optional<MenuItem> findMenuItemById(String menuItemId) throws ServiceException;

    /**
     * Adds menu item to database
     *
     * @param menuData - map with menu item data
     *                 As key use {@link by.jwd.cafe.controller.command.SessionAttribute}
     * @return true - if menu item was created and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean addMenuItem(Map<String, String> menuData) throws ServiceException;

    /**
     * Updates menu item
     *
     * @param menuData - map with menu item data
     *                 As key use {@link by.jwd.cafe.controller.command.SessionAttribute}
     * @return true - if menu item was updated and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean updateMenuItem(Map<String, String> menuData) throws ServiceException;

    /**
     * Create image
     *
     * @param newImage   - image as byte array
     * @param menuItemId - menu item id
     * @return true - if image was created and false - if was not
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean createImage(byte[] newImage, String menuItemId) throws ServiceException;

    /**
     * Adds this menu item and its quantity to the cart
     * If this menu item is already presented in the cart, the quantity of
     * this menu item summed up with previous quantity.
     *
     * @param cart      - a user's cart
     * @param itemToAdd - a menu item to add to the cart
     * @param quantity  - current menu item quantity
     */
    void addItemToCart(Map<MenuItem, Integer> cart, MenuItem itemToAdd, int quantity);

    /**
     * Removes the menu item from the cart
     *
     * @param cart           - a user's shopping cart
     * @param itemToRemoveId - id of the menu item to remove
     */
    boolean removeItemFromCart(Map<MenuItem, Integer> cart, int itemToRemoveId);

    /**
     * Calculates cart sum using menu item price and its quantity.
     *
     * @param cart a user's cart
     * @return a {@code BigDecimal} value that represent total sum of all menu items in the cart
     */
    BigDecimal calculateCartSum(Map<MenuItem, Integer> cart);
}
