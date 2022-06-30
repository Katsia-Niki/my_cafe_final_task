package by.jwd.cafe.controller.command.impl.customer.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.service.MenuItemService;
import by.jwd.cafe.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Map;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class GoToCartPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<MenuItem, Integer> cart = (Map<MenuItem, Integer>) session.getAttribute(CART);
        session.setAttribute(CURRENT_PAGE, PagePath.CART);
        Router router = new Router(PagePath.CART);
        if (cart == null || cart.isEmpty()) {
            return router;
        }
        MenuItemService service = MenuItemServiceImpl.getInstance();
        BigDecimal cartSum = service.calculateCartSum(cart);
        session.setAttribute(CART_SUM, cartSum);
        return router;
    }
}
