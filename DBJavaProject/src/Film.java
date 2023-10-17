import java.util.*;

public class Film {
    private String title;
    private List<String> actors;
    private List<String> directors;
    private Date releaseDate;
    private String country;
    private int rating;

    public Film(String title, List<String> actors, Date releaseDate, String country, int rating) {
        this.title = title;
        this.actors = actors;
        this.releaseDate = releaseDate;
        this.country = country;
        this.rating = rating;
        this.directors = actors;
    }

    public String getTitle() {
        return title;
    }

    public int getRating() {
        return rating;
    }

    public List<String> getActors() {
        return  actors;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

}

