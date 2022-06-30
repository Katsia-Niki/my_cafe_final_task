package by.jwd.cafe.controller.command;

/**
 * {@code Router} class represent complex form response of {@link Command}
 * It includes the page to which the transition should be made and type of sending.
 */

public class Router {
    private String page;
    private Type type = Type.FORWARD;

    /**
     * {@code SendingType} enum represent a sending type
     */
    public enum Type {
        /**
         * Forward type.
         */
        FORWARD,
        /**
         * Redirect type.
         */
        REDIRECT
    }

    public Router(String page) {
        this.page = page;
    }

    public Router(String page, Type type) {
        this.page = page;
        if (type != null) {
            this.type = type;
        }
    }

    public String getPage() {
        return page;
    }

    public Type getType() {
        return type;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
