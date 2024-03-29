package lab_2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import controller.FilmRepository;
import entities.Actor;
import entities.Director;
import entities.Film;


import javax.security.auth.login.LoginException;
import static Logger.LogManager.logException;
public class Main {

	public static void main(String[] args) {
		try {
			FilmRepository filmRepo = new FilmRepository();
			Scanner scanner = new Scanner(System.in);
			boolean exit = false;
			do {
				System.out.println();
				System.out.println("Choose an option:");
				System.out.println("1. Sort films by rating");
				System.out.println("2. Find film by id");
				System.out.println("3. Print actors in film");
				System.out.println("4. Print top rated films");
				System.out.println("5. Print actors in at least N films");
				System.out.println("6. Print actor directors");
				System.out.println("7. Add new film");
				System.out.println("8. Delete films by release date");
				System.out.println("9. Update film rating");
				System.out.println("0. Exit");
				System.out.print("Your choice: ");
				int choice = scanner.nextInt();
				switch (choice) {
					case 1:
						filmRepo.sortFilmsByRating();
						break;
					case 2:
						System.out.print("Enter film id: ");
						int id = scanner.nextInt();
						Film film = filmRepo.findFilmById(id);
						if (film != null) {
							List<Director> directors = filmRepo.getAllDirectors();
							directors.toArray();
							System.out.println(film.getTitle() + " -rating:" + film.getRating() + ", country:" + film.getCountry()
									+ ", year:" + film.getReleaseDate() + ", director:"
									+ directors.get(film.getDirectorId() - 1).getName());
						} else {
							System.out.println("No film with id: " + id);
						}
						break;
					case 3:
						System.out.print("Enter film id: ");
						int filmId = scanner.nextInt();
						filmRepo.printActorsInFilm(filmId);
						break;
					case 4:
						System.out.print("Enter number of films to print: ");
						int n = scanner.nextInt();
						filmRepo.printTopRatedFilms(n);
						break;
					case 5:
						System.out.print("Enter minimum number of films: ");
						int minMovies = scanner.nextInt();
						filmRepo.printActorsInMovies(minMovies);
						break;
					case 6:
						filmRepo.printActorDirectors();
						break;
					case 7:
						System.out.print("Enter film title: ");
						String title = scanner.next();
						System.out.print("Enter release year: ");
						int releaseDate = 0;
						String actorIdsString, country;
						int rating, directorId;
						try {
							releaseDate = scanner.nextInt();
							System.out.print("Enter country: ");
							country = scanner.next();
							System.out.print("Enter rating: ");
							rating = scanner.nextInt();
							System.out.print("Enter director id: ");
							directorId = scanner.nextInt();
							System.out.print("Enter actor id (separated by commas): ");
							actorIdsString = scanner.next();
						} catch (InputMismatchException e) {
							logException(e);
							break;
						}
						String[] actorIdsArr = actorIdsString.split(",");
						List<Integer> actorIds = new ArrayList<>();
						for (String actorIdStr : actorIdsArr) {
							actorIds.add(Integer.parseInt(actorIdStr));
						}
						Film newFilm = new Film(title, releaseDate, country, rating, directorId, actorIds);
						filmRepo.addFilm(newFilm);
						break;
					case 8:
						System.out.print("Enter years ago (integer): ");
						int yearsAgo = scanner.nextInt();
						filmRepo.deleteFilmsByReleaseDate(yearsAgo);
						break;
					case 9:
						System.out.print("Enter film id: ");
						int newRating;
						int updateFilmId;

						try {
							updateFilmId = scanner.nextInt();

							System.out.print("Enter new rating: ");
							newRating = scanner.nextInt();
							filmRepo.updateFilmRating(updateFilmId, newRating);
						} catch (InputMismatchException e) {
							logException(e);
						}

						break;
					case 0:
						exit = true;
						break;
					default:
						System.out.println("Invalid choice");
				}
			} while (!exit);
			scanner.close();
		}
		catch (InputMismatchException e) {
			logException(e);
		}
	}

}