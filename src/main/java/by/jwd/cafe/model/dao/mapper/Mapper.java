package by.jwd.cafe.model.dao.mapper;

import by.jwd.cafe.model.entity.AbstractEntity;

import java.sql.ResultSet;
import java.util.Optional;

/**
 * The EntityMapper interface maps rows of a ResultSet.
 */

public interface Mapper<T extends AbstractEntity> {
    /**
     * Maps data in the ResultSet.
     *
     * @param resultSet the ResultSet to map
     * @return the optional object of a result object for the current row
     */
    Optional<T> map(ResultSet resultSet);
}
