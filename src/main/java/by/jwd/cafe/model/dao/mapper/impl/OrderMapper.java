package by.jwd.cafe.model.dao.mapper.impl;

import by.jwd.cafe.model.dao.ColumnName;
import by.jwd.cafe.model.dao.mapper.Mapper;
import by.jwd.cafe.model.entity.Order;
import by.jwd.cafe.model.entity.PaymentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class OrderMapper implements Mapper<Order> {
    static Logger logger = LogManager.getLogger();
    private static final OrderMapper instance = new OrderMapper();

    private OrderMapper() {

    }

    public static OrderMapper getInstance() {
        return instance;
    }

    @Override
    public Optional<Order> map(ResultSet resultSet) {
        Optional<Order> optionalOrder;
        try {
            Order order = new Order.OrderBuilder()
                    .withOrderId(resultSet.getInt(ColumnName.ORDER_ID))
                    .withUserId(resultSet.getInt(ColumnName.USERS_USER_ID))
                    .withPaymentType(PaymentType.valueOf(resultSet.getString(ColumnName.PAYMENT_TYPE).toUpperCase()))
                    .withPickUpTime(resultSet.getDate(ColumnName.PICK_UP_TIME).toLocalDate().atTime(resultSet.getTime(ColumnName.PICK_UP_TIME).toLocalTime()))
                    .withOrderCost(resultSet.getBigDecimal(ColumnName.ORDER_COST))
                    .withIsPaid(resultSet.getBoolean(ColumnName.IS_PAID))
                    .withStatus(Order.Status.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase()))
                    .withCreationDate(resultSet.getDate(ColumnName.CREATION_DATE).toLocalDate())
                    .build();
            optionalOrder = Optional.of(order);
        } catch (SQLException e) {
            logger.error("SQL exception while map User resultSet", e);
            optionalOrder = Optional.empty();
        }
        return optionalOrder;
    }
}
