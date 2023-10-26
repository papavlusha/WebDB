package entities;

import java.sql.Date;

public class Director {
	private int directorId;
    private String name;
    private int date;

    public Director(String name_, int date_) {
        this.name = name_;
        this.date = date_;
    }
    public Director(int directorId_, String name_, int date_) {
      this.directorId = directorId_;
      this.name = name_;
      this.date = date_;
  }
    public int getId() {
    	return directorId;
    }
    public int getDate() {
        return date;
    }
    
    public int getDirectorId() {
        return directorId;
    }
    
    public String getName() {
        return name;
    }

}
