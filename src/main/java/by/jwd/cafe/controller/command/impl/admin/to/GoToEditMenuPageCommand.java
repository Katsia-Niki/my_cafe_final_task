package by.jwd.cafe.controller.command.impl.admin.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.RequestParameter;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.service.MenuItemService;
import by.jwd.cafe.service.impl.MenuItemServiceImpl;
import by.jwd.cafe.util.ImageEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class GoToEditMenuPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(EDIT_MENU_RESULT);
        session.removeAttribute(UPLOAD_RESULT);

        String menuItemIdToEdit = request.getParameter(RequestParameter.MENU_ITEM_ID_TO_EDIT);
        MenuItemService service = MenuItemServiceImpl.getInstance();
        Router router;
        try {
            Optional<MenuItem> optionalMenuItem = service.findMenuItemById(menuItemIdToEdit);
            if (optionalMenuItem.isPresent()) {
                MenuItem item = optionalMenuItem.get();
                Map<String, String> menuItemData = createMenuItemData(item);
                session.setAttribute(MENU_ITEM_IMAGE_SESSION, item.getPicture());
                session.setAttribute(MENU_ITEM_DATA_SESSION, menuItemData);
            } else {
                session.setAttribute(NOT_FOUND_SESSION, true);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.EDIT_MENU);
            router = new Router(PagePath.EDIT_MENU, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToEditMenuPageCommand was failed.", e);
            throw new CommandException("Try to execute GoToEditMenuPageCommand was failed.", e);
        }
        return router;
    }

    Map<String, String> createMenuItemData(MenuItem menuItem) {
        Map<String, String> menuItemData = new HashMap<>();
        menuItemData.put(MENU_ITEM_ID_SESSION, String.valueOf(menuItem.getMenuItemId()));
        menuItemData.put(MENU_ITEM_NAME_SESSION, menuItem.getName());
        menuItemData.put(MENU_ITEM_TYPE_SESSION, menuItem.getMenuItemType().toString());
        menuItemData.put(MENU_ITEM_DESCRIPTION_SESSION, menuItem.getDescription());
        menuItemData.put(MENU_ITEM_PRICE_SESSION, menuItem.getPrice().toString());
        menuItemData.put(MENU_ITEM_AVAILABLE_SESSION, String.valueOf(menuItem.isAvailable()));
        menuItemData.put(MENU_ITEM_PICTURE_SESSION, ImageEncoder.encode(menuItem.getPicture()));
        return menuItemData;
    }
}
