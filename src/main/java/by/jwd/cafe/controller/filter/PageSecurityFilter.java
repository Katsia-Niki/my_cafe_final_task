package by.jwd.cafe.controller.filter;


import by.jwd.cafe.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static by.jwd.cafe.controller.command.PagePath.*;
import static by.jwd.cafe.controller.command.SessionAttribute.CURRENT_ROLE;

/**
 * {@code PageSecurityFilter} class implements functional of {@link Filter}
 * Restricts access to the page depending on the user's role.
 */
@WebFilter(urlPatterns = {"/pages/*"})
public class PageSecurityFilter implements Filter {
    private Set<String> guestPages;
    private Set<String> customerPages;
    private Set<String> adminPages;
    private Set<String> allPages;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        guestPages = Set.of(LOGIN,
                MAIN,
                MENU,
                REGISTRATION,
                CONTACT);
        customerPages = Set.of(CONTACT,
                CANCEL_ORDER,
                CART,
                CUSTOMER_ACCOUNT,
                CUSTOMER_ORDERS,
                CHANGE_PASSWORD,
                HOME,
                LOGIN,
                MAIN,
                MENU,
                PLACE_ORDER,
                REGISTRATION,
                REFILL_BALANCE);
        adminPages = Set.of(ADMIN_ACCOUNT,
                ALL_MENU,
                CONTACT,
                CHANGE_PASSWORD,
                CREATE_MENU_ITEM,
                EDIT_MENU,
                HOME,
                LOGIN,
                MAIN,
                MENU,
                ORDER_MANAGEMENT,
                REGISTRATION,
                UPDATE_ORDER,
                USERS);
        allPages = new HashSet<>();
        allPages.addAll(guestPages);
        allPages.addAll(customerPages);
        allPages.addAll(adminPages);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getServletPath();
        boolean isPageExist = allPages.stream().anyMatch(requestURI::contains);

        if (isPageExist) {
            HttpSession session = request.getSession();
            UserRole role = session.getAttribute(CURRENT_ROLE) == null
                    ? UserRole.GUEST
                    : UserRole.valueOf(session.getAttribute(CURRENT_ROLE).toString());
            boolean isAcceptable =
                    switch (role) {
                        case CUSTOMER -> customerPages.stream().anyMatch(requestURI::contains);
                        case ADMIN -> adminPages.stream().anyMatch(requestURI::contains);
                        default -> guestPages.stream().anyMatch(requestURI::contains);
                    };
            if (!isAcceptable) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
