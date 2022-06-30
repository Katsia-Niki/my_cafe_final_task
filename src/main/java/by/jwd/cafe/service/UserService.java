package by.jwd.cafe.service;

import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code UserService} interface represent functional business logic
 * for work with class {@link by.jwd.cafe.model.entity.User}
 */
public interface UserService {

    /**
     * Authenticate user in system
     *
     * @param userData - map with user data
     *                 As key use {@link by.jwd.cafe.controller.command.SessionAttribute}
     * @return an Optional describing user, or an empty Optional if user not found or login or password wrong
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    Optional<User> authenticate(Map<String, String> userData) throws ServiceException;

    /**
     * Create user
     *
     * @param userData - map with user data
     *                 As key use {@link by.jwd.cafe.controller.command.SessionAttribute}
     * @return true - if user was created and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean createNewAccount(Map<String, String> userData) throws ServiceException;

    /**
     * Finds all users for the current page.
     *
     * @param currentPageNumber a current page number
     * @return a list of users
     */
    List<User> findAllUsers(int currentPageNumber) throws ServiceException;

    /**
     * Update user password
     *
     * @param passwordData - map with user data
     *                     As key use {@link by.jwd.cafe.controller.command.SessionAttribute}
     * @return true - if user password was updated and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean changePassword(Map<String, String> passwordData) throws ServiceException;

    /**
     * Find user by id
     *
     * @param userId - user id
     * @return an Optional describing user, or an empty Optional if user not found
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    Optional<User> findUserById(String userId) throws ServiceException;

    /**
     * Update user
     *
     * @param userData - map with user data
     *                 As key use {@link by.jwd.cafe.controller.command.SessionAttribute}
     * @return true - if user was updated and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean updatePersonalData(Map<String, String> userData) throws ServiceException;

    /**
     * Updates user's status in database
     *
     * @param userId        - the id of user to update
     * @param newUserStatus - new user's status
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean updateUserStatus(int userId, boolean newUserStatus) throws ServiceException;

    /**
     * Updates user's role in database
     *
     * @param userId   - the id of user to update
     * @param userRole - new user's role
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean updateUserRole(int userId, String userRole) throws ServiceException;

    /**
     * Update user balance
     *
     * @param balanceData - map with user data
     *                    As key use {@link by.jwd.cafe.controller.command.SessionAttribute}
     * @return true - if user balance was updated and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean refillBalance(Map<String, String> balanceData) throws ServiceException;

    /**
     * Counts number of pages with all users.
     *
     * @return a number of pages
     */
    int findNumberOfPages() throws ServiceException;
}
