package by.jwd.cafe.controller.command.impl.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.controller.command.SessionAttribute;
import by.jwd.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class GoToRegistrationPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.REGISTRATION_RESULT);
        Map<String, String> userData = new HashMap<>();
        session.setAttribute(SessionAttribute.USER_DATA_SESSION, userData);
        String currentPage = Command.extractPage(request);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, currentPage);
        return new Router(PagePath.REGISTRATION);
    }
}
