// Подключаем необходимые классы
package DaoPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import entities.Film;

public class DAOFilm {
	private Connection connection;

	public DAOFilm(Connection conn) {
		this.connection = conn;
		if (conn != null) {
			System.out.println("\nConnection successful!\n");
		} else {
			System.out.println("\nConnection failed\n");
		}
	}

	// запрос на добавление фильма
	public void createFilm(Film film) throws SQLException {
		String sql = "INSERT INTO films (title, release_date, country, rating, director_id) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = this.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, film.getTitle());
		ps.setInt(2, film.getReleaseDate());
		ps.setString(3, film.getCountry());
		ps.setInt(4, film.getRating());
		ps.setInt(5, film.getDirectorId());
		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			int filmId = rs.getInt(1);

			// Добавляем информацию об актерах, участвовавших в фильме
			for (int actorId : film.getActorIds()) {
				sql = "INSERT INTO films_actors (film_id, actor_id) VALUES (?, ?)";
				ps = this.connection.prepareStatement(sql);
				ps.setInt(1, filmId);
				ps.setInt(2, actorId);
				ps.executeUpdate();
			}
		}
	}

	// запрос на получение всех фильмов
	public List<Film> getAllFilms() throws SQLException {
		List<Film> films = new ArrayList<>();
		String sql = "SELECT * FROM films";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int filmId = rs.getInt("id");

			// Получаем информацию об актерах, участвовавших в фильме
			List<Integer> actorIds = new ArrayList<>();
			sql = "SELECT actor_id FROM films_actors WHERE film_id = ?";
			PreparedStatement psActors = this.connection.prepareStatement(sql);
			psActors.setInt(1, filmId);
			ResultSet rsActors = psActors.executeQuery();
			while (rsActors.next()) {
				actorIds.add(rsActors.getInt("actor_id"));
			}

			Film film = new Film(filmId, rs.getString("title"), rs.getInt("release_date"), rs.getString("country"),
					rs.getInt("rating"), rs.getInt("director_id"), actorIds);
			films.add(film);
		}
		return films;
	}

	// запрос на получение фильма по id
	public Film getFilmById(int searchId) throws SQLException {
		Film res = null;
		String sql = "SELECT * FROM films WHERE id = ?";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setInt(1, searchId);
		ResultSet rs = ps.executeQuery();
		if (!rs.next()) {
			return res;
		}

		int filmId = rs.getInt("id");

		// Получаем информацию об актерах, участвовавших в фильме
		List<Integer> actorIds = new ArrayList<>();
		sql = "SELECT actor_id FROM films_actors WHERE film_id = ?";
		PreparedStatement psActors = this.connection.prepareStatement(sql);
		psActors.setInt(1, filmId);
		ResultSet rsActors = psActors.executeQuery();
		while (rsActors.next()) {
			actorIds.add(rsActors.getInt("actor_id"));
		}

		res = new Film(filmId, rs.getString("title"), rs.getInt("release_date"), rs.getString("country"),
				rs.getInt("rating"), rs.getInt("director_id"), actorIds);
		return res;
	}

	// запрос на обновление фильма
	public void updateFilm(Film film) throws SQLException {
		String sql = "UPDATE films SET title = ?, release_date = ?, country = ?, rating = ?, director_id = ? WHERE id = ?";

		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setString(1, film.getTitle());
		ps.setInt(2, film.getReleaseDate());
		ps.setString(3, film.getCountry());
		ps.setInt(4, film.getRating());
		ps.setInt(5, film.getDirectorId());
		ps.setInt(6, film.getId());
		ps.executeUpdate();

		// Обновляем информацию об актерах, участвовавших в фильме
		sql = "DELETE FROM films_actors WHERE film_id = ?";
		ps = this.connection.prepareStatement(sql);
		ps.setInt(1, film.getId());
		ps.executeUpdate();

		for (int actorId : film.getActorIds()) {
			sql = "INSERT INTO films_actors (film_id, actor_id) VALUES (?, ?)";
			ps = this.connection.prepareStatement(sql);
			ps.setInt(1, film.getId());
			ps.setInt(2, actorId);
			ps.executeUpdate();
		}
	}

	// запрос на удаление фильма по id
	public void deleteFilm(int id) throws SQLException {
		String sql = "DELETE FROM films WHERE id = ?";

		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
	}
}
