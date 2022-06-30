package by.jwd.cafe.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code ConnectionPool} class represent thread-safe pool of connection to database
 */
public class ConnectionPool {
    static Logger logger = LogManager.getLogger();
    private static final int POOL_SIZE = 8;
    private static final String DB_PROPERTY = "properties.db";
    private static final String DB_DRIVER_KEY = "driver";
    private static final String DB_URL_KEY = "url";
    private static final String DB_USER_KEY = "user";
    private static final String DB_PASSWORD_KEY = "password";
    private static final String DB_DRIVER;
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static ConnectionPool instance;
    private static AtomicBoolean instanceIsExist = new AtomicBoolean(false);
    private static Lock instanceLocker = new ReentrantLock();
    private BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);
    private BlockingQueue<ProxyConnection> usedConnections = new LinkedBlockingQueue<>(POOL_SIZE);

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(DB_PROPERTY);
            DB_URL = bundle.getString(DB_URL_KEY);
            DB_USER = bundle.getString(DB_USER_KEY);
            DB_PASSWORD = bundle.getString(DB_PASSWORD_KEY);
            DB_DRIVER = bundle.getString(DB_DRIVER_KEY);
            Class.forName(DB_DRIVER);
        } catch (MissingResourceException e) {
            logger.fatal("Property file not found or has incorrect data " + DB_PROPERTY, e);
            throw new RuntimeException("Property file not found or has incorrect data" + DB_PROPERTY, e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Driver have not been registered" + DB_PROPERTY, e);
            throw new ExceptionInInitializerError();
        }
    }

    /**
     * Constructor creates queue of free {@link ProxyConnection}
     */
    private ConnectionPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            } catch (SQLException e) {
                logger.error("Connection was not created " + i, e);
            }
        }
        if (freeConnections.isEmpty()) {
            logger.fatal("Connection pool is empty.");
            throw new RuntimeException("Connection pool is empty.");
        }
        logger.info("Connection pool was created.");
    }

    /**
     * {@code getInstance} method represent thread-safe singleton
     *
     * @return instance of {@link ConnectionPool}
     */
    public static ConnectionPool getInstance() {
        if (!instanceIsExist.get()) {
            instanceLocker.lock();
            try {
                if (instanceIsExist.compareAndSet(false, true)) {
                    instance = new ConnectionPool();
                }
            } finally {
                instanceLocker.unlock();
            }
        }
        return instance;
    }

    /**
     * {@code getConnection} method get free {@link Connection} from {@link ConnectionPool}
     *
     * @return free {@link Connection} from {@link ConnectionPool}
     */
    public Connection getConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = freeConnections.take();
            usedConnections.put(proxyConnection);
        } catch (InterruptedException e) {
            logger.error("Try to get connection from pool was failed. ", e);
            Thread.currentThread().interrupt();
        }
        return proxyConnection;
    }

    /**
     * {@code releaseConnection} method release {@link Connection} into {@link ConnectionPool}
     */
    public boolean releaseConnection(Connection connection) {
        boolean flag = false;
        if (connection instanceof ProxyConnection proxyConnection) {
            try {
                usedConnections.remove(proxyConnection);
                freeConnections.put(proxyConnection);
                flag = true;
            } catch (InterruptedException e) {
                logger.error("Try to release connection from pool was failed. ", e);
                Thread.currentThread().interrupt();
            }
        } else {
            logger.error("Unknown connection.");
        }
        return flag;
    }

    /**
     * {@code destroyPool} method destroy {@link ConnectionPool}
     */
    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException e) {
                logger.error("Try to destroy connection pool was failed. SQLException ", e);
            } catch (InterruptedException e) {
                logger.error("Try to destroy connection pool was failed. InterruptedException ", e);
                Thread.currentThread().interrupt();
            }
        }
        deregisterDriver();
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(d -> {
            try {
                DriverManager.deregisterDriver(d);
            } catch (SQLException e) {
                logger.error("Try to deregister driver " + d + " was failed.", e);
            }
        });
    }
}
