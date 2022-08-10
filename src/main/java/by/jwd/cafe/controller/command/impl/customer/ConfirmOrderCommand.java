package by.jwd.cafe.controller.command.impl.customer;

import by.jwd.cafe.controller.command.*;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.service.OrderService;
import by.jwd.cafe.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> orderData = (Map<String, String>) session.getAttribute(SessionAttribute.ORDER_DATA_SESSION);
        removeTempData(orderData);
        updateOrderDataFromRequest(request, orderData);

        Map<MenuItem, Integer> cart = (Map<MenuItem, Integer>) session.getAttribute(SessionAttribute.CART);
        OrderService service = OrderServiceImpl.getInstance();
        Router router;
        try {
            boolean isConfirmed = service.createOrder(orderData, cart);
            if (isConfirmed) {
                session.setAttribute(SessionAttribute.CART, new HashMap<>());
            }
            session.setAttribute(SessionAttribute.ORDER_CONFIRMED_MESSAGE, isConfirmed);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.PLACE_ORDER);
            router = new Router(PagePath.PLACE_ORDER);
        } catch (ServiceException e) {
            logger.error("Try to execute ConfirmOrderCommand was failed.", e);
            throw new CommandException("Try to execute ConfirmOrderCommand was failed.", e);
        }
        return router;
    }

    private void updateOrderDataFromRequest(HttpServletRequest request, Map<String, String> orderData) {
        String paymentTypeStr = request.getParameter(RequestParameter.PAYMENT_TYPE);
        String pickUpTimeStr = request.getParameter(RequestParameter.PICK_UP_TIME);
        orderData.put(SessionAttribute.PAYMENT_TYPE_SESSION, paymentTypeStr);
        orderData.put(SessionAttribute.PICK_UP_TIME_SESSION, pickUpTimeStr);
    }

    private void removeTempData(Map<String, String> orderData) {
        orderData.remove(SessionAttribute.WRONG_PAYMENT_TYPE_SESSION);
        orderData.remove(SessionAttribute.WRONG_PICK_UP_TIME_SESSION);
        orderData.remove(SessionAttribute.NOT_ENOUGH_MONEY_SESSION);
        orderData.remove(SessionAttribute.NOT_ENOUGH_LOYALTY_POINTS_SESSION);
    }
}
