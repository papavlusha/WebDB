// Подключаем необходимые классы
package DaoPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lab_2.ConnectionPool;
import entities.Film;
import static Logger.LogManager.logException;
public class DAOFilm {
	private Connection connection;

	public DAOFilm() {}

	// запрос на добавление фильма
	public void createFilm(Film film) throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
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
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
	}

	// запрос на получение всех фильмов
	public List<Film> getAllFilms() throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
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
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
		return null;
	}

	// запрос на получение фильма по id
	public Film getFilmById(int searchId) throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
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
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
		return null;
	}

	// запрос на обновление фильма
	public void updateFilm(Film film) throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
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
		} catch (Exception e) {
			logException(e);
		}
		finally {
			ConnectionPool.ReleaseConnection(connection);
		}
	}

	// запрос на удаление фильма по id
	public void deleteFilm(int id) throws SQLException {
		try {
			connection = ConnectionPool.GetConnection();
			String sql = "DELETE FROM films WHERE id = ?";
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
