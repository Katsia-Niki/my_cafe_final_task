package by.jwd.cafe.controller.command.impl;

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

public class RegistrationCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> userData = (Map<String, String>) session.getAttribute(USER_DATA_SESSION);
        removeTempData(userData);
        updateUserDataFromRequest(request, userData);
        UserService service = UserServiceImpl.getInstance();
        Router router;
        try {
            int sizeBefore = userData.size();
            boolean result = service.createNewAccount(userData);
            int sizeAfter = userData.size();
            if (sizeBefore == sizeAfter) {
                session.removeAttribute(USER_DATA_SESSION);
                session.setAttribute(REGISTRATION_RESULT, result);
                router = new Router(PagePath.REGISTRATION);
            } else {
                session.setAttribute(USER_DATA_SESSION, userData);
                router = new Router(PagePath.REGISTRATION);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.REGISTRATION);
        } catch (ServiceException e) {
            logger.error("Try to create new account was failed.", e);
            throw new CommandException("Try to create new account was failed.", e);
        }
        return router;
    }

    private void removeTempData(Map<String, String> userData) {
        userData.remove(WRONG_LOGIN_SESSION);
        userData.remove(WRONG_EMAIL_SESSION);
        userData.remove(WRONG_PASSWORD_SESSION);
        userData.remove(WRONG_FIRST_NAME_SESSION);
        userData.remove(WRONG_LAST_NAME_SESSION);
        userData.remove(MISMATCH_PASSWORDS_SESSION);
        userData.remove(WRONG_EMAIL_EXISTS_SESSION);
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(EMAIL_SESSION, request.getParameter(RequestParameter.EMAIL));
        userData.put(LOGIN_SESSION, request.getParameter(RequestParameter.LOGIN));
        userData.put(FIRST_NAME_SESSION, request.getParameter(RequestParameter.FIRST_NAME));
        userData.put(LAST_NAME_SESSION, request.getParameter(RequestParameter.LAST_NAME));
        userData.put(PASSWORD_SESSION, request.getParameter(RequestParameter.PASS));
        userData.put(REPEAT_PASSWORD_SESSION, request.getParameter(RequestParameter.REPEAT_PASSWORD));
    }
}
