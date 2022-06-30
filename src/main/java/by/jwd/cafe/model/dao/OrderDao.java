package by.jwd.cafe.model.dao;

import by.jwd.cafe.exception.DaoException;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.model.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * {@code OrderDao} class implements functional of {@link BaseDao}
 */
public interface OrderDao extends BaseDao<Integer, Order> {

    /**
     * Create new order, update user balance and user loyalty points by that order, create ordered menu items
     *
     * @param order         - new order
     * @param balance       - user balance
     * @param loyaltyPoints - user loyalty points
     * @param cart          - user cart
     * @return true - if Order and ordered menu items were created and user balance and user loyalty points were updated and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean createOrder(Order order, BigDecimal balance, BigDecimal loyaltyPoints, Map<MenuItem, Integer> cart) throws DaoException;

    /**
     * Find order by user id
     *
     * @param userId - user id
     * @return list of order or empty list if order not found
     * @throws DaoException - if request from database was failed
     */
    List<Order> findOrderByUserId(int userId) throws DaoException;

    /**
     * Find order by order status
     *
     * @param status - order status
     * @return list of order or empty list if order not found
     * @throws DaoException - if request from database was failed
     */
    List<Order> findOrdersByStatus(Order.Status status) throws DaoException;

    /**
     * Find order by date range
     *
     * @param from - low border of range
     * @param to   - upper border of range
     * @return list of order or empty list if order not found
     * @throws DaoException - if request from database was failed
     */
    List<Order> findOrdersByDateRange(LocalDate from, LocalDate to) throws DaoException;

    /**
     * Find all orders
     *
     * @return list of orders or empty list if orders not found
     * @throws DaoException - if request from database was failed
     */
    List<Order> findAll() throws DaoException;

    /**
     * Update order status, update user balance and user loyalty points by that order (if order was cancelled)
     *
     * @param order             - new order
     * @param userBalance       - user balance
     * @param userLoyaltyPoints - user loyalty points
     * @return true - if order status, user balance and user loyalty points were updated and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean updateStatus(Order order, BigDecimal userBalance, BigDecimal userLoyaltyPoints) throws DaoException;
}
