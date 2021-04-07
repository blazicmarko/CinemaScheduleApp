package com.example.Cinema.model;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class Filter {
    @NotNull
    private String movieName;
    private Date date;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
