package DaoPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import lab_2.ConnectionPool;
import entities.Director;
import static Logger.LogManager.logException;

public class DAODirector {
	private Connection connection;

	public DAODirector() {}

	public void addDirector(Director director) throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
			String sql = "INSERT INTO directors(name, birth_date) VALUES(?, ?)";
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ps.setString(1, director.getName());
			ps.setInt(2, director.getDate());
			ps.executeUpdate();
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
	}

	public List<Director> getAllDirectors() throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
			List<Director> drivers = new ArrayList<>();
			String sql = "SELECT * FROM directors";
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Director driver = new Director(rs.getInt("Id"), rs.getString("name"), rs.getInt("birth_date"));
				drivers.add(driver);
			}

			return drivers;
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
		return null;
	}


}
