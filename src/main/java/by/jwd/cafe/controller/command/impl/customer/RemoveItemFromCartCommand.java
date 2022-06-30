package by.jwd.cafe.controller.command.impl.customer;

import by.jwd.cafe.controller.command.*;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.service.MenuItemService;
import by.jwd.cafe.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;

public class RemoveItemFromCartCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<MenuItem, Integer> cart = (Map<MenuItem, Integer>) session.getAttribute(SessionAttribute.CART);
        int menuItemIdToRemove = Integer.parseInt(request.getParameter(RequestParameter.MENU_ITEM_ID_TO_REMOVE));
        MenuItemService service = MenuItemServiceImpl.getInstance();
        boolean result = service.removeItemFromCart(cart, menuItemIdToRemove);
        if (!result) {
            logger.error("Invalid menu item id to remove."); //fixme может удалить?
        }
        BigDecimal cartSum = service.calculateCartSum(cart);
        session.setAttribute(SessionAttribute.CART_SUM, cartSum);
        return new Router(PagePath.CART, Router.Type.REDIRECT);
    }
}
