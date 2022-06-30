package by.jwd.cafe.controller.command.impl.customer;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.Order;
import by.jwd.cafe.service.OrderService;
import by.jwd.cafe.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class CancelOrderCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        OrderService orderService = OrderServiceImpl.getInstance();
        Order order = (Order) session.getAttribute(ORDER_SESSION);
        String role = session.getAttribute(CURRENT_ROLE).toString();
        String newStatus = Order.Status.CANCELLED_BY_CUSTOMER.name();

        Router router;
        try {
            boolean result = orderService.updateStatus(role, newStatus, order);
            session.setAttribute(UPDATE_ORDER_RESULT, result);
            session.removeAttribute(ORDER_SESSION);
            session.setAttribute(CURRENT_PAGE, PagePath.CANCEL_ORDER);
            router = new Router(PagePath.CANCEL_ORDER, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CancelOrderCommand was failed.", e);
            throw new CommandException("Try to execute CancelOrderCommand was failed.", e);
        }
        return router;
    }
}
