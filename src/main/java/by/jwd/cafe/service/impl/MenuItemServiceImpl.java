package by.jwd.cafe.service.impl;

import by.jwd.cafe.exception.DaoException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.dao.MenuItemDao;
import by.jwd.cafe.model.dao.impl.MenuItemDaoImpl;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.model.entity.MenuItemType;
import by.jwd.cafe.service.MenuItemService;
import by.jwd.cafe.validator.MenuItemValidator;
import by.jwd.cafe.validator.impl.MenuItemValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class MenuItemServiceImpl implements MenuItemService {
    static Logger logger = LogManager.getLogger();
    private static MenuItemServiceImpl instance = new MenuItemServiceImpl();
    private MenuItemDao itemDao = MenuItemDaoImpl.getInstance();

    private MenuItemServiceImpl() {
    }

    public static MenuItemServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<MenuItem> findAllMenuItems(int currentPageNumber) throws ServiceException {
        List<MenuItem> menu;
        try {
            menu = itemDao.findAllPaginatedItems(currentPageNumber);
        } catch (DaoException e) {
            logger.error("Try to find all menu was failed.", e);
            throw new ServiceException("Try to find all menu was failed.", e);
        }
        return menu;
    }

    @Override
    public int findNumberOfPages() throws ServiceException {
        int numberOfPages;
        try {
            numberOfPages = itemDao.findNumberOfAllItems();
        } catch (DaoException e) {
            logger.error("Try to count pages was failed.", e);
            throw new ServiceException("Try to count pages was failed.", e);
        }
        return numberOfPages;
    }

    @Override
    public int findNumberOfAvailablePages() throws ServiceException {
        int numberOfPages;
        try {
            numberOfPages = itemDao.findNumberOfAvailableItems();
        } catch (DaoException e) {
            logger.error("Try to count pages was failed.", e);
            throw new ServiceException("Try to count pages was failed.", e);
        }
        return numberOfPages;
    }

    @Override
    public List<MenuItem> findAvailablePaginatedMenuItems(int currentPageNumber) throws ServiceException {
        List<MenuItem> menu;
        try {
            menu = itemDao.findAvailablePaginatedItems(currentPageNumber);
        } catch (DaoException e) {
            logger.error("Try to find all menu was failed.", e);
            throw new ServiceException("Try to find all menu was failed.", e);
        }
        return menu;
    }

    @Override
    public Optional<MenuItem> findMenuItemById(String menuItemId) throws ServiceException {
        Optional<MenuItem> optionalItem = Optional.empty();
        try {
            int itemId = Integer.parseInt(menuItemId);
            optionalItem = itemDao.findEntityById(itemId);
        } catch (NumberFormatException ex) {
            logger.info("Not valid menu item id.", ex);
        } catch (DaoException e) {
            logger.error("Try to find menu item by id was failed.", e);
            throw new ServiceException("Try to find menu item by id was failed.", e);
        }
        return optionalItem;
    }

    @Override
    public boolean addMenuItem(Map<String, String> menuData) throws ServiceException {
        boolean isAdded = false;
        MenuItemValidator validator = MenuItemValidatorImpl.getInstance();
        if (!validator.validateItemData(menuData)) {
            logger.info("Invalid menu item data.");
            return isAdded;
        }
        String itemName = menuData.get(MENU_ITEM_NAME_SESSION);
        String description = menuData.get(MENU_ITEM_DESCRIPTION_SESSION);
        BigDecimal price = new BigDecimal(menuData.get(MENU_ITEM_PRICE_SESSION));
        MenuItemType menuItemType = MenuItemType.valueOf(menuData.get(MENU_ITEM_TYPE_SESSION).toUpperCase());
        boolean isAvailable = menuData.get(MENU_ITEM_AVAILABLE_SESSION) != null
                ? Boolean.parseBoolean(menuData.get(MENU_ITEM_AVAILABLE_SESSION))
                : Boolean.TRUE;
        MenuItem item = new MenuItem.MenuItemBuilder()
                .withName(itemName)
                .withDescription(description)
                .withMenuItemType(menuItemType)
                .withPrice(price)
                .withIsAvailable(isAvailable)
                .build();
        try {
            isAdded = itemDao.add(item);
        } catch (DaoException e) {
            logger.info("Try to add new menu item was failed.", e);
            throw new ServiceException("Try to add new menu item was failed.", e);
        }
        return isAdded;
    }

    @Override
    public boolean updateMenuItem(Map<String, String> menuData) throws ServiceException {
        boolean isUpdated = false;
        MenuItemValidator validator = MenuItemValidatorImpl.getInstance();
        if (!validator.validateItemData(menuData)) {
            logger.info("Invalid menu item data.");
            return isUpdated;
        }
        String itemName = menuData.get(MENU_ITEM_NAME_SESSION);
        String description = menuData.get(MENU_ITEM_DESCRIPTION_SESSION);
        BigDecimal price = new BigDecimal(menuData.get(MENU_ITEM_PRICE_SESSION));
        boolean isAvailable = Boolean.parseBoolean(menuData.get(MENU_ITEM_AVAILABLE_SESSION));
        try {
            int menuItemId = Integer.parseInt(menuData.get(MENU_ITEM_ID_SESSION));
            MenuItemType itemType = MenuItemType.valueOfMenuItemType(menuData.get(MENU_ITEM_TYPE_SESSION).toUpperCase());
            MenuItem menuItem = new MenuItem.MenuItemBuilder()
                    .withMenuItemId(menuItemId)
                    .withMenuItemType(itemType)
                    .withName(itemName)
                    .withDescription(description)
                    .withPrice(price)
                    .withIsAvailable(isAvailable)
                    .build();
            isUpdated = itemDao.update(menuItem);
        } catch (IllegalArgumentException e) {
            logger.info("Invalid menu item data.");
        } catch (DaoException e) {
            logger.error("Try to update menu item was failed.", e);
            throw new ServiceException("Try to update menu item was failed.", e);
        }
        return isUpdated;
    }

    @Override
    public boolean createImage(byte[] newImage, String menuItemId) throws ServiceException {
        boolean isCreate;
        try {
            int itemId = Integer.parseInt(menuItemId);
            isCreate = itemDao.updateImage(newImage, itemId);
        } catch (DaoException e) {
            logger.error("Try to create image was failed.", e);
            throw new ServiceException("Try to create image was failed.", e);
        }
        return isCreate;
    }

    @Override
    public void addItemToCart(Map<MenuItem, Integer> cart, MenuItem itemToAdd, int quantity) {
        if (!cart.containsKey(itemToAdd)) {
            cart.put(itemToAdd, quantity);
        } else {
            int previousQuantity = cart.get(itemToAdd);
            cart.put(itemToAdd, previousQuantity + quantity);
        }
    }

    @Override
    public boolean removeItemFromCart(Map<MenuItem, Integer> cart, int itemToRemoveId) {
        boolean result;
        Set<MenuItem> cartSet = cart.keySet();
        Optional<MenuItem> menuItemToRemove = cartSet.stream()
                .filter(menuItem -> itemToRemoveId == menuItem.getMenuItemId())
                .findAny();
        if (menuItemToRemove.isPresent()) {
            cart.remove(menuItemToRemove.get());
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public BigDecimal calculateCartSum(Map<MenuItem, Integer> cart) {
        BigDecimal cartSum = new BigDecimal(0);
        Set<Map.Entry<MenuItem, Integer>> menuItems = cart.entrySet();

        for (Map.Entry<MenuItem, Integer> menuItem : menuItems) {
            BigDecimal price = menuItem.getKey().getPrice();
            BigDecimal quantity = BigDecimal.valueOf(menuItem.getValue());
            cartSum = cartSum.add(price.multiply(quantity));
        }
        return cartSum;
    }
}
