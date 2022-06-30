package by.jwd.cafe.model.dao.mapper;

import by.jwd.cafe.model.entity.AbstractEntity;

import java.sql.ResultSet;
import java.util.Optional;

public interface Mapper<T extends AbstractEntity> {
    Optional<T> map(ResultSet resultSet);
}
