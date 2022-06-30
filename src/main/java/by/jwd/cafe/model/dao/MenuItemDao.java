package by.jwd.cafe.model.dao;

import by.jwd.cafe.exception.DaoException;
import by.jwd.cafe.model.entity.MenuItem;

import java.util.List;

/**
 * {@code MenuItemDao} class implements functional of {@link BaseDao}
 */
public interface MenuItemDao extends BaseDao<Integer, MenuItem> {

    /**
     * Finds all menu items on current page
     *
     * @param currentPageNumber - number of current page
     * @return list of menu items or empty list if menu items not found
     * @throws DaoException - if request from database was failed
     */
    List<MenuItem> findAllPaginatedItems(int currentPageNumber) throws DaoException;

    /**
     * Finds all available menu items on current page
     *
     * @param currentPageNumber - number of current page
     * @return list of menu items or empty list if menu items not found
     * @throws DaoException - if request from database was failed
     */
    List<MenuItem> findAvailablePaginatedItems(int currentPageNumber) throws DaoException;

    /**
     * Finds number of all available menu items
     *
     * @return number of all available menu items
     * @throws DaoException - if request from database was failed
     */
    int findNumberOfAvailableItems() throws DaoException;

    /**
     * Finds number of all menu items
     *
     * @return number of all menu items
     * @throws DaoException - if request from database was failed
     */
    int findNumberOfAllItems() throws DaoException;

    /**
     * Updates image of manu item
     *
     * @param newImage   - new image
     * @param menuItemId - manu item id
     * @return true - if image was updated and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean updateImage(byte[] newImage, int menuItemId) throws DaoException;
}
