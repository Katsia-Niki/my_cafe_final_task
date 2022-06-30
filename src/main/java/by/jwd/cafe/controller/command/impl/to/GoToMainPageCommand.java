package by.jwd.cafe.controller.command.impl.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.controller.command.SessionAttribute;
import by.jwd.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GoToMainPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = Command.extractPage(request);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, currentPage);
        return session.getAttribute(SessionAttribute.CURRENT_LOGIN_SESSION) != null
                ? new Router(PagePath.HOME)
                : new Router(PagePath.MAIN);
    }
}
