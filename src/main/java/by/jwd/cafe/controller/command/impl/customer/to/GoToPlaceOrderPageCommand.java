package by.jwd.cafe.controller.command.impl.customer.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.controller.command.SessionAttribute;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.PaymentType;
import by.jwd.cafe.service.OrderService;
import by.jwd.cafe.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class GoToPlaceOrderPageCommand implements Command {
    static Logger logger = LogManager.getLogger();
    private static final long ORDER_NOT_EARLIER_TIME_IN_MINUTES = 60;
    private static final long ORDER_NOT_LATER_TIME_IN_HOURS = 72;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.ORDER_CONFIRMED_MESSAGE);
        BigDecimal cartSum = new BigDecimal(session.getAttribute(SessionAttribute.CART_SUM).toString());
        String userId = session.getAttribute(SessionAttribute.CURRENT_USER_ID).toString();
        OrderService service = OrderServiceImpl.getInstance();
        BigDecimal pointsForAccount = service.calculateLoyaltyPoints(cartSum, PaymentType.ACCOUNT);
        BigDecimal pointsForCash = service.calculateLoyaltyPoints(cartSum, PaymentType.CASH);
        Router router;
        try {
            Map<String, String> orderData = createOrderData(pointsForAccount, pointsForCash, cartSum, userId);
            session.setAttribute(SessionAttribute.ORDER_DATA_SESSION, orderData);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.PLACE_ORDER);
            router = new Router(PagePath.PLACE_ORDER, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.info("Try to execute GoToPlaceOrderPageCommand was failed.", e);
            throw new CommandException("Try to execute GoToPlaceOrderPageCommand was failed.", e);
        }
        return router;
    }

    private Map<String, String> createOrderData(BigDecimal pointsForAccount,
                                                BigDecimal pointsForCash, BigDecimal cartSum, String userId) throws ServiceException {
        Map<String, String> orderData = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        now = now.truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime minPickUpTime = now.plusMinutes(ORDER_NOT_EARLIER_TIME_IN_MINUTES);
        LocalDateTime maxPickUpTime = now.plusHours(ORDER_NOT_LATER_TIME_IN_HOURS);

        orderData.put(SessionAttribute.POINTS_FOR_ACCOUNT, pointsForAccount.toString());
        orderData.put(SessionAttribute.POINTS_FOR_CASH, pointsForCash.toString());
        orderData.put(SessionAttribute.CART_SUM, cartSum.toString());
        orderData.put(SessionAttribute.MIN_PICK_UP_TIME, minPickUpTime.toString());
        orderData.put(SessionAttribute.MAX_PICK_UP_TIME, maxPickUpTime.toString());
        orderData.put(SessionAttribute.USER_ID_SESSION, userId);

        return orderData;
    }
}
