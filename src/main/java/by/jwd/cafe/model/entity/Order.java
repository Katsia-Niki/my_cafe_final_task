package by.jwd.cafe.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * The {@code Order} class
 * is an entity that represents table 'order' in the database.
 */

public class Order extends AbstractEntity {
    private static long serialVersionUID = 1L;
    private int orderId;
    private int userId;
    private PaymentType paymentType;
    private LocalDateTime pickUpTime;
    private BigDecimal orderCost;
    private Order.Status status;
    private boolean isPaid;
    private LocalDate creationDate;

    public enum Status {
        ACTIVE, IN_PROCESS, CANCELLED_BY_CUSTOMER, CANCELLED_BY_ADMIN, FINISHED;

        private static final char UNDERSCORE = '_';
        private static final char SPACE = ' ';

        @Override
        public String toString() {
            return this.name().replace(UNDERSCORE, SPACE).toLowerCase();
        }

        public static Order.Status valueOfOrderStatus(String name) {
            return Order.Status.valueOf(name.toUpperCase().replace(SPACE, UNDERSCORE));
        }
    }

    public Order() {
    }

    public static class OrderBuilder {
        private Order newOrder;

        {
            newOrder = new Order();
        }

        public OrderBuilder withOrderId(int orderId) {
            newOrder.orderId = orderId;
            return this;
        }

        public OrderBuilder withUserId(int userId) {
            newOrder.userId = userId;
            return this;
        }

        public OrderBuilder withPaymentType(PaymentType paymentType) {
            newOrder.paymentType = paymentType;
            return this;
        }

        public OrderBuilder withPickUpTime(LocalDateTime pickUpTime) {
            newOrder.pickUpTime = pickUpTime;
            return this;
        }

        public OrderBuilder withOrderCost(BigDecimal orderCost) {
            newOrder.orderCost = orderCost;
            return this;
        }

        public OrderBuilder withStatus(Order.Status status) {
            newOrder.status = status;
            return this;
        }

        public OrderBuilder withIsPaid(boolean isPaid) {
            newOrder.isPaid = isPaid;
            return this;
        }

        public OrderBuilder withCreationDate(LocalDate creationDate) {
            newOrder.creationDate = creationDate;
            return this;
        }

        public Order build() {
            return newOrder;
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(LocalDateTime pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (userId != order.userId) return false;
        if (isPaid != order.isPaid) return false;
        if (paymentType != order.paymentType) return false;
        if (pickUpTime != null ? !pickUpTime.equals(order.pickUpTime) : order.pickUpTime != null) return false;
        if (orderCost != null ? !orderCost.equals(order.orderCost) : order.orderCost != null) return false;
        if (status != order.status) return false;
        return creationDate != null ? creationDate.equals(order.creationDate) : order.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + userId;
        result = 31 * result + (paymentType != null ? paymentType.hashCode() : 0);
        result = 31 * result + (pickUpTime != null ? pickUpTime.hashCode() : 0);
        result = 31 * result + (orderCost != null ? orderCost.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isPaid ? 1 : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "Order{", "}");
        joiner.add("orderId=" + orderId);
        joiner.add("userId=" + userId);
        joiner.add("paymentType=" + paymentType);
        joiner.add("pickUpTime=" + pickUpTime);
        joiner.add("orderCost=" + orderCost);
        joiner.add("status=" + status);
        joiner.add("isPaid=" + isPaid);
        joiner.add("creationDate=" + creationDate);
        return joiner.toString();
    }
}
