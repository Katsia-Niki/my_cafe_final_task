package by.jwd.cafe.controller.command.impl;

import by.jwd.cafe.controller.command.Command;
import by.jwd.cafe.controller.command.RequestParameter;
import by.jwd.cafe.controller.command.Router;
import by.jwd.cafe.controller.command.SessionAttribute;
import by.jwd.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeLanguageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    private enum Language {
        EN("en_EN"),
        RU("ru_RU");
        private String locale;

        Language(String locale) {
            this.locale = locale;
        }

        public String getLocale() {
            return locale;
        }
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = session.getAttribute(SessionAttribute.CURRENT_PAGE).toString();
        Language newLanguage = Language.valueOf(request.getParameter(RequestParameter.LANGUAGE));
        logger.info("Language will be changed to " + newLanguage.getLocale());
        switch (newLanguage) {
            case EN -> session.setAttribute(SessionAttribute.LOCALE, Language.EN.getLocale());
            default -> session.setAttribute(SessionAttribute.LOCALE, Language.RU.getLocale());
        }
        return new Router(currentPage);
    }
}
