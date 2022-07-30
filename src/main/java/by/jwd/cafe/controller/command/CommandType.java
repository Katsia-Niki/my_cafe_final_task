package by.jwd.cafe.controller.command;

import by.jwd.cafe.model.entity.UserRole;

import java.util.EnumSet;

import static by.jwd.cafe.model.entity.UserRole.*;

/**
 * {@code CommandType} enum represent all commands,
 * contains jsp command name and user roles, who can call that command
 */
public enum CommandType {
    ADD_ITEM_TO_CART(EnumSet.of(CUSTOMER)),
    CANCEL_ORDER(EnumSet.of(CUSTOMER)),
    CHANGE_PASSWORD(EnumSet.of(CUSTOMER, ADMIN)),
    CHANGE_LANGUAGE(EnumSet.of(GUEST, CUSTOMER, ADMIN)),
    CONFIRM_ORDER(EnumSet.of(CUSTOMER)),
    CREATE_MENU_ITEM(EnumSet.of(ADMIN)),
    DEFAULT(EnumSet.of(GUEST, CUSTOMER, ADMIN)),
    EDIT_MENU_ITEM(EnumSet.of(ADMIN)),
    FIND_ALL_USERS(EnumSet.of(ADMIN)),
    FIND_ALL_AVAILABLE_MENU(EnumSet.of(GUEST, CUSTOMER, ADMIN)),
    FIND_ALL_MENU(EnumSet.of(ADMIN)),
    FIND_ALL_ORDERS(EnumSet.of(ADMIN)),
    FIND_ORDER_BY_USER_ID(EnumSet.of(CUSTOMER)),
    FIND_ORDERS_BY_STATUS(EnumSet.of(ADMIN)),
    FIND_ORDERS_BY_DATE_RANGE(EnumSet.of(ADMIN)),
    GO_TO_ACCOUNT_PAGE(EnumSet.of(CUSTOMER, ADMIN)),
    GO_TO_CANCEL_ORDER_PAGE(EnumSet.of(CUSTOMER, ADMIN)),
    GO_TO_CART_PAGE(EnumSet.of(CUSTOMER)),
    GO_TO_CHANGE_PASSWORD_PAGE(EnumSet.of(CUSTOMER, ADMIN)),
    GO_TO_CONTACT_PAGE(EnumSet.of(GUEST, CUSTOMER, ADMIN)),
    GO_TO_CREATE_MENU_ITEM_PAGE(EnumSet.of(ADMIN)),
    GO_TO_EDIT_MENU_PAGE(EnumSet.of(ADMIN)),
    GO_TO_LOGIN_PAGE(EnumSet.of(GUEST)),
    GO_TO_MAIN_PAGE(EnumSet.of(GUEST, CUSTOMER, ADMIN)),
    GO_TO_MENU_PAGE(EnumSet.of(GUEST, CUSTOMER, ADMIN)),
    GO_TO_ORDER_MANAGEMENT_PAGE(EnumSet.of(ADMIN)),
    GO_TO_PLACE_ORDER_PAGE(EnumSet.of(CUSTOMER)),
    GO_TO_REFILL_BALANCE_PAGE(EnumSet.of(CUSTOMER)),
    GO_TO_REGISTRATION_PAGE(EnumSet.of(GUEST)),
    GO_TO_UPDATE_ORDER_PAGE(EnumSet.of(ADMIN)),
    LOGIN(EnumSet.of(GUEST)),
    LOGOUT(EnumSet.of(CUSTOMER, ADMIN)),
    REGISTRATION(EnumSet.of(GUEST)),
    REFILL_BALANCE(EnumSet.of(CUSTOMER)),
    REMOVE_ITEM_FROM_CART(EnumSet.of(CUSTOMER)),
    UPDATE_ORDER(EnumSet.of(ADMIN)),
    UPDATE_PERSONAL_DATA(EnumSet.of(CUSTOMER, ADMIN)),
    UPDATE_USER_ROLE(EnumSet.of(ADMIN)),
    UPDATE_USER_STATUS(EnumSet.of(ADMIN)),
    UPLOAD_IMAGE(EnumSet.of(ADMIN));
    private EnumSet<UserRole> acceptableRole;

    CommandType(EnumSet<UserRole> acceptableRole) {
        this.acceptableRole = acceptableRole;
    }

    public EnumSet<UserRole> getAcceptableRole() {
        return acceptableRole;
    }
}