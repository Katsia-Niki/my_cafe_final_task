package by.jwd.cafe.validator;

import java.math.BigDecimal;
import java.util.Map;

/**
 * {@code UserValidator} interface represent functional to validate input data
 * for work with class {@link by.jwd.cafe.model.entity.User}
 */
public interface UserValidator {
    /**
     * {@code MAX_BALANCE} constant represent max balance value
     */
    BigDecimal MAX_AMOUNT = new BigDecimal(999999.99);
    /**
     * {@code WRONG_DATA_MARKER} constant represent string to mark wrong data
     */
    String WRONG_DATA_MARKER = "Wrong data";

    /**
     * {@code validateLogin} method to validate user login
     *
     * @param login - user login
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateLogin(String login);

    /**
     * {@code validateEmail} method to validate user email
     *
     * @param email - user email
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateEmail(String email);

    /**
     * {@code validatePassword} method to validate user password
     *
     * @param password - user password
     * @return result of validation, true - valid, false - not valid
     */
    boolean validatePassword(String password);

    /**
     * {@code validateName} method to validate username
     *
     * @param name - username
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateName(String name);

    /**
     * {@code validateAmount} method to validate amount
     *
     * @param amount - amount as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateAmount(String amount);

    /**
     * {@code validateRole} method to validate user role
     *
     * @param role - user role as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateRole(String role);

    /**
     * {@code validateUserDataCreate} method to validate user data to create new user
     *
     * @param userData - user data to create new user
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateUserDataCreate(Map<String, String> userData);

    /**
     * {@code validateUserDataUpdate} method to validate user data to update new user
     *
     * @param userData - user data to update new user
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateUserDataUpdate(Map<String, String> userData);
}
