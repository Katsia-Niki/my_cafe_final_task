package by.jwd.cafe.service;

import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.model.entity.Order;
import by.jwd.cafe.model.entity.PaymentType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code OrderService} interface represent functional business logic
 * for work with class {@link by.jwd.cafe.model.entity.Order}
 */
public interface OrderService {

    /**
     * Creates order
     *
     * @param orderData - map with order data
     *                  As key use {@link by.jwd.cafe.controller.command.SessionAttribute}
     * @return true - if order was created and false - if was not
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean createOrder(Map<String, String> orderData, Map<MenuItem, Integer> cart) throws ServiceException;

    /**
     * Finds all orders
     *
     * @return order list or empty list if order not found
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    List<Order> findAllOrders() throws ServiceException;

    /**
     * Finds order by user id
     *
     * @param userId - user id
     *               As key use {@link by.jwd.cafe.controller.command.RequestAttribute}
     * @return order list or empty list if order not found
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    List<Order> findOrderByUserId(int userId) throws ServiceException;

    /**
     * Finds order by status
     *
     * @param searchParameters - map of search parameters
     *                         As key use {@link by.jwd.cafe.controller.command.RequestAttribute}
     * @return order list or empty list if order not found
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    List<Order> findOrdersByStatus(Map<String, String> searchParameters) throws ServiceException;

    /**
     * Finds order by date range
     *
     * @param searchParameters - map of search parameters
     *                         As key use {@link by.jwd.cafe.controller.command.RequestAttribute}
     * @return order list or empty list if order not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    List<Order> findOrdersByDateRange(Map<String, String> searchParameters) throws ServiceException;

    /**
     * Finds order by id
     *
     * @param orderId - order id
     * @return an Optional describing order, or an empty Optional if order not found
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    Optional<Order> findOrderById(String orderId) throws ServiceException;

    /**
     * Updates order status
     *
     * @param role      - user role
     * @param newStatus - value of new order status
     * @param order     - {@link Order} object
     * @return true - if order was updated and false - if was not
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    boolean updateStatus(String role, String newStatus, Order order) throws ServiceException;

    /**
     * Calculates loyalty points, depends on payment type.
     *
     * @param cartSum     - value of cart sum
     * @param paymentType - {@link PaymentType} object
     * @return the number of loyalty points, that customer gets for the order
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    BigDecimal calculateLoyaltyPoints(BigDecimal cartSum, PaymentType paymentType);

    /**
     * Creates map of order id and marks "can be cancelled"
     *
     * @param orders - order list
     * @return map represent relations between order id and mark "can be cancelled"
     */
    Map<Integer, Boolean> createСanBeCancelledMap(List<Order> orders);

    /**
     * Creates map of order id and mark "can be updated"
     *
     * @param orders - order list
     * @return map represent relations between order id and mark "can be updated"
     */
    Map<Integer, Boolean> createCanBeUpdatedMap(List<Order> orders);

    /**
     * Forms list available order statuses for current status
     *
     * @param status - current status
     * @return order status list or empty list if current order status have not available statuses
     * @throws ServiceException - if dao method throw {@link by.jwd.cafe.exception.DaoException}
     */
    List<Order.Status> findAvailableStatuses(Order.Status status);
}
