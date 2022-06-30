package by.jwd.cafe.service.impl;

import by.jwd.cafe.exception.DaoException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.dao.UserDao;
import by.jwd.cafe.model.dao.impl.UserDaoImpl;
import by.jwd.cafe.model.entity.User;
import by.jwd.cafe.model.entity.UserRole;
import by.jwd.cafe.service.UserService;
import by.jwd.cafe.util.PasswordEncryptor;
import by.jwd.cafe.validator.UserValidator;
import by.jwd.cafe.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.jwd.cafe.controller.command.SessionAttribute.*;
import static by.jwd.cafe.validator.UserValidator.WRONG_DATA_MARKER;

public class UserServiceImpl implements UserService {
    static Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance = new UserServiceImpl();
    private UserDao userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> authenticate(Map<String, String> userData) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        String login = userData.get(LOGIN_SESSION);
        String password = userData.get(PASSWORD_SESSION);
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validateLogin(login) || !validator.validatePassword(password)) {
            userData.put(WRONG_LOGIN_OR_PASSWORD_SESSION, WRONG_DATA_MARKER);
            return optionalUser;
        }
        try {
            String secretPassword = PasswordEncryptor.md5Apache(password);
            optionalUser = userDao.findUserByLoginAndPassword(login, secretPassword);
            if (optionalUser.isEmpty()) {
                logger.info("User " + login + " was not found in cafe.users.");
                userData.put(NOT_FOUND_SESSION, WRONG_DATA_MARKER);
            }
        } catch (DaoException e) {
            logger.error("Try to authenticate user " + login + password + " was failed.", e);
            throw new ServiceException("Try to authenticate user " + login + password + " was failed.", e);
        }
        return optionalUser;
    }

    @Override
    public boolean createNewAccount(Map<String, String> userData) throws ServiceException {
        boolean isCreated = false;
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validateUserDataCreate(userData)) {
            return isCreated;
        }
        String login = userData.get(LOGIN_SESSION);
        String password = userData.get(PASSWORD_SESSION);
        String firstName = userData.get(FIRST_NAME_SESSION);
        String lastName = userData.get(LAST_NAME_SESSION);
        String email = userData.get(EMAIL_SESSION);
        try {
            if (userDao.isEmailExist(email)) {
                userData.put(WRONG_EMAIL_EXISTS_SESSION, WRONG_DATA_MARKER);
                return isCreated;
            }
            if (userDao.isLoginExist(login)) {
                userData.put(WRONG_LOGIN_EXISTS_SESSION, WRONG_DATA_MARKER);
                return isCreated;
            }
            String secretPassword = PasswordEncryptor.md5Apache(password);
            User newUser = new User.UserBuilder()
                    .withLogin(login)
                    .withPassword(secretPassword)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withEmail(email)
                    .withBalance(BigDecimal.ZERO)
                    .withLoyaltyPoints(BigDecimal.ZERO)
                    .withIsActive(true)
                    .withUserRole(UserRole.CUSTOMER)
                    .build();
            isCreated = userDao.add(newUser);
        } catch (DaoException e) {
            logger.error("Try to create new account was failed.", e);
            throw new ServiceException("Try to create new account was failed.", e);
        }
        return isCreated;
    }

    @Override
    public List<User> findAllUsers(int currentPageNumber) throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAllPaginatedUsers(currentPageNumber);
        } catch (DaoException e) {
            logger.error("Try to find all users was failed.", e);
            throw new ServiceException("Try to find all users was failed.", e);
        }
        return users;
    }

    @Override
    public int findNumberOfPages() throws ServiceException {
        int numberOfPages;
        try {
            numberOfPages = userDao.findNumberOfAllUsers();
        } catch (DaoException e) {
            logger.error("Try to count pages was failed.", e);
            throw new ServiceException("Try to count pages was failed.", e);
        }
        return numberOfPages;
    }

    @Override
    public boolean changePassword(Map<String, String> passwordData) throws ServiceException {
        boolean isChanged = false;
        int userId = Integer.parseInt(passwordData.get(USER_ID_SESSION));
        String login = passwordData.get(LOGIN_SESSION);
        String oldPassword = passwordData.get(PASSWORD_SESSION);
        String newPassword = passwordData.get(NEW_PASSWORD_SESSION);
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validatePassword(newPassword) || !validator.validatePassword(oldPassword)) {
            logger.info("Invalid password.");
            passwordData.put(WRONG_PASSWORD_SESSION, WRONG_DATA_MARKER);
            return isChanged;
        }
        String oldSecretPassword = PasswordEncryptor.md5Apache(oldPassword);
        try {
            Optional<User> optionalUser = userDao.findUserByLoginAndPassword(login, oldSecretPassword);
            if (optionalUser.isEmpty()) {
                logger.info("Wrong password.");
                passwordData.put(WRONG_PASSWORD_SESSION, WRONG_DATA_MARKER);
                return isChanged;
            }
            String newSecretPassword = PasswordEncryptor.md5Apache(newPassword);
            isChanged = userDao.updatePassword(userId, newSecretPassword);
        } catch (DaoException e) {
            logger.error("Try to change password was failed.", e);
            throw new ServiceException("Try to change password was failed.", e);
        }
        return isChanged;
    }

    @Override
    public Optional<User> findUserById(String userId) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        try {
            int id = Integer.parseInt(userId);
            optionalUser = userDao.findEntityById(id);
        } catch (NumberFormatException e) {
            logger.info("Invalid user id");
        } catch (DaoException e) {
            logger.error("Try to find user by id " + userId + " was failed.", e);
            throw new ServiceException("Try to find user by id " + userId + " was failed.", e);
        }
        return optionalUser;
    }

    @Override
    public boolean updatePersonalData(Map<String, String> userData) throws ServiceException {
        boolean isUpdated = false;
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validateUserDataUpdate(userData)) {
            logger.info("Invalid personal data.");
            return isUpdated;
        }
        String login = userData.get(LOGIN_SESSION);
        String password = userData.get(PASSWORD_SESSION);
        String email = userData.get(EMAIL_SESSION);
        String newEmail = userData.get(NEW_EMAIL_SESSION);
        if (password != null) {
            String secretPassword = PasswordEncryptor.md5Apache(password);
            try {
                if (!newEmail.equals(email) && userDao.isEmailExist(newEmail)) {
                    logger.info("User with this email has been already registered.");
                    userData.put(WRONG_EMAIL_EXISTS_SESSION, WRONG_DATA_MARKER);
                    return isUpdated;
                }
                Optional<User> optionalUser = userDao.findUserByLoginAndPassword(login, secretPassword);
                if (optionalUser.isEmpty()) {
                    logger.info("Wrong password");
                    userData.put(WRONG_PASSWORD_SESSION, WRONG_DATA_MARKER);
                    return isUpdated;
                }
            } catch (DaoException e) {
                logger.error("Try to update personal data was failed.", e);
                throw new ServiceException("Try to update personal data was failed.", e);
            }
        }
        int userId = Integer.parseInt(userData.get(USER_ID_SESSION));
        String firstName = userData.get(FIRST_NAME_SESSION);
        String lastName = userData.get(LAST_NAME_SESSION);
        UserRole role = UserRole.valueOf(userData.get(ROLE_NAME_SESSION).toUpperCase());
        boolean isActive = Boolean.parseBoolean(userData.get(IS_ACTIVE_SESSION));
        BigDecimal balance = new BigDecimal(userData.get(BALANCE_SESSION));
        BigDecimal loyaltyPoints = new BigDecimal(userData.get(LOYALTY_POINTS_SESSION));

        User user = new User.UserBuilder()
                .withUserId(userId)
                .withLogin(login)
                .withPassword(password)
                .withEmail(newEmail)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withUserRole(role)
                .withIsActive(isActive)
                .withBalance(balance)
                .withLoyaltyPoints(loyaltyPoints)
                .build();
        try {
            isUpdated = userDao.update(user);
        } catch (DaoException e) {
            logger.error("Try to update user was failed.", e);
            throw new ServiceException("Try to update user was failed.", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserStatus(int userId, boolean newUserStatus) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = userDao.updateUserStatus(userId, newUserStatus);
        } catch (DaoException e) {
            logger.error("Try to update user status was failed.", e);
            throw new ServiceException("Try to update user status was failed.", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserRole(int userId, String userRole) throws ServiceException {
        boolean isUpdated = false;
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validateRole(userRole)) {
            logger.info("Invalid user role.");
            return isUpdated;
        }
        UserRole newUserRole = UserRole.valueOf(userRole) == UserRole.CUSTOMER
                ? UserRole.ADMIN : UserRole.CUSTOMER;
        try {
            boolean isUserActive = userDao.findUserStatusByUserId(userId);
            if (!isUserActive) {
                return isUpdated;
            }
            isUpdated = userDao.updateUserRole(userId, newUserRole);
        } catch (DaoException e) {
            logger.error("Try to update user role was failed.", e);
            throw new ServiceException("Try to update user role was failed.", e);
        }
        return isUpdated;
    }

    @Override
    public boolean refillBalance(Map<String, String> balanceData) throws ServiceException {
        boolean isRefilled = false;
        UserValidator validator = UserValidatorImpl.getInstance();
        String amountAsString = balanceData.get(REFILL_AMOUNT_SESSION);
        if (!validator.validateAmount(amountAsString)) {
            logger.info("Invalid amount.");
            balanceData.put(WRONG_AMOUNT_SESSION, WRONG_DATA_MARKER);
            return isRefilled;
        }
        int userId = Integer.parseInt(balanceData.get(USER_ID_SESSION));
        BigDecimal amount = new BigDecimal(amountAsString);
        BigDecimal balance = new BigDecimal(balanceData.get(BALANCE_SESSION));
        BigDecimal refilledBalance = amount.add(balance);
        if (refilledBalance.compareTo(UserValidator.MAX_AMOUNT) > 0) {
            logger.info("The amount is too big.");
            balanceData.put(WRONG_AMOUNT_OVERSIZE_SESSION, WRONG_DATA_MARKER);
            return isRefilled;
        }
        try {
            isRefilled = userDao.updateBalance(userId, amount);
        } catch (DaoException e) {
            logger.info("Try to refill balance was failed.", e);
            throw new ServiceException("Try to refill balance was failed.", e);
        }
        return isRefilled;
    }
}
