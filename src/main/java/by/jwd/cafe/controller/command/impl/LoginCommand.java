package by.jwd.cafe.controller.command.impl;

import by.jwd.cafe.controller.command.*;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.User;
import by.jwd.cafe.service.UserService;
import by.jwd.cafe.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class LoginCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> userData = (Map<String, String>) session.getAttribute(USER_DATA_SESSION);
        removeTempData(userData);
        updateUserDataFromRequest(request, userData);
        UserService userService = UserServiceImpl.getInstance();
        Router router;
        try {
            Optional<User> optionalUser = userService.authenticate(userData);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.removeAttribute(USER_DATA_SESSION);
                session.setAttribute(CURRENT_USER_ID, user.getUserId());
                session.setAttribute(CURRENT_LOGIN_SESSION, user.getLogin());
                session.setAttribute(CURRENT_ROLE, user.getRole().toString());
                session.setAttribute(CURRENT_USER_IS_ACTIVE, user.isActive());
                session.setAttribute(CURRENT_BALANCE, user.getBalance());
                session.setAttribute(CURRENT_LOYALTY_POINTS, user.getLoyaltyPoints());
                request.setAttribute(RequestAttribute.USER, user.getFirstName());
                session.setAttribute(CURRENT_PAGE, PagePath.HOME);
                router = new Router(PagePath.HOME);
            } else {
                session.setAttribute(USER_DATA_SESSION, userData);
                session.setAttribute(CURRENT_PAGE, PagePath.LOGIN);
                router = new Router(PagePath.LOGIN);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }

    private void removeTempData(Map<String, String> userData) {
        userData.remove(WRONG_LOGIN_OR_PASSWORD_SESSION);
        userData.remove(NOT_FOUND_SESSION);
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(LOGIN_SESSION, request.getParameter(RequestParameter.LOGIN));
        userData.put(PASSWORD_SESSION, request.getParameter(RequestParameter.PASS));
    }
}
