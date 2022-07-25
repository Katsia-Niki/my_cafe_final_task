package by.jwd.cafe.service.impl;

import by.jwd.cafe.exception.DaoException;
import by.jwd.cafe.exception.ServiceException;
import by.jwd.cafe.model.dao.OrderDao;
import by.jwd.cafe.model.dao.UserDao;
import by.jwd.cafe.model.dao.impl.OrderDaoImpl;
import by.jwd.cafe.model.dao.impl.UserDaoImpl;
import by.jwd.cafe.model.entity.MenuItem;
import by.jwd.cafe.model.entity.Order;
import by.jwd.cafe.model.entity.PaymentType;
import by.jwd.cafe.service.OrderService;
import by.jwd.cafe.validator.OrderValidator;
import by.jwd.cafe.validator.impl.OrderValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static by.jwd.cafe.controller.command.RequestAttribute.*;
import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class OrderServiceImpl implements OrderService {
    static Logger logger = LogManager.getLogger();
    private static final String DEFAULT_DATE_FROM = "2022-05-01";
    private static OrderServiceImpl instance = new OrderServiceImpl();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    private OrderDao orderDao = OrderDaoImpl.getInstance();

    @Override
    public boolean updateStatus(String role, String status, Order order) throws ServiceException {
        boolean result = false;
        OrderValidator validator = OrderValidatorImpl.getInstance();
        if (!validator.validateStatus(status)) {
            logger.info("Invalid status.");
            return result;
        }
        Order.Status newStatus = Order.Status.valueOf(status);
        Order.Status oldStatus = order.getStatus();
        if (oldStatus == newStatus) {
            logger.info("Nothing to update.");
            result = true;
            return result;
        }
        if (!validator.validateStatusChange(role, oldStatus, newStatus)) {
            logger.info("Invalid status for update");
            return result;
        }
        try {
            Order orderWithNewStatus = new Order.OrderBuilder()
                    .withOrderId(order.getOrderId())
                    .withUserId(order.getUserId())
                    .withPaymentType(order.getPaymentType())
                    .withPickUpTime(order.getPickUpTime())
                    .withOrderCost(order.getOrderCost())
                    .withStatus(newStatus)
                    .withIsPaid(order.isPaid())
                    .withCreationDate(order.getCreationDate())
                    .build();
            UserDao userDao = UserDaoImpl.getInstance();
            BigDecimal userBalance = userDao.findBalanceByUserId(order.getUserId());
            BigDecimal userLoyaltyPoints = userDao.findLoyaltyPointsByUserId(order.getUserId());
            result = orderDao.updateStatus(orderWithNewStatus, userBalance, userLoyaltyPoints);
        } catch (DaoException e) {
            logger.error("Try to update order status was failed.", e);
            throw new ServiceException("Try to update order status was failed.", e);
        }
        return result;
    }

    @Override
    public BigDecimal calculateLoyaltyPoints(BigDecimal cartSum, PaymentType paymentType) {
        BigDecimal percentLoyaltyPoints = new BigDecimal(paymentType.getPercentLoyaltyPoints());
        BigDecimal result = cartSum.multiply(percentLoyaltyPoints.divide(new BigDecimal(100)));
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result;
    }

    @Override
    public boolean createOrder(Map<String, String> orderData, Map<MenuItem, Integer> cart) throws ServiceException {
        boolean isCreated = false;
        try {
            int userId = Integer.parseInt(orderData.get(USER_ID_SESSION));
            UserDao userDao = UserDaoImpl.getInstance();
            BigDecimal userBalance = userDao.findBalanceByUserId(userId);
            BigDecimal userLoyaltyPoints = userDao.findLoyaltyPointsByUserId(userId);

            OrderValidator validator = OrderValidatorImpl.getInstance();

            if (!validator.validateOrderData(orderData, userBalance, userLoyaltyPoints)) {
                return isCreated;
            }
            String paymentTypeStr = orderData.get(PAYMENT_TYPE_SESSION);
            BigDecimal cartSum = new BigDecimal(orderData.get(CART_SUM));
            String pickUpTimeStr = orderData.get(PICK_UP_TIME_SESSION);
            PaymentType paymentType = PaymentType.valueOf(paymentTypeStr.toUpperCase());

            userBalance = paymentType == PaymentType.ACCOUNT
                    ? userBalance.subtract(cartSum)
                    : userBalance;

            if (paymentType == PaymentType.LOYALTY_POINTS) {
                userLoyaltyPoints = userLoyaltyPoints.subtract(cartSum);
            } else {
                BigDecimal pointsToAdd = this.calculateLoyaltyPoints(cartSum, paymentType);
                userLoyaltyPoints = userLoyaltyPoints.add(pointsToAdd);
            }
            boolean isPaid = paymentType != PaymentType.CASH;

            LocalDateTime pickUpTime = LocalDateTime.parse(pickUpTimeStr);

            Order order = new Order.OrderBuilder()
                    .withUserId(userId)
                    .withPaymentType(paymentType)
                    .withPickUpTime(pickUpTime)
                    .withOrderCost(cartSum)
                    .withIsPaid(isPaid)
                    .build();

            isCreated = orderDao.createOrder(order, userBalance, userLoyaltyPoints, cart);
        } catch (DaoException e) {
            logger.error("Try to create order was failed.", e);
            throw new ServiceException("Try to create order was failed.", e);
        }
        return isCreated;
    }

    @Override
    public List<Order> findAllOrders() throws ServiceException {
        List<Order> orderList;
        try {
            orderList = orderDao.findAll();
        } catch (DaoException e) {
            logger.error("Try to find all orders was failed ", e);
            throw new ServiceException("Try to find all orders was failed", e);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrderByUserId(int userId) throws ServiceException {
        List<Order> orderList;
        try {
            orderList = orderDao.findOrderByUserId(userId);
        } catch (DaoException e) {
            logger.error("Try to findOrderByUserId was failed ", e);
            throw new ServiceException("Try to findOrderByUserId was failed", e);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrdersByStatus(Map<String, String> searchParameters) throws ServiceException {
        List<Order> orderList;
        String tempStatus = searchParameters.get(ORDER_STATUS_ATTRIBUTE);
        try {
            if (!tempStatus.isBlank()) {
                Order.Status status = Order.Status.valueOf(tempStatus);
                orderList = orderDao.findOrdersByStatus(status);
            } else {
                orderList = orderDao.findAll();
            }
        } catch (DaoException e) {
            logger.error("Try to find orders by status was failed.", e);
            throw new ServiceException("Try to find orders by status was failed.", e);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrdersByDateRange(Map<String, String> searchParameters) throws ServiceException {
        List<Order> orders = new ArrayList<>();
        String tempDateFrom = searchParameters.get(DATE_FROM_ATTRIBUTE);
        String temDateTo = searchParameters.get(DATE_TO_ATTRIBUTE);
        tempDateFrom = tempDateFrom.isBlank() ? DEFAULT_DATE_FROM : tempDateFrom;
        temDateTo = temDateTo.isBlank() ? LocalDate.now().toString() : temDateTo;

        OrderValidator validator = OrderValidatorImpl.getInstance();
        if (!validator.validateDateRange(tempDateFrom, temDateTo)) {
            searchParameters.put(WRONG_DATE_RANGE_ATTRIBUTE, OrderValidator.WRONG_DATA_MARKER);
            return orders;
        }
        try {
            LocalDate from = LocalDate.parse(tempDateFrom);
            LocalDate to = LocalDate.parse(temDateTo);
            orders = orderDao.findOrdersByDateRange(from, to);
        } catch (DaoException e) {
            logger.error("Try to find orders by date range was failed.", e);
            throw new ServiceException("Try to find orders by date range was failed.", e);
        }
        return orders;
    }

    @Override
    public Optional<Order> findOrderById(String orderId) throws ServiceException {
        Optional<Order> optionalOrder = Optional.empty();
        try {
            int orderIdInt = Integer.parseInt(orderId);
            optionalOrder = orderDao.findEntityById(orderIdInt);
        } catch (NumberFormatException ex) {
            logger.info("Invalid order id.");
        } catch (DaoException e) {
            logger.error("Try to find order by id was failed.", e);
            throw new ServiceException("Try to find order by id was failed.", e);
        }
        return optionalOrder;
    }

    @Override
    public Map<Integer, Boolean> createСanBeCancelledMap(List<Order> orders) {
        LocalDateTime now = LocalDateTime.now();
        Map<Integer, Boolean> canBeCanceledMap = new HashMap<>();
        orders.stream().filter(order -> order.getPickUpTime().compareTo(now) > 0 && order.getStatus() == Order.Status.ACTIVE)
                .forEach(order -> canBeCanceledMap.put(order.getOrderId(), true));
        return canBeCanceledMap;
    }

    @Override
    public Map<Integer, Boolean> createCanBeUpdatedMap(List<Order> orders) {
        Map<Integer, Boolean> canBeUpdatedMap = new HashMap<>();
        orders.stream().filter(order -> order.getStatus() == Order.Status.ACTIVE
                        || order.getStatus() == Order.Status.IN_PROCESS)
                .forEach(order -> canBeUpdatedMap.put(order.getOrderId(), true));
        return canBeUpdatedMap;
    }

    @Override
    public List<Order.Status> findAvailableStatuses(Order.Status status) {
        List<Order.Status> statuses;
        statuses = switch (status) {
            case ACTIVE -> Arrays.asList(Order.Status.IN_PROCESS, Order.Status.CANCELLED_BY_ADMIN);
            case IN_PROCESS -> Arrays.asList(Order.Status.FINISHED, Order.Status.CANCELLED_BY_ADMIN);
            default -> new ArrayList<>();
        };
        return statuses;
    }
}
