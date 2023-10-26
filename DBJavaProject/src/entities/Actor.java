package entities;

import java.sql.Date;

public class Actor {
	private int actorId;
    private String name;
    private int date;

    public Actor(String name_, int date_) {;
        this.name = name_;
        this.date = date_;
    }
    public Actor(int actorID_, String name_, int date_) {
      this.actorId = actorID_;
      this.name = name_;
      this.date = date_;
  }
    public Actor() {}
    
    public int getActorId() {
        return actorId;
    }

    public String getName() {
        return name;
    }

    public int getDate() {
        return date;
    }
}
