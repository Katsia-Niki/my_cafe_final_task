package by.jwd.cafe.controller.command.impl.admin;

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

import static by.jwd.cafe.controller.command.SessionAttribute.UPDATE_USER_RESULT;

public class UpdateUserStatusCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int userId = parseIntParameter(request.getParameter(RequestParameter.USER_ID));
        boolean isActive = Boolean.parseBoolean(request.getParameter(RequestParameter.USER_IS_ACTIVE));
        boolean newUserStatus = !isActive;
        UserService service = UserServiceImpl.getInstance();
        Router router;
        try {
            boolean result = service.updateUserStatus(userId, newUserStatus);
            session.setAttribute(UPDATE_USER_RESULT, result);
            router = new Router(PagePath.USERS);
        } catch (ServiceException e) {
            logger.error("Try to execute UpdateUserStatusCommand was failed.", e);
            throw new CommandException("Try to execute UpdateUserStatusCommand was failed.", e);
        }
        return router;
    }
}
