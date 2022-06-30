package by.jwd.cafe.model.dao;

public final class ColumnName {
    //table cafe.users
    public static final String USER_ID = "user_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String BALANCE = "balance";
    public static final String LOYALTY_POINTS = "loyalty_points";
    public static final String IS_ACTIVE = "is_active";
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_NAME = "role_name";

    //table cafe.menu_item
    public static final String MENU_ITEM_ID = "menu_item_id";
    public static final String MENU_ITEM_TYPE_ID = "menu_item_type_id";
    public static final String MENU_ITEM_NAME = "menu_item_name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String AVAILABLE = "available";
    public static final String PICTURE = "picture";

    //table cafe.menu_item_type
    public static final String TYPE_ID = "id";
    public static final String TYPE_NAME = "type_name";
    //table cafe.order
    public static final String ORDER_ID = "order_id";
    public static final String USERS_USER_ID = "users_user_id";
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String PICK_UP_TIME = "pick_up_time";
    public static final String ORDER_COST = "order_cost";
    public static final String IS_PAID = "is_paid";
    public static final String STATUS = "status";
    public static final String CREATION_DATE = "creation_date";


    public static final String NUM = "num";

    private ColumnName() {
    }
}
