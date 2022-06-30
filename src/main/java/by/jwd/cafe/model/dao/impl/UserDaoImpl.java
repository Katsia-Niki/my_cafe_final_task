package by.jwd.cafe.model.dao.impl;

import by.jwd.cafe.exception.DaoException;
import by.jwd.cafe.model.dao.ColumnName;
import by.jwd.cafe.model.dao.UserDao;
import by.jwd.cafe.model.dao.mapper.Mapper;
import by.jwd.cafe.model.dao.mapper.impl.UserMapper;
import by.jwd.cafe.model.entity.User;
import by.jwd.cafe.model.entity.UserRole;
import by.jwd.cafe.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.jwd.cafe.model.dao.ColumnName.*;

public class UserDaoImpl implements UserDao {
    static Logger logger = LogManager.getLogger();
    private static final String INSERT_USER = """
            INSERT INTO cafe.users (login, password, first_name, last_name, email, balance, loyalty_points, is_active,
            role_id) values (?,?,?,?,?,?,?,?,?)""";
    private static final String UPDATE_BALANCE = """
            UPDATE cafe.users SET balance=balance+? WHERE user_id=?""";
    private static final String CHANGE_PASSWORD = """
            UPDATE cafe.users SET password=? WHERE user_id=?""";
    private static final String UPDATE_USER = """
            UPDATE cafe.users SET first_name=?, last_name=?, email=?, balance=?, loyalty_points=?, is_active=?, 
            role_id=? WHERE user_id=?""";
    private static final String UPDATE_USER_STATUS = """
            UPDATE cafe.users SET is_active=?
            WHERE user_id=?""";
    private static final String UPDATE_USER_ROLE = """
            UPDATE cafe.users SET role_id=?
            WHERE user_id=?""";
    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = """
            SELECT user_id, login, password, first_name, last_name, email, balance, loyalty_points, 
            is_active, role_id 
            FROM cafe.users 
            WHERE login=? AND password=?""";
    private static final String SELECT_USER_BY_ID = """
            SELECT user_id, login, password, first_name, last_name, email, balance, loyalty_points, 
            is_active, role_id 
            FROM cafe.users 
            WHERE user_id=?""";
    private static final String SELECT_USER_BY_LOGIN = """
            SELECT user_id, login, password, first_name, last_name, email, balance, loyalty_points, 
            is_active, role_name 
            FROM cafe.users JOIN users_role ON users_role.users_role_role_id=users.role_id 
            WHERE login=?""";
    private static final String SELECT_USER_BY_EMAIL = """
            SELECT user_id, login, password, first_name, last_name, email, balance, loyalty_points, 
            is_active, role_name 
            FROM cafe.users JOIN users_role ON users_role.users_role_role_id=users.role_id 
            WHERE email=?""";
    private static String SELECT_ALL_USERS = """
            SELECT user_id, login, password, first_name, last_name, email, balance, loyalty_points,
            is_active, role_id 
            FROM cafe.users""";

    private static String SELECT_ALL_PAGINATED_USERS = """
            SELECT user_id, login, password, first_name, last_name, email, balance, loyalty_points,
            is_active, role_id 
            FROM cafe.users
            LIMIT ? OFFSET ?""";
    private static final String SELECT_BALANCE_BY_USER_ID = """
            SELECT balance
            FROM cafe.users
            WHERE user_id=?""";

    private static final String SELECT_LOYALTY_POINTS_BY_USER_ID = """
            SELECT loyalty_points
            FROM cafe.users
            WHERE user_id=?""";

    private static final String SELECT_NUMBER_ALL_USERS = """
            SELECT count(user_id) AS num
            FROM cafe.users
            """;

    private static final String SELECT_STATUS_BY_USER_ID = """
            SELECT is_active
            FROM cafe.users
            WHERE user_id=?""";

