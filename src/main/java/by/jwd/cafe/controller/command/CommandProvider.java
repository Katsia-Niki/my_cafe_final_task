package by.jwd.cafe.controller.command;

import by.jwd.cafe.controller.command.impl.*;
import by.jwd.cafe.controller.command.impl.admin.*;
import by.jwd.cafe.controller.command.impl.admin.to.*;
import by.jwd.cafe.controller.command.impl.customer.*;
import by.jwd.cafe.controller.command.impl.customer.to.GoToCancelOrderPageCommand;
import by.jwd.cafe.controller.command.impl.customer.to.GoToCartPageCommand;
import by.jwd.cafe.controller.command.impl.customer.to.GoToPlaceOrderPageCommand;
import by.jwd.cafe.controller.command.impl.customer.to.GoToRefillBalancePageCommand;
import by.jwd.cafe.controller.command.impl.to.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

import static by.jwd.cafe.controller.command.CommandType.*;

/**
 * {@code CommandProvider} class represent the relations between {@link CommandType} and {@link Command}
 * Hold and provide instance of all classes extends {@link Command}
 */
public final class CommandProvider {
    static Logger logger = LogManager.getLogger();
    private static EnumMap<CommandType, Command> commands = new EnumMap<>(CommandType.class);

    static {
        commands.put(ADD_ITEM_TO_CART, new AddItemToCartCommand());
        commands.put(CANCEL_ORDER, new CancelOrderCommand());
        commands.put(CHANGE_PASSWORD, new ChangePasswordCommand());
        commands.put(CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(CONFIRM_ORDER, new ConfirmOrderCommand());
        commands.put(CREATE_MENU_ITEM, new CreateMenuItemCommand());
        commands.put(DEFAULT, new DefaultCommand());
        commands.put(EDIT_MENU_ITEM, new EditMenuItemCommand());
        commands.put(FIND_ALL_USERS, new FindAllUsersCommand());
        commands.put(FIND_ALL_AVAILABLE_MENU, new FindAllAvailableMenuCommand());
        commands.put(FIND_ALL_MENU, new FindAllMenuCommand());
        commands.put(FIND_ALL_ORDERS, new FindAllOrdersCommand());
        commands.put(FIND_ORDER_BY_USER_ID, new FindOrderByUserIdCommand());
        commands.put(FIND_ORDERS_BY_STATUS, new FindOrdersByStatusCommand());
        commands.put(FIND_ORDERS_BY_DATE_RANGE, new FindOrdersByDateRangeCommand());
        commands.put(GO_TO_ACCOUNT_PAGE, new GoToAccountPageCommand());
        commands.put(GO_TO_CANCEL_ORDER_PAGE, new GoToCancelOrderPageCommand());
        commands.put(GO_TO_CART_PAGE, new GoToCartPageCommand());
        commands.put(GO_TO_CHANGE_PASSWORD_PAGE, new GoToChangePasswordPageCommand());
        commands.put(GO_TO_CONTACT_PAGE, new GoToContactPageCommand());
        commands.put(GO_TO_CREATE_MENU_ITEM_PAGE, new GoToCreateMenuItemPageCommand());
        commands.put(GO_TO_EDIT_MENU_PAGE, new GoToEditMenuPageCommand());
        commands.put(GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(GO_TO_MENU_PAGE, new GoToMenuPageCommand());
        commands.put(GO_TO_ORDER_MANAGEMENT_PAGE, new GoToOrderManagementPageCommand());
        commands.put(GO_TO_PLACE_ORDER_PAGE, new GoToPlaceOrderPageCommand());
        commands.put(GO_TO_REFILL_BALANCE_PAGE, new GoToRefillBalancePageCommand());
        commands.put(GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
        commands.put(GO_TO_UPDATE_ORDER_PAGE, new GoToUpdateOrderPageCommand());
        commands.put(LOGIN, new LoginCommand());
        commands.put(LOGOUT, new LogoutCommand());
        commands.put(REFILL_BALANCE, new RefillBalanceCommand());
        commands.put(REGISTRATION, new RegistrationCommand());
        commands.put(REMOVE_ITEM_FROM_CART, new RemoveItemFromCartCommand());
        commands.put(UPDATE_ORDER, new UpdateOrderCommand());
        commands.put(UPDATE_PERSONAL_DATA, new UpdatePersonalDataCommand());
        commands.put(UPDATE_USER_ROLE, new UpdateUserRoleCommand());
        commands.put(UPDATE_USER_STATUS, new UpdateUserStatusCommand());
        commands.put(UPLOAD_IMAGE, new UploadImageCommand());
    }

    /**
     * ${code of} - return {@link}
     *
     * @param commandName - name of command, type {@link CommandType}
     * @return suitable instance of class, which implements {@link Command},
     * or default command if such command not present
     */

    public static Command of(String commandName) {
        Command currentCommand = commands.get(DEFAULT);
        if (commandName != null) {
            try {
                currentCommand = commands.get(CommandType.valueOf(commandName.toUpperCase()));
            } catch (IllegalArgumentException e) {
                logger.error("Command " + commandName + " is not present.", e);
            }
        }
        return currentCommand;
    }
}
