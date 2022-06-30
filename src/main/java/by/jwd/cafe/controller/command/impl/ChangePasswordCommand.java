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

public class ChangePasswordCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> userData = (Map<String, String>) session.getAttribute(USER_DATA_SESSION);
        removeWrongMessage(userData);
        updateUserDataFromRequest(request, userData);
        UserService service = UserServiceImpl.getInstance();
        Router router;
        try {
            int sizeBefore = userData.size();
            boolean result = service.changePassword(userData);
            int sizeAfter = userData.size();
            if (sizeBefore == sizeAfter) {
                session.removeAttribute(USER_DATA_SESSION);
                session.setAttribute(CHANGE_PASSWORD_RESULT, result);
            } else {
                session.setAttribute(USER_DATA_SESSION, userData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.CHANGE_PASSWORD);
            router = new Router(PagePath.CHANGE_PASSWORD, Router.Type.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute ChangePasswordCommand was failed.", e);
            throw new CommandException("Try to execute ChangePasswordCommand was failed.", e);
        }
        return router;
    }

    private void removeWrongMessage(Map<String, String> userData) {
        userData.remove(WRONG_PASSWORD_SESSION);
        userData.remove(WRONG_NEW_PASSWORD_SESSION);
        userData.remove(WRONG_OLD_PASSWORD_SESSION);
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(PASSWORD_SESSION, request.getParameter(RequestParameter.PASS));
        userData.put(NEW_PASSWORD_SESSION, request.getParameter(RequestParameter.NEW_PASS));
    }
}
