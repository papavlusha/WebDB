import java.util.*;

public class FilmRepository {
    private List<Film> films = new ArrayList<>();

    public List<Film> getFilms() {
        return films;
    }

    public void addFilm(Film film) {
        films.add(film);
    }

    public void removeFilm(Film film) {
        films.remove(film);
    }

    public Film findFilmByTitle(String title) {
        for (Film film : films) {
            if (film.getTitle().equalsIgnoreCase(title)) {
                return film;
            }
        }
        return null;
    }

    public void sortFilmsByRating() {
        Collections.sort(films, new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                return Integer.compare(f2.getRating(), f1.getRating());
            }
        });
    }


    public List<String> findActorsByFilmTitle(String filmTitle) {
        Film film = findFilmByTitle(filmTitle);
        if (film != null) {
            return film.getActors();
        }
        return null;
    }

    public List<Film> findFilmsWithHighestRating() {
        List<Film> result = new ArrayList<>();
        int maxRating = Integer.MIN_VALUE;
        for (Film film : films) {
            if (film.getRating() > maxRating) {
                result.clear();
                result.add(film);
                maxRating = film.getRating();
            } else if (film.getRating() == maxRating) {
                result.add(film);
            }
        }
        return result;
    }

    public List<String> findActorsInFilmsCount(int count) {
        Map<String, Integer> actorsCountMap = new HashMap<>();
        for (Film film : films) {
            for (String actor : film.getActors()) {
                int currentCount = actorsCountMap.getOrDefault(actor, 0);
                actorsCountMap.put(actor, currentCount + 1);
            }
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : actorsCountMap.entrySet()) {
            if (entry.getValue() >= count) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public List<String> findActorDirectors() {
        Set<String> result = new HashSet<>();
        for (Film film : films) {
            for (String actor : film.getActors()) {
                for (String director : film.getDirectors()) {
                    if (actor.equalsIgnoreCase(director)) {
                        result.add(actor);
                    }
                }
            }
        }
        return new ArrayList<>(result);
    }
}
