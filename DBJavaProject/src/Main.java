import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        FilmRepository repository = new FilmRepository();
        Film film1 = new Film("The Shawshank Redemption",
                Arrays.asList("Tim Robbins", "Morgan Freeman"),
                new Date(1994, 9, 23),
                "USA",
                9);
        repository.addFilm(film1);

        Film film2 = new Film("The Godfather",
                Arrays.asList("Marlon Brando", "Al Pacino"),
                new Date(1972, 3, 24),
                "USA",
                10);
        repository.addFilm(film2);


        repository.sortFilmsByRating();
        List<Film> films = repository.getFilms();
        for (Film film : films) {
            System.out.println(film.getTitle() + " - " + film.getRating());
        }

        Film film = repository.findFilmByTitle("The Shawshank Redemption");
        if (film != null) {
            System.out.println("Найден фильм " + film.getTitle());
        }

        List<String> actorsInFilm = repository.findActorsByFilmTitle("The Shawshank Redemption");
        System.out.println("Актеры, снимавшиеся в The Shawshank Redemption:");
        for (String actor : actorsInFilm) {
            System.out.println(actor);
        }

        List<Film> highestRatedFilms = repository.findFilmsWithHighestRating();
        System.out.println("Фильмы с наивысшим рейтингом:");
        for (Film  f1 : highestRatedFilms) {
            System.out.println(f1.getTitle() + " - " + f1.getRating());
        }

        List<String> actorsInFilmsCount = repository.findActorsInFilmsCount(2);
        System.out.println("Актеры, снимавшиеся в как минимум 2 фильмах:");
        for (String actor : actorsInFilmsCount) {
            System.out.println(actor);
        }

        List<String> actorDirectors = repository.findActorDirectors();
        System.out.println("Актеры-режиссеры:");
        for (String actor : actorDirectors) {
            System.out.println(actor);
        }

        Film film3 = new Film("Pulp Fiction",
                Arrays.asList("John Travolta", "Samuel L. Jackson"),
                new Date(1994, 9, 10),
                "USA",
                8);
        repository.addFilm(film3);

        List<Film> oldFilms = new ArrayList<>();
        for (Film f : repository.getFilms()) {
            Date releaseDate = film.getReleaseDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(releaseDate);
            calendar.add(Calendar.YEAR, 10);
            Date tenYearsAgo = calendar.getTime();
            if (tenYearsAgo.before(new Date())) {
                oldFilms.add(film);
            }
        }
        for (Film f : oldFilms) {
            repository.removeFilm(film);
        }
    }

}