package lab_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.ResourceBundle;
public class JdbcConnector {
	private static Connection connection;

	public static Connection getConnection() {
		if (connection == null) {
			ResourceBundle resource = ResourceBundle.getBundle("database");
			String url = resource.getString("url");
			String user = resource.getString("username");;
			String password = resource.getString("password");
			try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public static void close() throws SQLException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new SQLException("Can't close connection", e);
			}
		}
	}
}