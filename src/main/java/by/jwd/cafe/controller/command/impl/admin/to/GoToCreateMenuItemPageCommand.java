package by.jwd.cafe.controller.command.impl.admin.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.controller.command.SessionAttribute;
import by.jwd.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class GoToCreateMenuItemPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.CREATE_MENU_ITEM_RESULT);

        Map<String, String> menuItemData = new HashMap<>();
        session.setAttribute(SessionAttribute.MENU_ITEM_DATA_SESSION, menuItemData);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.CREATE_MENU_ITEM);
        return new Router(PagePath.CREATE_MENU_ITEM, Router.Type.REDIRECT);
    }
}
