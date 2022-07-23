package test.jwd.cafe.model.pool;

import by.jwd.cafe.model.pool.ConnectionPool;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPoolTest {
    private ConnectionPool pool;

    @BeforeMethod
    public void init() {
        pool = ConnectionPool.getInstance();
    }

    @Test
    public void testGetConnection() {
        Connection connection = pool.getConnection();
        Assert.assertTrue(connection != null);
    }

    @Test
    public void testReleaseConnectionTrue() {
        Connection connection = pool.getConnection();
        Assert.assertTrue(pool.releaseConnection(connection));
    }

    @Test
    private void testReleaseConnectionFalse() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe",
                "root", "зфыы");
        Assert.assertFalse(pool.releaseConnection(connection));
    }

    @AfterMethod
    public void clean() {
        pool = null;
    }
}