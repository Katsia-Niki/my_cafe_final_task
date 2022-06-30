package by.jwd.cafe.exception;

/**
 * {@code ServiceException} class represent a checked exception from {@link by.jwd.cafe.service}
 *
 * @see Exception
 */
public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
