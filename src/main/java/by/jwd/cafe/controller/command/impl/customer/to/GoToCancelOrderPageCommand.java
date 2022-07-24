package by.jwd.cafe.controller.command.impl.customer.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.RequestParameter;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.model.entity.Order;
import by.jwd.cafe.service.MenuItemService;
import by.jwd.cafe.service.OrderService;
import by.jwd.cafe.service.impl.MenuItemServiceImpl;
import by.jwd.cafe.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class GoToCancelOrderPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(UPDATE_ORDER_RESULT);

        String orderId = request.getParameter(RequestParameter.ORDER_ID);
        OrderService orderService = OrderServiceImpl.getInstance();
        MenuItemService menuItemService = MenuItemServiceImpl.getInstance();

        Router router;
        try {
            Optional<Order> optionalOrder = orderService.findOrderById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                String menuItemId = String.valueOf(order.getOrderId());
                Optional<MenuItem> optionalMenuItem = menuItemService.findMenuItemById(menuItemId);
                MenuItem menuItem = optionalMenuItem.get();
                session.setAttribute(ORDER_SESSION, order);
                session.setAttribute(MENU_ITEM_SESSION, menuItem);
            } else {
                session.setAttribute(WRONG_ORDER_ID_SESSION, true);
            }
            session.setAttribute(CURRENT_PAGE, Command.extractPage(request));
            router = new Router(PagePath.CANCEL_ORDER);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToCancelOrderPageCommand was failed.", e);
            throw new CommandException("Try to execute GoToCancelOrderPageCommand was failed.", e);
        }
        return router;
    }
}
