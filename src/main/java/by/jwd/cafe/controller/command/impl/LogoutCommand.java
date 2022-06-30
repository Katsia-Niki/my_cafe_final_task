package by.jwd.cafe.controller.command.impl;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new Router(PagePath.INDEX, Router.Type.REDIRECT);
    }
}
