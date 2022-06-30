package by.jwd.cafe.controller.command;

import by.jwd.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * {@code Command} interface represent functional of command
 */
@FunctionalInterface
public interface Command {
    String CONTROLLER_PART = "/controller?";

    /**
     * Execute command
     *
     * @param request - request from controller, type {@link Router}
     * @throws CommandException - if service method throw {@link by.jwd.cafe.exception.ServiceException}
     */
    Router execute(HttpServletRequest request) throws CommandException;


    /**
     * Method helps to extract part of current page on forward route from request
     *
     * @param request - type of @{link HttpServletRequest}
     * @return current page
     */
    static String extractPage(HttpServletRequest request) {
        String commandPart = request.getQueryString();
        String currentPage = CONTROLLER_PART + commandPart;
        return currentPage;
    }

    /**
     * Method helps to parse parameters from String type to int type.
     *
     * @param input request parameter String type
     * @return request parameter int type
     */
    default int parseIntParameter(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
