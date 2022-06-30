package by.jwd.cafe.controller.command.impl;

import by.jwd.cafe.controller.command.*;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.UserRole;
import by.jwd.cafe.service.UserService;
import by.jwd.cafe.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class UpdatePersonalDataCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> userData = (Map<String, String>) session.getAttribute(SessionAttribute.USER_DATA_SESSION);
        removeTempData(userData);
        updateUserDataFromRequest(request, userData);
        UserService service = UserServiceImpl.getInstance();
        Router router;
        try {
            int sizeBefore = userData.size();
            boolean result = service.updatePersonalData(userData);
            int sizeAfter = userData.size();
            if (sizeBefore == sizeAfter) {
                session.removeAttribute(SessionAttribute.USER_DATA_SESSION);
                session.setAttribute(SessionAttribute.UPDATE_PERSONAL_DATA_RESULT, result);
            } else {
                session.setAttribute(SessionAttribute.USER_DATA_SESSION, userData);
            }
            UserRole role = UserRole.valueOf(session.getAttribute(SessionAttribute.CURRENT_ROLE).toString());
            if (role == UserRole.ADMIN) {
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.ADMIN_ACCOUNT);
                router = new Router(PagePath.ADMIN_ACCOUNT, Router.Type.REDIRECT);
            } else {
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.CUSTOMER_ACCOUNT);
                router = new Router(PagePath.CUSTOMER_ACCOUNT, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute UpdatePersonalDataCommand was failed", e);
            throw new CommandException("Try to execute UpdatePersonalDataCommand was failed", e);
        }
        return router;
    }

    private void removeTempData(Map<String, String> userData) {
        userData.remove(SessionAttribute.WRONG_EMAIL_EXISTS_SESSION);
        userData.remove(SessionAttribute.WRONG_PASSWORD_SESSION);
        userData.remove(SessionAttribute.WRONG_FIRST_NAME_SESSION);
        userData.remove(SessionAttribute.WRONG_LAST_NAME_SESSION);
        userData.remove(SessionAttribute.WRONG_EMAIL_SESSION);
        userData.remove(SessionAttribute.PASSWORD_SESSION);
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(SessionAttribute.NEW_EMAIL_SESSION, request.getParameter(RequestParameter.EMAIL));
        userData.put(SessionAttribute.FIRST_NAME_SESSION, request.getParameter(RequestParameter.FIRST_NAME));
        userData.put(SessionAttribute.LAST_NAME_SESSION, request.getParameter(RequestParameter.LAST_NAME));
        userData.put(SessionAttribute.ROLE_NAME_SESSION, request.getParameter(RequestParameter.ROLE));
        userData.put(SessionAttribute.IS_ACTIVE_SESSION, request.getParameter(RequestParameter.USER_IS_ACTIVE));
        userData.put(SessionAttribute.PASSWORD_SESSION, request.getParameter(RequestParameter.PASS));
    }
}
