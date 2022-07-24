package by.jwd.cafe.controller.command.impl.admin;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.jwd.cafe.controller.command.RequestAttribute.*;
import static by.jwd.cafe.controller.command.RequestParameter.DATE_FROM;
import static by.jwd.cafe.controller.command.RequestParameter.DATE_TO;
import static by.jwd.cafe.controller.command.SessionAttribute.CURRENT_PAGE;

public class FindOrdersByDateRangeCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> searchParameters = new HashMap<>();
        searchParameters.put(DATE_FROM_ATTRIBUTE, request.getParameter(DATE_FROM));
        searchParameters.put(DATE_TO_ATTRIBUTE, request.getParameter(DATE_TO));

        OrderService service = OrderServiceImpl.getInstance();
        Router router;
        try {
            List<Order> orders = service.findOrdersByDateRange(searchParameters);
            Map<Integer, Boolean> canBeUpdatedMap = service.createCanBeUpdatedMap(orders);
            request.setAttribute(CAN_BE_UPDATED_MAP, canBeUpdatedMap);
            request.setAttribute(ORDER_LIST, orders);
            request.setAttribute(SEARCH_PARAMETERS_ATTRIBUTE, searchParameters);
            session.setAttribute(CURRENT_PAGE, Command.extractPage(request));
            router = new Router(PagePath.ORDER_MANAGEMENT);
        } catch (ServiceException e) {
            logger.error("Try to execute FindOrdersByDateRangeCommand was failed.", e);
            throw new CommandException("Try to execute FindOrdersByDateRangeCommand was failed.", e);
        }
        return router;
    }
}
