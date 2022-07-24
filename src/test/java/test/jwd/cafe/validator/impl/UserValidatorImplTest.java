package test.jwd.cafe.validator.impl;

import by.jwd.cafe.validator.UserValidator;
import by.jwd.cafe.validator.impl.UserValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserValidatorImplTest {
    UserValidator validator = UserValidatorImpl.getInstance();

    @DataProvider(name = "emailProvider")
    public Object[][] createData() {
        return new Object[][]{
                {"ivanov@gmail.com", true},
                {"ivanov-33.5-rr@gmail.com", true},
                {"ivanov", false},
                {"ivanov.@gmail.com", false},
                {"ivanov@gmail.com.com", false},
        };
    }

    @Test(dataProvider = "emailProvider")
    public void testValidateEmail(String email, boolean expected) {
        boolean actual = validator.validateEmail(email);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "passwordProvider")
    public Object[][] createData2() {
        return new Object[][]{
                {"ivanov123", true},
                {"1234", true},
                {"12", false},
                {"<///>", false},
                {"11111111111111111111111111111111111111111111111111111111", false},
        };
    }

    @Test(dataProvider = "passwordProvider")
    public void testValidatePassword(String password, boolean expected) {
        boolean actual = validator.validatePassword(password);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "loginProvider")
    public Object[][] createData3() {
        return new Object[][]{
                {"Ivanov", true},
                {"Petr-Swonson", true},
                {"Ivan!!!", false},
                {"hello-me", true},
                {"<УРУРУ>", false},
                {"11111111111111111111111111111111111111111111111111111111", false},
                {"", false},
        };
    }

    @Test(dataProvider = "loginProvider")
    public void testValidateLogin(String login, boolean expected) {
        boolean actual = validator.validateLogin(login);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "nameProvider")
    public Object[][] createData1() {
        return new Object[][]{
                {"Ivanov", true},
                {"Petr Swonson", true},
                {"Ivan!!!", false},
                {"УРУРУ", true},
                {"<УРУРУ>", false},
                {"11111111111111111111111111111111111111111111111111111111", false},
                {"", false},
        };
    }

    @Test(dataProvider = "nameProvider")
    public void testValidateName(String name, boolean expected) {
        boolean actual = validator.validateName(name);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "amountProvider")
    public Object[][] createData5() {
        return new Object[][]{
                {"153.37", true},
                {"-153.37", false},
                {"153.3333", false},
                {"15333333333", false},
                {"<УРУРУ>", false},
                {"", false},
        };
    }

    @Test(dataProvider = "amountProvider")
    public void testValidateAmount(String amount, boolean expected) {
        boolean actual = validator.validateAmount(amount);
        Assert.assertEquals(actual, expected);
    }
}