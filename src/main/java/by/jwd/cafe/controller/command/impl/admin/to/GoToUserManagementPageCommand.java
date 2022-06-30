package by.jwd.cafe.controller.command.impl.admin.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static by.jwd.cafe.controller.command.RequestParameter.USER_ID_TO_EDIT;
import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class GoToUserManagementPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String userIdToEdit = request.getParameter(USER_ID_TO_EDIT);
        List<String> userRoles = Stream.of(UserRole.values())
                .map(role -> role.name()).toList();
        session.setAttribute(ALL_USER_ROLES_SESSION, userRoles);
        UserService service = UserServiceImpl.getInstance();
        Router router;
        try {
            Optional<User> optionalUser = service.findUserById(userIdToEdit);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER_TO_EDIT_SESSION, user);
            }
            session.setAttribute(CURRENT_PAGE, Command.extractPage(request));
            router = new Router(PagePath.USER_MANAGEMENT);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToUserManagementPageCommand was failed.", e);
            throw new CommandException("Try to execute GoToUserManagementPageCommand was failed.", e);
        }
        return router;
    }
}
