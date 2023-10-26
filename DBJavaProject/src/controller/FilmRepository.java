package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import DaoPackage.DAOActor;
import DaoPackage.DAODirector;
import DaoPackage.DAOFilm;
import entities.Actor;
import entities.Director;
import entities.Film;
import lab_2.ConnectionPool;

import static Logger.LogManager.logException;

public class FilmRepository {
	protected ConnectionPool cnr;
	private DAOActor daoActor;
	public DAODirector daoDirector;
	private DAOFilm daoFilm;

	public FilmRepository() {
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Connection to SQLite has been established.");
			daoActor = new DAOActor();
			daoDirector = new DAODirector();
			daoFilm = new DAOFilm();
		} catch (Exception e) {
			logException(e);
			e.printStackTrace();
			System.exit(0);
		}
	}

	// Часть 1. Сортировка и поиск фильма в наборе
	public void sortFilmsByRating() {
		try {
			List<Film> films = daoFilm.getAllFilms();
			Comparator<Film> compareByRating = Comparator.comparingInt(Film::getRating);
			Collections.sort(films, compareByRating.reversed());
			List<Director> directors = daoDirector.getAllDirectors();
			directors.toArray();
			for (Film item : films) {
				System.out.println(item.getTitle() + " -rating:" + item.getRating() + ", country:" + item.getCountry()
						+ ", year:" + item.getReleaseDate() + ", director:"
						+ directors.get(item.getDirectorId() - 1).getName());
			}
		} catch (SQLException e) {
			logException(e);
			e.printStackTrace();
		}
	}

	public Film findFilmById(int id) {
		try {
			return daoFilm.getFilmById(id);
		} catch (SQLException e) {
			logException(e);
			e.printStackTrace();
			return null;
		}
	}

	// Часть 2. Получение информации о фильмах и актерах
	public void printActorsInFilm(int filmId) {
		try {
			Film film = daoFilm.getFilmById(filmId);
			if (film == null) {
				System.out.println("No film with id: " + filmId);
				return;
			}
			List<Actor> actors = daoActor.getAllActors();
			List<Integer> actorIds = film.getActorIds();
			List<Actor> actorsInFilm = actors.stream().filter(actor -> actorIds.contains(actor.getActorId())).collect(Collectors.toList());
			for (Actor item: actorsInFilm) {
				System.out.println(item.getName() + " " + item.getDate());
			}
		} catch (SQLException e) {
			logException(e);
			e.printStackTrace();
		}
	}

	public void printTopRatedFilms(int n) {
		try {
			List<Film> films = daoFilm.getAllFilms();
			Comparator<Film> compareByRating = Comparator.comparingInt(Film::getRating);
			List<Film> topRatedFilms = films.stream().sorted(compareByRating.reversed()).limit(n)
					.collect(Collectors.toList());

			//topRatedFilms.forEach(System.out::println);
			List<Director> directors = daoDirector.getAllDirectors();
			directors.toArray();
			for (Film item : topRatedFilms) {
				System.out.println(item.getTitle() + " -rating:" + item.getRating() + ", country:"+ item.getCountry()
						+ ", year:" + item.getReleaseDate() + ", director:"
						+ directors.get(item.getDirectorId() - 1).getName());
			}
		} catch (SQLException e) {
			logException(e);
			e.printStackTrace();
		}
	}

	public void printActorsInMovies(int n) {
		try {
			List<Film> films = daoFilm.getAllFilms();
			List<Integer> actorIds = films.stream().flatMap(film -> film.getActorIds().stream())
					.collect(Collectors.groupingBy(actorId -> actorId, Collectors.counting())).entrySet().stream()
					.filter(entry -> entry.getValue() >= n).map(entry -> entry.getKey())
					.collect(Collectors.toList());
			List<Actor> actors = daoActor.getAllActors().stream().filter(actor -> actorIds.contains(actor.getActorId()))
					.collect(Collectors.toList());
			for (Actor item: actors) {
				System.out.println(item.getName() + " " + item.getDate());
			}
		} catch (SQLException e) {
			logException(e);
			e.printStackTrace();
		}
	}

	public List<Director> getAllDirectors() {
		try {
			List<Director> directors = daoDirector.getAllDirectors();
			return daoDirector.getAllDirectors();
		} catch (SQLException e) {
			logException(e);
			throw new RuntimeException(e);
		}
	}
	public void printActorDirectors() {
		try {
			List<Director> directors = daoDirector.getAllDirectors();
			List<Actor> actors = daoActor.getAllActors();
			List<String> names1 = directors.stream().map(Director::getName).collect(Collectors.toList());
			List<String> names2 = actors.stream().map(Actor::getName).collect(Collectors.toList());

			List<String> commonNames = names1.stream().filter(names2::contains).collect(Collectors.toList());
			for (String item: commonNames) {
				System.out.println(item);
			}

		} catch (SQLException e) {
			logException(e);
			e.printStackTrace();
		}
	}

	// Добавление нового фильма, удаление фильмов, дата выхода которых была более заданного числа лет назад
	public void addFilm(Film film) {
		try {
			daoFilm.createFilm(film);
		} catch (SQLException e) {
			logException(e);
			e.printStackTrace();
		}
	}

	public void deleteFilmsByReleaseDate(int yearsAgo) {
		try {
			List<Film> films = daoFilm.getAllFilms();
			List<Integer> idsToDelete = films.stream().filter(film -> {
				//long millisInYear = 1000L * 60 * 60 * 24 * 365;
				long yearsSinceRelease = (2023 - film.getReleaseDate());
				return yearsSinceRelease >= yearsAgo;
			}).map(film -> film.getId()).collect(Collectors.toList());
			idsToDelete.forEach(id -> {
				try {
					daoFilm.deleteFilm(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} catch (SQLException e) {
			logException(e);
			e.printStackTrace();
		}
	}

	// Администратор обновляет информацию о рейтинге фильмов
	public void updateFilmRating(int filmId, int rating) {
		try {
			Film filmToUpdate = daoFilm.getFilmById(filmId);
			if (filmToUpdate == null) {
				System.out.println("No film with id: " + filmId);
				return;
			}
			filmToUpdate.setRating(rating);
			daoFilm.updateFilm(filmToUpdate);
		} catch (SQLException e) {

			logException(e);
			e.printStackTrace();
		}
	}
}