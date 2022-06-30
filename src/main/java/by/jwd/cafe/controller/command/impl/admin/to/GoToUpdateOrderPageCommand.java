package by.jwd.cafe.controller.command.impl.admin.to;

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

import java.util.List;
import java.util.Optional;

import static by.jwd.cafe.controller.command.RequestParameter.ORDER_ID;
import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class GoToUpdateOrderPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(UPDATE_ORDER_RESULT);
        String orderId = request.getParameter(ORDER_ID);

        OrderService orderService = OrderServiceImpl.getInstance();

        Router router;
        try {
            Optional<Order> optionalOrder = orderService.findOrderById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                Order.Status status = order.getStatus();
                List<Order.Status> availableStatuses = orderService.findAvailableStatuses(status);
                session.setAttribute(ORDER_SESSION, order);
                session.setAttribute(AVAILABLE_ORDER_STATUSES_SESSION, availableStatuses);
            } else {
                session.setAttribute(WRONG_ORDER_ID_SESSION, true);
            }
            session.setAttribute(CURRENT_PAGE, Command.extractPage(request));
            router = new Router(PagePath.UPDATE_ORDER, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToUpdateOrderPageCommand was failed.", e);
            throw new CommandException("Try to execute GoToUpdateOrderPageCommand was failed.", e);
        }

        return router;
    }
}
