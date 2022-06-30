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

import java.util.List;
import java.util.Map;

import static by.jwd.cafe.controller.command.RequestAttribute.CAN_BE_CANCELLED_MAP;
import static by.jwd.cafe.controller.command.RequestAttribute.ORDER_LIST;
import static by.jwd.cafe.controller.command.SessionAttribute.CURRENT_PAGE;
import static by.jwd.cafe.controller.command.SessionAttribute.CURRENT_USER_ID;

public class FindOrderByUserIdCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(CURRENT_USER_ID);
        OrderService service = OrderServiceImpl.getInstance();
        Router router;
        try {
            List<Order> orderList = service.findOrderByUserId(userId);
            if (!orderList.isEmpty()) {
                Map<Integer, Boolean> canBeCancelledMap = service.createСanBeCancelledMap(orderList);
                request.setAttribute(CAN_BE_CANCELLED_MAP, canBeCancelledMap);
            }
            request.setAttribute(ORDER_LIST, orderList);

            session.setAttribute(CURRENT_PAGE, Command.extractPage(request));
            router = new Router(PagePath.CUSTOMER_ORDERS);
        } catch (ServiceException e) {
            logger.error("Try to execute FindOrderByUserIdCommand was failed.", e);
            throw new CommandException("Try to execute FindOrderByUserIdCommand was failed.", e);
        }
        return router;
    }
}
