package test.jwd.cafe.service.impl;

import by.jwd.cafe.model.entity.Order;
import by.jwd.cafe.model.entity.PaymentType;
import by.jwd.cafe.service.impl.OrderServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

public class OrderServiceImplTest {

    OrderServiceImpl orderService;

    @BeforeMethod
    public void initialize() {
        orderService = OrderServiceImpl.getInstance();
    }

    @Test
    public void testCreateСanBeCancelledMap() {
        Order order1 = new Order.OrderBuilder()
                .withOrderId(14)
                .withStatus(Order.Status.ACTIVE)
                .withPickUpTime(LocalDateTime.parse("2022-08-30T14:30"))
                .build();
        Order order2 = new Order.OrderBuilder()
                .withOrderId(19)
                .withStatus(Order.Status.FINISHED)
                .withPickUpTime(LocalDateTime.parse("2022-08-30T12:30"))
                .build();
        Order order3 = new Order.OrderBuilder()
                .withOrderId(28)
                .withStatus(Order.Status.ACTIVE)
                .withPickUpTime(LocalDateTime.parse("2022-07-07T14:30"))
                .build();
        List<Order> orders = List.of(order1, order2, order3);
        Map<Integer, Boolean> actual = orderService.createСanBeCancelledMap(orders);
        Map<Integer, Boolean> expected = new HashMap<>();
        expected.put(14, true);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCreateСanBeUpdatedMap() {
        Order order1 = new Order.OrderBuilder()
                .withOrderId(14)
                .withStatus(Order.Status.ACTIVE)
                .withPickUpTime(LocalDateTime.parse("2022-08-30T14:30"))
                .build();
        Order order2 = new Order.OrderBuilder()
                .withOrderId(19)
                .withStatus(Order.Status.FINISHED)
                .withPickUpTime(LocalDateTime.parse("2022-08-30T12:30"))
                .build();
        Order order3 = new Order.OrderBuilder()
                .withOrderId(28)
                .withStatus(Order.Status.IN_PROCESS)
                .withPickUpTime(LocalDateTime.parse("2022-08-17T14:30"))
                .build();
        List<Order> orders = List.of(order1, order2, order3);
        Map<Integer, Boolean> actual = orderService.createCanBeUpdatedMap(orders);
        Map<Integer, Boolean> expected = new HashMap<>();
        expected.put(14, true);
        expected.put(28, true);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "statusProvider")
    public Object[][] createData() {
        return new Object[][]{
                {Order.Status.ACTIVE, Arrays.asList(Order.Status.IN_PROCESS, Order.Status.CANCELLED_BY_ADMIN)},
                {Order.Status.IN_PROCESS, Arrays.asList(Order.Status.FINISHED, Order.Status.CANCELLED_BY_ADMIN)},
                {Order.Status.FINISHED, new ArrayList<Order.Status>()},
                {Order.Status.CANCELLED_BY_CUSTOMER, new ArrayList<Order.Status>()},
                {Order.Status.CANCELLED_BY_ADMIN, new ArrayList<Order.Status>()},
        };
    }

    @Test(dataProvider = "statusProvider")
    public void testFindAvailableStatuses(Order.Status status, List<Order.Status> expected) {
        List<Order.Status> actual = orderService.findAvailableStatuses(status);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCalculateLoyaltyPoints() {
        BigDecimal cartSum = new BigDecimal(45.50);
        PaymentType paymentType = PaymentType.ACCOUNT;
        BigDecimal expected = new BigDecimal(6.83);
        expected = expected.setScale(2, RoundingMode.HALF_UP);
        BigDecimal actual = orderService.calculateLoyaltyPoints(cartSum, paymentType);
        Assert.assertEquals(actual, expected);
    }

    @AfterMethod
    public void clean() {
        orderService = null;
    }
}