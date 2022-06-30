package by.jwd.cafe.validator.impl;

import by.jwd.cafe.model.entity.UserRole;
import by.jwd.cafe.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.jwd.cafe.controller.command.SessionAttribute.*;


public final class UserValidatorImpl implements UserValidator {
    static Logger logger = LogManager.getLogger();
    private static final String EMAIL_REGEX =
            "[\\d\\p{Alpha}]([\\d\\p{Alpha}_\\-\\.]*)[\\d\\p{Alpha}_\\-]@[\\d\\p{Alpha}_\\-]{2,}\\.\\p{Alpha}{2,6}";
    private static final String PASSWORD_REGEX = "^[\\wА-я\\.\\-]{3,45}$";
    private static final String AMOUNT_REGEX = "\\d{1,4}(\\.\\d\\d)??";
    private static final String NAME_REGEX = "[\\wА-яЁё\\s]{2,20}";
    private static final String LOGIN_REGEX = "[\\w-]{2,45}";
    private static final UserValidatorImpl instance = new UserValidatorImpl();

    private UserValidatorImpl() {

    }

    public static UserValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean validateLogin(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    @Override
    public boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean validateName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    @Override
    public boolean validateAmount(String amount) {
        return amount != null && amount.matches(AMOUNT_REGEX);
    }

    @Override
    public boolean validateRole(String role) {
        try {
            UserRole.valueOf(role);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validateUserDataCreate(Map<String, String> userData) {
        String login = userData.get(LOGIN_SESSION);
        String password = userData.get(PASSWORD_SESSION);
        String repeatPassword = userData.get(REPEAT_PASSWORD_SESSION);
        String firstName = userData.get(FIRST_NAME_SESSION);
        String lastName = userData.get(LAST_NAME_SESSION);
        String email = userData.get(EMAIL_SESSION);

        boolean isValid = true;
        if (!validateLogin(login)) {
            userData.put(WRONG_LOGIN_SESSION, WRONG_DATA_MARKER);
            logger.info("Invalid login.");
            isValid = false;
        }
        if (!validatePassword(password)) {
            userData.put(WRONG_PASSWORD_SESSION, WRONG_DATA_MARKER);
            logger.info("Invalid password.");
            isValid = false;
        }
        if (!password.equals(repeatPassword)) {
            userData.put(MISMATCH_PASSWORDS_SESSION, WRONG_DATA_MARKER);
            logger.info("Mismatch passwords.");
            isValid = false;
        }
        if (!validateName(firstName)) {
            userData.put(WRONG_FIRST_NAME_SESSION, WRONG_DATA_MARKER);
            logger.info("Invalid first name.");
            isValid = false;
        }
        if (!validateName(lastName)) {
            userData.put(WRONG_LAST_NAME_SESSION, WRONG_DATA_MARKER);
            logger.info("Invalid last name.");
            isValid = false;
        }
        if (!validateEmail(email)) {
            userData.put(WRONG_EMAIL_SESSION, WRONG_DATA_MARKER);
            logger.info("Invalid email.");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateUserDataUpdate(Map<String, String> userData) {
        String firstName = userData.get(FIRST_NAME_SESSION);
        String lastName = userData.get(LAST_NAME_SESSION);
        String password = userData.get(PASSWORD_SESSION);
        String newEmail = userData.get(NEW_EMAIL_SESSION);

        boolean isValid = true;
        if (password != null && !validatePassword(password)) {
            userData.put(WRONG_PASSWORD_SESSION, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (firstName != null && !validateName(firstName)) {
            userData.put(WRONG_FIRST_NAME_SESSION, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (lastName != null && !validateName(lastName)) {
            userData.put(WRONG_LAST_NAME_SESSION, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (newEmail != null && !validateEmail(newEmail)) {
            userData.put(WRONG_EMAIL_SESSION, WRONG_DATA_MARKER);
            isValid = false;
        }
        return isValid;
    }
}
