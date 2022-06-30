package by.jwd.cafe.controller.command.impl.admin;

import by.jwd.cafe.controller.command.*;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.service.MenuItemService;
import by.jwd.cafe.service.impl.MenuItemServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class UploadImageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        MenuItemService service = MenuItemServiceImpl.getInstance();
        Router router;
        try {
            Part part = request.getPart(RequestParameter.IMAGE);
            try (InputStream inputStream = part.getInputStream()) {
                byte[] newImage = inputStream.readAllBytes();
                String menuItemId = request.getParameter(RequestParameter.MENU_ITEM_ID);
                boolean result = service.createImage(newImage, menuItemId);
                session.setAttribute(SessionAttribute.UPLOAD_RESULT, result);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.EDIT_MENU);
                router = new Router(PagePath.EDIT_MENU, Router.Type.REDIRECT);
            }
        } catch (ServletException | IOException | ServiceException e) {
            logger.error("Try to execute UploadImageCommand was failed.", e);
            throw new CommandException("Try to execute UploadImageCommand was failed.", e);
        }
        return router;
    }
}
