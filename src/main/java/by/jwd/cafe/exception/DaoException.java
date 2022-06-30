package by.jwd.cafe.exception;

/**
 * {@code DaoException} class represent a checked exception from {@link by.jwd.cafe.model.dao}
 *
 * @see Exception
 */
public class DaoException extends Exception {
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
