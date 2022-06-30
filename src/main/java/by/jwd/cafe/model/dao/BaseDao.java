package by.jwd.cafe.model.dao;

import by.jwd.cafe.exception.DaoException;
import by.jwd.cafe.model.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

/**
 * The interface represent common functional to dao classes.
 *
 * @param <K> the type parameter
 * @param <T> the type parameter
 */
public interface BaseDao<K, T extends AbstractEntity> {
    /**
     * Find all entities from database
     *
     * @return entity list or empty list if table is empty
     * @throws DaoException - if request from database was failed
     */
    List<T> findAll() throws DaoException;

    /**
     * Add new entity to database
     *
     * @param t - entity extends {@link AbstractEntity}
     * @return true - if entity was created and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean add(T t) throws DaoException;

    /**
     * Delete entity from database.
     *
     * @param t - entity extends {@link AbstractEntity}
     * @return true - if entity was deleted and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean update(T t) throws DaoException;

    /**
     * Find entity from database by id
     *
     * @param entityId - entity id
     * @return an Optional describing entity, or an empty Optional if entity not found
     * @throws DaoException - if request from database was failed
     */
    Optional<T> findEntityById(K entityId) throws DaoException;
}
