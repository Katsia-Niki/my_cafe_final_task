package by.jwd.cafe.controller.command.impl;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.jwd.cafe.controller.command.SessionAttribute.CURRENT_PAGE;
import static by.jwd.cafe.controller.command.SessionAttribute.CURRENT_ROLE;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Router router = null;
        if (session.getAttribute(CURRENT_ROLE) != null) {
            session.setAttribute(CURRENT_PAGE, PagePath.HOME);
            router = new Router(PagePath.HOME);
        } else {
            session.setAttribute(CURRENT_PAGE, PagePath.MAIN);
            router = new Router(PagePath.MAIN);
        }
        return router;
    }
}
