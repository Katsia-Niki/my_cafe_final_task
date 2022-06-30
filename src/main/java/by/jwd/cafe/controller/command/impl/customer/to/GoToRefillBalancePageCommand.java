package by.jwd.cafe.controller.command.impl.customer.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.User;
import by.jwd.cafe.service.UserService;
import by.jwd.cafe.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class GoToRefillBalancePageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(REFILL_BALANCE_RESULT);
        UserService service = UserServiceImpl.getInstance();
        String userId = session.getAttribute(CURRENT_USER_ID).toString();
        Router router;
        try {
            Optional<User> optionalUser = service.findUserById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                BigDecimal balance = user.getBalance();
                Map<String, String> balanceData = new HashMap<>();
                balanceData.put(BALANCE_SESSION, balance.toString());
                balanceData.put(USER_ID_SESSION, userId);
                session.setAttribute(BALANCE_DATA_SESSION, balanceData);
            } else {
                session.setAttribute(NOT_FOUND_SESSION, true);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.REFILL_BALANCE);
            router = new Router(PagePath.REFILL_BALANCE, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.info("Try to execute GoToRefillBalanceCommand was failed.", e);
            throw new CommandException("Try to execute GoToRefillBalanceCommand was failed.", e);
        }
        return router;
    }
}
