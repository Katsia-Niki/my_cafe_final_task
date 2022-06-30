package by.jwd.cafe.controller.command.impl.admin;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.RequestParameter;
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

public class UpdateOrderCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(ORDER_SESSION);
        String role = session.getAttribute(CURRENT_ROLE).toString();
        String newStatus = request.getParameter(RequestParameter.ORDER_STATUS);

        OrderService service = OrderServiceImpl.getInstance();
        Router router;
        try {
            boolean result = service.updateStatus(role, newStatus, order);
            session.setAttribute(UPDATE_ORDER_RESULT, result);
            session.removeAttribute(ORDER_SESSION);
            session.removeAttribute(AVAILABLE_ORDER_STATUSES_SESSION);
            session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_ORDER);
            router = new Router(PagePath.UPDATE_ORDER, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute UpdateOrderCommand was failed.", e);
            throw new CommandException("Try to execute UpdateOrderCommand was failed.", e);
        }
        return router;
    }
}
