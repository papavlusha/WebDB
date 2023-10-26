package lab_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ResourceBundle;
public class ConnectionPool {
    private static final int MAX_POOL_SIZE = 10;
    private static final String URL = "jdbc:sqlite:/home/pawel/BSU/trash/films.db";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";
    
    private static ConnectionPool instance;
    private Queue<Connection> connectionQueue;
    private Lock lock;
    
    private ConnectionPool() {
        connectionQueue = new LinkedList<>();
        lock = new ReentrantLock();
    }
    
    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        lock.lock();
        try {
            if (connectionQueue.isEmpty()) {
                return createConnection();
            } else {
                return connectionQueue.poll();
            }
        } finally {
            lock.unlock();
        }
    }
    
    public void releaseConnection(Connection connection) {
        lock.lock();
        try {
            if (connectionQueue.size() < MAX_POOL_SIZE) {
                connectionQueue.offer(connection);
            } else {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}