    private static final String SELECT_ROLE_BY_USER_ID = """
            SELECT role_name
            FROM cafe.users
            JOIN users_role ON users_role.users_role_role_id=users.role_id
            WHERE user_id=?""";
    private static final int DEFAULT_PAGE_CAPACITY = 5;
    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        Mapper<User> mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Optional<User> optionalUser = mapper.map(resultSet);
                if (optionalUser.isPresent()) {
                    userList.add(optionalUser.get());
                }
            }
            logger.log(Level.INFO, "List: " + userList);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public List<User> findAllPaginatedUsers(int currentPageNumber) throws DaoException {
        List<User> userList = new ArrayList<>();
        Mapper<User> mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PAGINATED_USERS)) {
            statement.setInt(1, DEFAULT_PAGE_CAPACITY);
            statement.setInt(2, (currentPageNumber - 1) * DEFAULT_PAGE_CAPACITY);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<User> optionalUser = mapper.map(resultSet);
                    if (optionalUser.isPresent()) {
                        userList.add(optionalUser.get());
                    }
                }
            }
            logger.log(Level.INFO, "List: " + userList);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public int findNumberOfAllUsers() throws DaoException {
        int num = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_NUMBER_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                num = resultSet.getInt(ColumnName.NUM);
                num = num % DEFAULT_PAGE_CAPACITY == 0
                        ? num / DEFAULT_PAGE_CAPACITY
                        : num / DEFAULT_PAGE_CAPACITY + 1;
            }
        } catch (SQLException e) {
            logger.error("SQL request to findNumberOfAllUsers was failed.", e);
            throw new DaoException("SQL request to findNumberOfAllUsers was failed.", e);
        }
        return num;
    }

    @Override
    public boolean add(User user) throws DaoException {
        int rows;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setBigDecimal(6, user.getBalance());
            statement.setBigDecimal(7, user.getLoyaltyPoints());
            statement.setBoolean(8, user.isActive());
            statement.setInt(9, user.getRole().ordinal());
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request add user to cafe.users was failed.", e);
            throw new DaoException("SQL request add user to cafe.users was failed.", e);
        }
        return rows == 1;
    }

    @Override
    public boolean update(User user) throws DaoException {
        int rows;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setBigDecimal(4, user.getBalance());
            statement.setBigDecimal(5, user.getLoyaltyPoints());
            statement.setBoolean(6, user.isActive());
            statement.setInt(7, user.getRole().ordinal());
            statement.setInt(8, user.getUserId());
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request changePassword from table cafe.users was failed.", e);
            throw new DaoException("SQL request changePassword from table cafe.users was failed.", e);
        }
        return rows == 1;
    }

    @Override
    public Optional<User> findEntityById(Integer entityId) throws DaoException {
        Optional<User> optionalUser;
        Mapper<User> mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setInt(1, entityId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                optionalUser = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findUserById from cafe was failed.", e);
            throw new DaoException("SQL request findUserById from cafe was failed.", e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        Mapper<User> mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                optionalUser = mapper.map(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findUserByLoginAndPassword from cafe was failed.", e);
            throw new DaoException("SQL request findUserByLoginAndPassword from cafe was failed.", e);
        }
        return optionalUser;
    }

    @Override
    public boolean updatePassword(int userId, String newPassword) throws DaoException {
        int rows;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setInt(2, userId);
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request changePassword from table cafe.users was failed.", e);
            throw new DaoException("SQL request changePassword from table cafe.users was failed.", e);
        }
        return rows == 1;
    }

    @Override
    public boolean updateUserStatus(int userId, boolean newUserStatus) throws DaoException {
        int rows;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATUS)) {
            statement.setBoolean(1, newUserStatus);
            statement.setInt(2, userId);
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request updateUserStatus from table cafe.users was failed.", e);
            throw new DaoException("SQL request updateUserStatus from table cafe.users was failed.", e);
        }
        return rows == 1;
    }

    @Override
    public boolean updateUserRole(int userId, UserRole userRole) throws DaoException {
        int rows;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_ROLE)) {
            statement.setInt(1, userRole.ordinal());
            statement.setInt(2, userId);
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request updateUserRole from table cafe.users was failed.", e);
            throw new DaoException("SQL request updateUserRole from table cafe.users was failed.", e);
        }
        return rows == 1;
    }

    @Override
    public boolean updateBalance(int userId, BigDecimal amount) throws DaoException {
        int rows;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BALANCE)) {
            statement.setBigDecimal(1, amount);
            statement.setInt(2, userId);
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request updateBalance from table cafe.users was failed.", e);
            throw new DaoException("SQL request updateBalance from table cafe.users was failed.", e);
        }
        return rows == 1;
    }

    @Override
    public BigDecimal findBalanceByUserId(int userId) throws DaoException {
        BigDecimal balance = BigDecimal.ZERO;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BALANCE_BY_USER_ID)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    balance = resultSet.getBigDecimal(BALANCE);
                }
            }
        } catch (SQLException e) {
            logger.error("Try to execute request findBalanceByUserId was failed.", e);
            throw new DaoException("Try to execute request findBalanceByUserId was failed.", e);
        }
        return balance;
    }

    @Override
    public BigDecimal findLoyaltyPointsByUserId(int userId) throws DaoException {
        BigDecimal loyaltyPoints = BigDecimal.ZERO;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_LOYALTY_POINTS_BY_USER_ID)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    loyaltyPoints = resultSet.getBigDecimal(LOYALTY_POINTS);
                }
            }
        } catch (SQLException e) {
            logger.error("Try to execute request findLoyaltyPointsByUserId was failed.", e);
            throw new DaoException("Try to execute request findLoyaltyPointsByUserId was failed.", e);
        }
        return loyaltyPoints;
    }

    @Override
    public boolean isEmailExist(String email) throws DaoException {
        boolean isExist = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isExist = true;
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request isEmailExist from table cafe.users was failed.", e);
            throw new DaoException("SQL request isEmailExist from table cafe.users was failed.", e);
        }
        return isExist;
    }

    @Override
    public boolean isLoginExist(String login) throws DaoException {
        boolean isExist = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isExist = true;
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request isLoginExist from table cafe.users was failed.", e);
            throw new DaoException("SQL request isLoginExist from table cafe.users was failed.", e);
        }
        return isExist;
    }

    @Override
    public boolean findUserStatusByUserId(int userId) throws DaoException {
        boolean isActive = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STATUS_BY_USER_ID)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isActive = resultSet.getBoolean(IS_ACTIVE);
                }
            }
        } catch (SQLException e) {
            logger.error("Try to execute request findUserStatusByUserId was failed.", e);
            throw new DaoException("Try to execute request findUserStatusByUserId was failed.", e);
        }
        return isActive;
    }
}
