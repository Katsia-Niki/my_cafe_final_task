package by.jwd.cafe.controller.command.impl.customer;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.RequestParameter;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.service.UserService;
import by.jwd.cafe.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class RefillBalanceCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> balanceData = (Map<String, String>) session.getAttribute(BALANCE_DATA_SESSION);
        removeTempData(balanceData);
        updateDataFromRequest(request, balanceData);
        UserService service = UserServiceImpl.getInstance();
        Router router;
        try {
            int sizeBefore = balanceData.size();
            boolean result = service.refillBalance(balanceData);
            int sizeAfter = balanceData.size();
            if (sizeBefore == sizeAfter) {
                session.setAttribute(REFILL_BALANCE_RESULT, result);
                session.removeAttribute(BALANCE_DATA_SESSION);
            } else {
                session.setAttribute(BALANCE_DATA_SESSION, balanceData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.REFILL_BALANCE);
            router = new Router(PagePath.REFILL_BALANCE, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.info("Try to execute RefillBalanceCommand was failed.", e);
            throw new CommandException("Try to execute RefillBalanceCommand was failed.", e);
        }
        return router;
    }

    private void removeTempData(Map<String, String> balanceData) {
        balanceData.remove(WRONG_AMOUNT_OVERSIZE_SESSION);
        balanceData.remove(WRONG_AMOUNT_SESSION);
    }

    private void updateDataFromRequest(HttpServletRequest request, Map<String, String> balanceData) {
        balanceData.put(REFILL_AMOUNT_SESSION, request.getParameter(RequestParameter.REFILL_AMOUNT));
    }
}
