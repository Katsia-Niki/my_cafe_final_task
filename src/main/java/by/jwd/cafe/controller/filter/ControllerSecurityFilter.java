package by.jwd.cafe.controller.filter;

import by.jwd.cafe.controller.command.CommandType;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.RequestParameter;
import by.jwd.cafe.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumSet;

import static by.jwd.cafe.controller.command.SessionAttribute.CURRENT_ROLE;

/**
 * {@code ControllerSecurityFilter} class implements functional of {@link Filter}
 * Restricts access to the commands depending on the user's role.
 */

@WebFilter(filterName = "PreControllerFilter", urlPatterns = "/controller")
public class ControllerSecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();

        String commandName = httpServletRequest.getParameter(RequestParameter.COMMAND);
        if (commandName == null) {
            httpServletResponse.sendRedirect(PagePath.INDEX);
            return;
        }
        try {
            CommandType command = CommandType.valueOf(commandName.toUpperCase());
            UserRole userRole = session.getAttribute(CURRENT_ROLE) == null
                    ? UserRole.GUEST
                    : UserRole.valueOf(session.getAttribute(CURRENT_ROLE).toString());
            EnumSet<UserRole> roles = command.getAcceptableRole();
            if (roles.contains(userRole)) {
                chain.doFilter(request, response);
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void init(FilterConfig config) {
    }

    public void destroy() {
    }
}
