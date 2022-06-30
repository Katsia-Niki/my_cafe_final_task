package by.jwd.cafe.controller.command.impl.admin;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.RequestParameter;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.service.MenuItemService;
import by.jwd.cafe.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class EditMenuItemCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> menuItemData = (Map<String, String>) session.getAttribute(MENU_ITEM_DATA_SESSION);

        removeTempData(menuItemData);
        updateMenuItemData(request, menuItemData);

        MenuItemService service = MenuItemServiceImpl.getInstance();
        Router router;
        try {
            int sizeBefore = menuItemData.size();
            boolean result = service.updateMenuItem(menuItemData);
            int sizeAfter = menuItemData.size();
            if (sizeBefore == sizeAfter) {
                session.removeAttribute(MENU_ITEM_DATA_SESSION);
                session.setAttribute(EDIT_MENU_RESULT, result);
            } else {
                session.setAttribute(MENU_ITEM_DATA_SESSION, menuItemData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.EDIT_MENU);
            router = new Router(PagePath.EDIT_MENU, Router.Type.REDIRECT);

        } catch (ServiceException e) {
            logger.error("Try to execute EditMenuItemCommand was failed.", e);
            throw new CommandException("Try to execute EditMenuItemCommand was failed.", e);
        }
        return router;
    }

    private void removeTempData(Map<String, String> menuItemData) {
        menuItemData.remove(WRONG_MENU_ITEM_NAME);
        menuItemData.remove(WRONG_MENU_ITEM_DESCRIPTION);
        menuItemData.remove(WRONG_MENU_ITEM_PRICE);
    }

    private void updateMenuItemData(HttpServletRequest request, Map<String, String> menuItemData) {
        menuItemData.put(MENU_ITEM_NAME_SESSION, request.getParameter(RequestParameter.MENU_ITEM_NAME));
        menuItemData.put(MENU_ITEM_DESCRIPTION_SESSION, request.getParameter(RequestParameter.MENU_ITEM_DESCRIPTION));
        menuItemData.put(MENU_ITEM_PRICE_SESSION, request.getParameter(RequestParameter.MENU_ITEM_PRICE));
        menuItemData.put(MENU_ITEM_AVAILABLE_SESSION, request.getParameter(RequestParameter.MENU_ITEM_IS_AVAILABLE));
    }
}
