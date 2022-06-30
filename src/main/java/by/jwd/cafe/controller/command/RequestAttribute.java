package by.jwd.cafe.controller.command;

/**
 * {@code RequestAttribute} class represent container for request attribute name
 * The class does not contain functionality, only constants.
 */

public final class RequestAttribute {
    public static final String USER = "user";
    public static final String CAN_BE_CANCELLED_MAP = "can_be_cancelled_map";
    public static final String CAN_BE_UPDATED_MAP = "can_be_updated_map";
    public static final String CURRENT_PAGE_NUMBER = "current_page_number";

    public static final String DATE_FROM_ATTRIBUTE = "date_from_atr";
    public static final String DATE_TO_ATTRIBUTE = "date_to_atr";
    public static final String USERS_LIST = "users_list";
    public static final String ORDER_LIST = "order_list";
    public static final String ORDER_STATUS_ATTRIBUTE = "order_status_atr";
    public static final String SEARCH_ATTRIBUTE = "search_atr";
    public static final String SEARCH_PARAMETERS_ATTRIBUTE = "search_parameters_atr";
    public static final String NUMBER_OF_PAGES = "number_of_pages";
    public static final String WRONG_DATE_RANGE_ATTRIBUTE = "wrong_date_range_atr";

    private RequestAttribute() {

    }
}
