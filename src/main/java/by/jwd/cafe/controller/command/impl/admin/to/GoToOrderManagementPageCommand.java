package by.jwd.cafe.controller.command.impl.admin.to;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.PagePath;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.exception.CommandException;
import by.jwd.cafe.model.entity.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static by.jwd.cafe.controller.command.RequestAttribute.SEARCH_ATTRIBUTE;
import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class GoToOrderManagementPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        List<String> statuses = Stream.of(Order.Status.values())
                .map(status -> status.name())
                .toList();
        List<Boolean> isPaid = new ArrayList<>();
        isPaid.add(Boolean.TRUE);
        isPaid.add(Boolean.FALSE);

        request.setAttribute(SEARCH_ATTRIBUTE, true);
        session.setAttribute(IS_PAID_SESSION, isPaid);
        session.setAttribute(ALL_ORDER_STATUSES_SESSION, statuses);
        session.setAttribute(CURRENT_PAGE, Command.extractPage(request));

        return new Router(PagePath.ORDER_MANAGEMENT);
    }
}
