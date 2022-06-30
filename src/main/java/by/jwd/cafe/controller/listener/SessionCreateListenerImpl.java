package by.jwd.cafe.controller.listener;

import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.model.entity.MenuItem;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

/**
 * {@code SessionCreateListenerImpl} class implements functional of {@link HttpSessionListener}
 */
@WebListener
public class SessionCreateListenerImpl implements HttpSessionListener {
    private static String DEFAULT_LOCALE = "ru_RU";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Map<MenuItem, Integer> cart = new HashMap<>();
        session.setAttribute(CART, cart);
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(CURRENT_PAGE, PagePath.INDEX);
        LocalDate today = LocalDate.now();
        session.setAttribute(TODAY, today.toString());
    }
}
