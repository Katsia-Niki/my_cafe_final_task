package test.jwd.cafe.validator.impl;

import by.jwd.cafe.model.entity.Order;
import by.jwd.cafe.validator.OrderValidator;
import by.jwd.cafe.validator.impl.OrderValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static by.jwd.cafe.controller.command.SessionAttribute.*;

public class OrderValidatorImplTest {

    OrderValidator validator = OrderValidatorImpl.getInstance();

    @DataProvider(name = "orderDataProvider")
    public Object[][] createData() {
        Map<String, String> map1 = new HashMap<>();
        map1.put(PAYMENT_TYPE_SESSION, "ACCOUNT");
        map1.put(CART_SUM, "80");
        map1.put(PICK_UP_TIME_SESSION, "2022-08-03T12:30");
        BigDecimal balance1 = new BigDecimal(100);
        BigDecimal loyaltyPoints1 = new BigDecimal(20);

        Map<String, String> map2 = new HashMap<>();
        map2.put(PAYMENT_TYPE_SESSION, "ACCOUNT");
        map2.put(CART_SUM, "80");
        map2.put(PICK_UP_TIME_SESSION, "2022-08-03T12:30");
        BigDecimal balance2 = new BigDecimal(10);
        BigDecimal loyaltyPoints2 = new BigDecimal(20);

        Map<String, String> map3 = new HashMap<>();
        map3.put(PAYMENT_TYPE_SESSION, "LOYALTY_POINTS");
        map3.put(CART_SUM, "80");
        map3.put(PICK_UP_TIME_SESSION, "2022-08-03T12:30");
        BigDecimal balance3 = new BigDecimal(10);
        BigDecimal loyaltyPoints3 = new BigDecimal(90);

        Map<String, String> map4 = new HashMap<>();
        map4.put(PAYMENT_TYPE_SESSION, "CASH");
        map4.put(CART_SUM, "80");
        map4.put(PICK_UP_TIME_SESSION, "2022-08-03T12:30");
        BigDecimal balance4 = new BigDecimal(10);
        BigDecimal loyaltyPoints4 = new BigDecimal(10);

        return new Object[][]{
                {map1, balance1, loyaltyPoints1, true},
                {map2, balance2, loyaltyPoints2, false},
                {map3, balance3, loyaltyPoints3, true},
                {map4, balance4, loyaltyPoints4, true},
        };
    }

    @Test(dataProvider = "orderDataProvider")
    public void testValidateOrderData(Map<String, String> map, BigDecimal balance, BigDecimal loyaltyPoints, boolean expected) {
        boolean actual = validator.validateOrderData(map, balance, loyaltyPoints);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "statusProvider")
    public Object[][] createData3() {
        return new Object[][]{
                {"CUSTOMER", Order.Status.ACTIVE, Order.Status.CANCELLED_BY_CUSTOMER, true},
                {"CUSTOMER", Order.Status.IN_PROCESS, Order.Status.CANCELLED_BY_CUSTOMER, false},
                {"ADMIN", Order.Status.ACTIVE, Order.Status.FINISHED, false},
                {"ADMIN", Order.Status.IN_PROCESS, Order.Status.ACTIVE, false},
                {"ADMIN", Order.Status.IN_PROCESS, Order.Status.FINISHED, true},
        };
    }

    @Test(dataProvider = "statusProvider")
    public void testValidateStatusChange(String role, Order.Status oldStatus, Order.Status newStatus, boolean expected) {
        boolean actual = validator.validateStatusChange(role, oldStatus, newStatus);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "dateRangeProvider")
    public Object[][] createData1() {
        return new Object[][]{
                {"2022-01-01", "2022-01-05", true},
                {"2022-01-05", "2022-01-01", false},
                {"2022-01-01", "azaza", false},
                {"azaza", "2022-01-05", false},
                {"", "", false},
        };
    }

    @Test(dataProvider = "dateRangeProvider")
    public void testValidateDateRange(String from, String to, boolean expected) {
        boolean actual = validator.validateDateRange(from, to);
        Assert.assertEquals(actual, expected);
    }

}