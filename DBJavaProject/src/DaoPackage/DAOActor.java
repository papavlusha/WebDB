package DaoPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entities.Actor;

public class DAOActor {
	private Connection connection;

	public DAOActor(Connection conn) {
		this.connection = conn;
		if (connection != null) {
//			System.out.println("\nConnection successful!\n");
		} else {
			System.out.println("\nVi loshara bez db\n");
		}
	}

	public void addActor(Actor actor) throws SQLException {
		
		String sql = "INSERT INTO actors(name, birth_date) VALUES(?, ?)";

		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setString(1, actor.getName());
		ps.setInt(2, actor.getDate());
		ps.executeUpdate();
	}

	public Actor getActorById(int actorId) throws SQLException {
		Actor actor = null;
		String sql = "SELECT * FROM actors WHERE id = ?";

		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setInt(1, actorId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			actor= new Actor(rs.getInt("id"), rs.getString("name"), rs.getInt("birth_date"));
		}

		return actor;
	}

	public List<Actor> getAllActors() throws SQLException {
		List<Actor> actors = new ArrayList<>();
		String sql = "SELECT * FROM actors";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Actor actor = new Actor(rs.getInt("id"), rs.getString("name"), rs.getInt("birth_date"));
			actors.add(actor);
		}

		return actors;
	}


	public void deleteActor(int id) throws SQLException {
		String sql = "DELETE FROM actors WHERE id = ?";

		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();

	}
}
