package by.jwd.cafe.controller.command.impl.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.controller.command.SessionAttribute;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.User;
import by.jwd.cafe.model.entity.UserRole;
import by.jwd.cafe.service.UserService;
import by.jwd.cafe.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GoToAccountPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.UPDATE_PERSONAL_DATA_RESULT);
        UserService service = UserServiceImpl.getInstance();
        String roleName = session.getAttribute(SessionAttribute.CURRENT_ROLE).toString();
        UserRole userRole = UserRole.valueOf(roleName);
        String userId = session.getAttribute(SessionAttribute.CURRENT_USER_ID).toString();

        Router router;
        try {
            Optional<User> optionalUser = service.findUserById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Map<String, String> userData = new HashMap<>();
                userData.put(SessionAttribute.USER_ID_SESSION, userId);
                userData.put(SessionAttribute.LOGIN_SESSION, user.getLogin());
                userData.put(SessionAttribute.FIRST_NAME_SESSION, user.getFirstName());
                userData.put(SessionAttribute.LAST_NAME_SESSION, user.getLastName());
                userData.put(SessionAttribute.EMAIL_SESSION, user.getEmail());
                userData.put(SessionAttribute.BALANCE_SESSION, user.getBalance().toString());
                userData.put(SessionAttribute.LOYALTY_POINTS_SESSION, user.getLoyaltyPoints().toString());
                userData.put(SessionAttribute.IS_ACTIVE_SESSION, String.valueOf(user.isActive()));
                userData.put(SessionAttribute.ROLE_NAME_SESSION, user.getRole().toString());
                session.setAttribute(SessionAttribute.USER_DATA_SESSION, userData);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, Command.extractPage(request));
            } else {
                session.setAttribute(SessionAttribute.NOT_FOUND_SESSION, true);
            }
            router = userRole == UserRole.ADMIN
                    ? new Router(PagePath.ADMIN_ACCOUNT, Router.Type.REDIRECT)
                    : new Router(PagePath.CUSTOMER_ACCOUNT, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToAccountPageCommand was failed.", e);
            throw new CommandException("Try to execute GoToAccountPageCommand was failed.", e);
        }
        return router;
    }
}
