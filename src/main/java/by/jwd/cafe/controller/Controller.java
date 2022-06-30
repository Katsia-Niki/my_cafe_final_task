package by.jwd.cafe.controller;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.CommandProvider;
import by.jwd.cafe.controller.command.RequestParameter;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * {@code Controller} class extends functional of {@link HttpServlet}
 * Processes all requests after filtering.
 */

@WebServlet(name = "controller", urlPatterns = {"/controller"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024,
        maxRequestSize = 1024 * 1024)
public class Controller extends HttpServlet {
    static Logger logger = LogManager.getLogger();

    public void init() {
        logger.log(Level.INFO, "Servlet init: " + this.getServletInfo());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        Command command = CommandProvider.of(commandName);
        logger.info(commandName); // fixme
        try {
            Router router = command.execute(request);
            String toPage = router.getPage();

            switch (router.getType()) {
                case FORWARD:
                    request.getRequestDispatcher(toPage).forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(toPage);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (CommandException e) {
            logger.error("Error while command execution " + commandName, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void destroy() {
        logger.log(Level.INFO, "Servlet destroyed: " + this.getServletName());
    }
}