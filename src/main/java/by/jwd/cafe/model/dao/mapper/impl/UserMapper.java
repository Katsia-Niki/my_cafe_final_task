package by.jwd.cafe.model.dao.mapper.impl;

import by.jwd.cafe.model.dao.ColumnName;
import by.jwd.cafe.model.dao.mapper.Mapper;
import by.jwd.cafe.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserMapper implements Mapper<User> {
    static Logger logger = LogManager.getLogger();
    private static final UserMapper instance = new UserMapper();

    private UserMapper() {

    }

    public static UserMapper getInstance() {
        return instance;
    }


    @Override
    public Optional<User> map(ResultSet resultSet) {
        Optional<User> optionalUser;
        try {
            User user = new User.UserBuilder()
                    .withUserId(resultSet.getInt(ColumnName.USER_ID))
                    .withLogin(resultSet.getString(ColumnName.LOGIN))
                    .withPassword(resultSet.getString(ColumnName.PASSWORD))
                    .withFirstName(resultSet.getString(ColumnName.FIRST_NAME))
                    .withLastName(resultSet.getString(ColumnName.LAST_NAME))
                    .withEmail(resultSet.getString(ColumnName.EMAIL))
                    .withBalance(resultSet.getBigDecimal(ColumnName.BALANCE))
                    .withLoyaltyPoints(resultSet.getBigDecimal(ColumnName.LOYALTY_POINTS))
                    .withIsActive(resultSet.getBoolean(ColumnName.IS_ACTIVE))
                    .withUserRoleById(resultSet.getInt(ColumnName.ROLE_ID))
                    .build();
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            logger.error("SQL exception while map User resultSet", e);
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
