package DaoPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entities.Director;

public class DAODirector {
	private Connection connection;

	public DAODirector(Connection conn) {
		this.connection = conn;
		if (conn != null) {
//        	System.out.println("\nConnection successful!\n");
		} else {
			System.out.println("\nVi loshara bez db\n");
		}
	}

	public void addDirector(Director director) throws SQLException {
		String sql = "INSERT INTO directors(name, birth_date) VALUES(?, ?)";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setString(1, director.getName());
		ps.setInt(2,  director.getDate());
		ps.executeUpdate();
	}

	public List<Director> getAllDirectors() throws SQLException {
		List<Director> drivers = new ArrayList<>();
		String sql = "SELECT * FROM directors";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Director driver = new Director(rs.getInt("Id"), rs.getString("name"), rs.getInt("birth_date"));
			drivers.add(driver);
		}

		return drivers;
	}


}
