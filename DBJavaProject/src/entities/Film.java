// Подключаем необходимые библиотеки
package entities;

import java.sql.Date;
import java.util.List;



public class Film {
    private int id;
    private String title;
    private List<Integer> actorIds;
    private int directorsId;
    private int releaseDate;
    private String country;
    private int rating;

    public Film(int id, String title, int releaseDate, String country, int rating, int directorsId, List<Integer> actorIds) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.country = country;
        this.rating = rating;
        this.directorsId = directorsId;
        this.actorIds = actorIds;
    }

    public Film(String title, int releaseDate, String country, int rating, int directorsId, List<Integer> actorIds) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.country = country;
        this.rating = rating;
        this.directorsId = directorsId;
        this.actorIds = actorIds;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getRating() {
        return rating;
    }

    public List<Integer> getActorIds() {
        return actorIds;
    }

    public int getDirectorId() {
        return directorsId;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public String getCountry() {
        return country;
    }
}