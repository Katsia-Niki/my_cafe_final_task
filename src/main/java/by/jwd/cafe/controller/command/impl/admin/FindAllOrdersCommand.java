package by.jwd.cafe.controller.command.impl.admin;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.RequestAttribute;
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
import java.util.Map;

import static by.jwd.cafe.controller.command.SessionAttribute.CURRENT_PAGE;

public class FindAllOrdersCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        OrderService service = OrderServiceImpl.getInstance();

        Router router;
        try {
            List<Order> orders = service.findAllOrders();
            Map<Integer, Boolean> canBeUpdatedMap = service.createCanBeUpdatedMap(orders);
            request.setAttribute(RequestAttribute.ORDER_LIST, orders);
            request.setAttribute(RequestAttribute.CAN_BE_UPDATED_MAP, canBeUpdatedMap);
            session.setAttribute(CURRENT_PAGE, Command.extractPage(request));
            router = new Router(PagePath.ORDER_MANAGEMENT);
        } catch (ServiceException e) {
            logger.error("Try to execute FindAllOrdersCommand was failed.", e);
            throw new CommandException("Try to execute FindAllOrdersCommand was failed.", e);
        }
        return router;
    }
}
