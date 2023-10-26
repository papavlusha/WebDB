package DaoPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entities.Actor;
import lab_2.ConnectionPool;
import static Logger.LogManager.logException;

public class DAOActor {
	private Connection connection;

	public DAOActor() {}

	public void addActor(Actor actor) throws SQLException {

		try {
			connection = ConnectionPool.GetConnection();
			String sql = "INSERT INTO actors(name, birth_date) VALUES(?, ?)";
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ps.setString(1, actor.getName());
			ps.setInt(2, actor.getDate());
			ps.executeUpdate();
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
	}

	public Actor getActorById(int actorId) throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
			Actor actor = null;
			String sql = "SELECT * FROM actors WHERE id = ?";

			PreparedStatement ps = this.connection.prepareStatement(sql);
			ps.setInt(1, actorId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				actor = new Actor(rs.getInt("id"), rs.getString("name"), rs.getInt("birth_date"));
			}

			return actor;
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
		return null;
	}

	public List<Actor> getAllActors() throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
			List<Actor> actors = new ArrayList<>();
			String sql = "SELECT * FROM actors";
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Actor actor = new Actor(rs.getInt("id"), rs.getString("name"), rs.getInt("birth_date"));
				actors.add(actor);
			}

			return actors;
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
		return null;
	}


	public void deleteActor(int id) throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
			String sql = "DELETE FROM actors WHERE id = ?";
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
	}
}
