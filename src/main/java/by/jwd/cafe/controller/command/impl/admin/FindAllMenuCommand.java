package by.jwd.cafe.controller.command.impl.admin;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.service.MenuItemService;
import by.jwd.cafe.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.jwd.cafe.controller.command.RequestAttribute.CURRENT_PAGE_NUMBER;
import static by.jwd.cafe.controller.command.RequestAttribute.NUMBER_OF_PAGES;
import static by.jwd.cafe.controller.command.RequestParameter.DEFAULT_PAGE_NUMBER;
import static by.jwd.cafe.controller.command.RequestParameter.PAGE;
import static by.jwd.cafe.controller.command.SessionAttribute.CURRENT_PAGE;
import static by.jwd.cafe.controller.command.SessionAttribute.MENU_ITEM_ALL_SESSION;

public class FindAllMenuCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        int currentPageNumber = parseIntParameter(request.getParameter(PAGE)) != 0
                ? parseIntParameter(request.getParameter(PAGE))
                : DEFAULT_PAGE_NUMBER;
        MenuItemService service = MenuItemServiceImpl.getInstance();
        Router router;
        try {
            List<MenuItem> menu = service.findAllMenuItems(currentPageNumber);
            int numberOfPages = service.findNumberOfPages();
            session.setAttribute(MENU_ITEM_ALL_SESSION, menu);
            session.setAttribute(CURRENT_PAGE, Command.extractPage(request));
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE_NUMBER, currentPageNumber);
            router = new Router(PagePath.ALL_MENU);
        } catch (ServiceException e) {
            logger.error("Try to execute FindAllMenuCommand was failed.", e);
            throw new CommandException("Try to execute FindAllMenuCommand was failed.", e);
        }
        return router;
    }
}
