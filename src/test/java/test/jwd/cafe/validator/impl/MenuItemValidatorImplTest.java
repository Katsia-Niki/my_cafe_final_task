package test.jwd.cafe.validator.impl;

import by.jwd.cafe.validator.MenuItemValidator;
import by.jwd.cafe.validator.impl.MenuItemValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MenuItemValidatorImplTest {

    MenuItemValidator validator = MenuItemValidatorImpl.getInstance();

    @DataProvider(name = "nameProvider")
    public Object[][] createData1() {
        return new Object[][]{
                {"Карамельные капкейки", true},
                {"Cheesecake", true},
                {"Tort!!!", false},
                {"УРУРУ", true},
                {"<УРУРУ>", false},
                {"", false}
        };
    }

    @Test(dataProvider = "nameProvider")
    public void testValidateName(String name, boolean expected) {
        boolean actual = validator.validateName(name);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "descriptionProvider")
    public Object[][] createData2() {
        return new Object[][]{
                {"Карамельные капкейки из сливочного сыра с шариком пломбира", true},
                {"Cheesecake with blueberry", true},
                {"<УРУРУ>", false},
                {"", false}
        };
    }

    @Test(dataProvider = "descriptionProvider")
    public void testValidateDescription(String description, boolean expected) {
        boolean actual = validator.validateDescription(description);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "priceProvider")
    public Object[][] createData3() {
        return new Object[][]{
                {"10", true},
                {"azaza", false},
                {"-5", false},
                {"99999999999", false}
        };
    }

    @Test(dataProvider = "priceProvider")
    public void testValidatePrice(String price, boolean expected) {
        boolean actual = validator.validatePrice(price);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "typeProvider")
    public Object[][] createData4() {
        return new Object[][]{
                {"appetizer", true},
                {"main course", true},
                {"MAIN_COURSE", true},
                {"99999999999", false}
        };
    }

    @Test(dataProvider = "typeProvider")
    public void testValidateType(String type, boolean expected) {
        boolean actual = validator.validateType(type);
        Assert.assertEquals(actual, expected);
    }
}