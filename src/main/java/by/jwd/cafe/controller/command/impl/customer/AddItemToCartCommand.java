package by.jwd.cafe.controller.command.impl.customer;

import by.jwd.cafe.controller.command.*;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.service.MenuItemService;
import by.jwd.cafe.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AddItemToCartCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        List<MenuItem> menuItemList = (List<MenuItem>) session.getAttribute(SessionAttribute.MENU_ITEM_AVAILABLE_SESSION);
        Map<MenuItem, Integer> cart = (Map<MenuItem, Integer>) session.getAttribute(SessionAttribute.CART);
        int menuItemId = Integer.parseInt(request.getParameter(RequestParameter.MENU_ITEM_ID));
        int quantity = Integer.parseInt(request.getParameter(RequestParameter.MENU_ITEM_QUANTITY));
        Optional<MenuItem> optionalItemToAdd = menuItemList.stream()
                .filter(menuItem -> menuItemId == menuItem.getMenuItemId()).findAny();
        MenuItemService service = MenuItemServiceImpl.getInstance();
        if (optionalItemToAdd.isPresent()) {
            service.addItemToCart(cart, optionalItemToAdd.get(), quantity);
            session.setAttribute(SessionAttribute.MESSAGE_ITEM_ADDED_TO_CART, true);
            session.setAttribute(SessionAttribute.ITEM_LABEL, menuItemId);
        } else {
            session.setAttribute(SessionAttribute.MESSAGE_ITEM_ADDED_TO_CART, false);
        }
        session.setAttribute(SessionAttribute.CURRENT_PAGE, Command.extractPage(request));
        Router router = new Router(PagePath.MENU, Router.Type.REDIRECT);
        return router;
    }
}
