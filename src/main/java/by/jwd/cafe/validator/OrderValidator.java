package by.jwd.cafe.validator;

import by.jwd.cafe.model.entity.Order;

import java.math.BigDecimal;
import java.util.Map;

/**
 * {@code OrderValidator} interface represent functional to validate input data
 * for work with class {@link by.jwd.cafe.model.entity.Order}
 */
public interface OrderValidator {
    /**
     * {@code WRONG_DATA_MARKER} constant represent string to mark wrong data
     */
    String WRONG_DATA_MARKER = "Wrong data";

    /**
     * Validate order data boolean.
     *
     * @param orderData     - the order data
     * @param balance       - the balance
     * @param loyaltyPoints - the loyalty points
     * @return the boolean
     */
    boolean validateOrderData(Map<String, String> orderData, BigDecimal balance, BigDecimal loyaltyPoints);

    /**
     * {@code validateStatusChange} method to validate role, old and new status for change order status
     *
     * @param role      - user role
     * @param oldStatus - old status of order
     * @param newStatus - new status of order
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateStatusChange(String role, Order.Status oldStatus, Order.Status newStatus);

    /**
     * {@code validateStatus} method to validate order status
     *
     * @param newStatus - order status as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateStatus(String newStatus);

    /**
     * {@code validateDateRange} method to validate date range for order search
     *
     * @param dateFrom - low border of range
     * @param dateTo   - upper border of range
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateDateRange(String dateFrom, String dateTo);
}
